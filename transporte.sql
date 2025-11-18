-- ============================================
-- LIMPEZA SEGURA
-- ============================================

DROP VIEW IF EXISTS vw_viagens_finalizadas;
DROP VIEW IF EXISTS vw_usuarios_grupos;
DROP VIEW IF EXISTS vw_passageiros_viagens;

DROP TRIGGER IF EXISTS trg_usuario_criado;
DROP TRIGGER IF EXISTS trg_finaliza_viagem;
DROP TRIGGER IF EXISTS trg_passageiro_criado;

DROP PROCEDURE IF EXISTS nova_viagem;
DROP PROCEDURE IF EXISTS novo_passageiro;
DROP FUNCTION IF EXISTS gerar_id;

DROP TABLE IF EXISTS viagens_passageiros;
DROP TABLE IF EXISTS passageiros;
DROP TABLE IF EXISTS viagens;
DROP TABLE IF EXISTS rotas;
DROP TABLE IF EXISTS veiculos;
DROP TABLE IF EXISTS motoristas;
DROP TABLE IF EXISTS log_usuarios;
DROP TABLE IF EXISTS log_sistema;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS grupos_usuarios;

DROP DATABASE IF EXISTS transporte;

-- ============================================
-- CRIAÇÃO DO BANCO
-- ============================================

CREATE DATABASE transporte
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE transporte;

-- ============================================
-- GRUPOS E USUÁRIOS
-- ============================================

CREATE TABLE grupos_usuarios (
    id_grupo VARCHAR(10) PRIMARY KEY,
    nome_grupo VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT
) ENGINE=InnoDB;

CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    id_grupo VARCHAR(10),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuarios_grupo FOREIGN KEY (id_grupo) REFERENCES grupos_usuarios(id_grupo)
) ENGINE=InnoDB;

-- ============================================
-- ENTIDADES PRINCIPAIS
-- ============================================

CREATE TABLE motoristas (
    id_motorista VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnh VARCHAR(20) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    status ENUM('ativo','inativo') DEFAULT 'ativo'
) ENGINE=InnoDB;

CREATE TABLE veiculos (
    id_veiculo VARCHAR(20) PRIMARY KEY,
    modelo VARCHAR(50) NOT NULL,
    placa VARCHAR(10) UNIQUE,
    capacidade INT,
    status ENUM('disponivel','em_servico','manutencao') DEFAULT 'disponivel'
) ENGINE=InnoDB;

CREATE TABLE rotas (
    id_rota VARCHAR(20) PRIMARY KEY,
    origem VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    distancia_km DECIMAL(7,2)
) ENGINE=InnoDB;

-- ============================================
-- ENTIDADE PASSAGEIROS
-- ============================================

CREATE TABLE passageiros (
    id_passageiro VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    status ENUM('ativo','inativo') DEFAULT 'ativo'
) ENGINE=InnoDB;

-- ============================================
-- ENTIDADE VIAGENS
-- ============================================

CREATE TABLE viagens (
    id_viagem VARCHAR(20) PRIMARY KEY,
    id_motorista VARCHAR(20) NOT NULL,
    id_veiculo VARCHAR(20) NOT NULL,
    id_rota VARCHAR(20) NOT NULL,
    id_usuario INT NOT NULL,
    status ENUM('pendente','em_andamento','finalizada') DEFAULT 'pendente',
    FOREIGN KEY (id_motorista) REFERENCES motoristas(id_motorista),
    FOREIGN KEY (id_veiculo) REFERENCES veiculos(id_veiculo),
    FOREIGN KEY (id_rota) REFERENCES rotas(id_rota),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB;

-- ============================================
-- RELAÇÃO VIAGEM ↔ PASSAGEIROS (N:N)
-- ============================================

CREATE TABLE viagens_passageiros (
    id_viagem VARCHAR(20),
    id_passageiro VARCHAR(20),
    PRIMARY KEY (id_viagem, id_passageiro),
    FOREIGN KEY (id_viagem) REFERENCES viagens(id_viagem) ON DELETE CASCADE,
    FOREIGN KEY (id_passageiro) REFERENCES passageiros(id_passageiro) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================
-- LOGS
-- ============================================

CREATE TABLE log_usuarios (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    acao VARCHAR(100),
    detalhes TEXT,
    data_log TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE log_sistema (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    componente VARCHAR(50),
    nivel ENUM('INFO','WARN','ERROR') DEFAULT 'INFO',
    mensagem TEXT,
    data_log TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- ÍNDICES
-- ============================================

CREATE INDEX idx_email_usuario ON usuarios(email);
CREATE INDEX idx_motorista_status ON motoristas(status);
CREATE INDEX idx_veiculo_status ON veiculos(status);
CREATE INDEX idx_viagens_status_data ON viagens(status, data_viagem);
CREATE INDEX idx_passageiro_status ON passageiros(status);

-- ============================================
-- FUNÇÃO PARA GERAR ID
-- ============================================

DELIMITER $$
CREATE FUNCTION gerar_id(prefixo VARCHAR(5)) 
RETURNS VARCHAR(20)
DETERMINISTIC
NO SQL
BEGIN
    RETURN CONCAT(prefixo,
                  DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'),
                  LPAD(FLOOR(RAND()*999),3,'0'));
END$$
DELIMITER ;

-- ============================================
-- PROCEDURES
-- ============================================

DELIMITER $$

CREATE PROCEDURE nova_viagem(
    IN p_motorista VARCHAR(20),
    IN p_veiculo VARCHAR(20),
    IN p_rota VARCHAR(20),
    IN p_usuario INT
)
BEGIN
    DECLARE nova VARCHAR(20);
    SET nova = gerar_id('VIA');
    INSERT INTO viagens VALUES(nova, p_motorista, p_veiculo, p_rota, p_usuario, NOW(), 'pendente');
    UPDATE veiculos SET status = 'em_servico' WHERE id_veiculo = p_veiculo;
    INSERT INTO log_sistema(componente, mensagem) VALUES('viagens', CONCAT('Criada viagem ', nova));
END$$

CREATE PROCEDURE novo_passageiro(
    IN p_nome VARCHAR(100),
    IN p_cpf VARCHAR(14),
    IN p_telefone VARCHAR(20)
)
BEGIN
    DECLARE novo_id VARCHAR(20);
    SET novo_id = gerar_id('PAS');
    INSERT INTO passageiros (id_passageiro, nome, cpf, telefone, status)
    VALUES (novo_id, p_nome, p_cpf, p_telefone, 'ativo');
    INSERT INTO log_sistema(componente, mensagem)
    VALUES('passageiros', CONCAT('Passageiro criado: ', p_nome, ' (', p_cpf, ')'));
END$$

DELIMITER ;

-- ============================================
-- TRIGGERS
-- ============================================

DELIMITER $$

CREATE TRIGGER trg_usuario_criado
AFTER INSERT ON usuarios FOR EACH ROW
INSERT INTO log_usuarios(id_usuario, acao, detalhes)
VALUES (NEW.id_usuario, 'CRIACAO', CONCAT('email=', NEW.email));
$$

CREATE TRIGGER trg_finaliza_viagem
AFTER UPDATE ON viagens
FOR EACH ROW
BEGIN
    IF NEW.status = 'finalizada' AND OLD.status <> 'finalizada' THEN
        UPDATE veiculos SET status = 'disponivel' WHERE id_veiculo = NEW.id_veiculo;
    END IF;
END$$

CREATE TRIGGER trg_passageiro_criado
AFTER INSERT ON passageiros
FOR EACH ROW
INSERT INTO log_sistema(componente, nivel, mensagem)
VALUES ('passageiros', 'INFO', CONCAT('Novo passageiro cadastrado: ', NEW.nome, ' - CPF: ', NEW.cpf));
$$

DELIMITER ;

-- ============================================
-- VIEWS
-- ============================================

CREATE VIEW vw_viagens_finalizadas AS
SELECT v.id_viagem, v.data_viagem, v.status,
       m.nome AS motorista, ve.modelo AS veiculo, r.origem, r.destino
FROM viagens v
JOIN motoristas m ON v.id_motorista = m.id_motorista
JOIN veiculos ve ON v.id_veiculo = ve.id_veiculo
JOIN rotas r ON v.id_rota = r.id_rota
WHERE v.status = 'finalizada';

CREATE VIEW vw_usuarios_grupos AS
SELECT u.id_usuario, u.nome, u.email, g.nome_grupo
FROM usuarios u
JOIN grupos_usuarios g ON u.id_grupo = g.id_grupo;

CREATE VIEW vw_passageiros_viagens AS
SELECT p.id_passageiro, p.nome AS passageiro, p.cpf, p.status AS status_passageiro,
       v.id_viagem, v.status AS status_viagem, r.origem, r.destino
FROM passageiros p
JOIN viagens_passageiros vp ON p.id_passageiro = vp.id_passageiro
JOIN viagens v ON vp.id_viagem = v.id_viagem
JOIN rotas r ON v.id_rota = r.id_rota;

-- ============================================
-- USUÁRIO DO SISTEMA
-- ============================================

CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY 'senha123';
GRANT ALL PRIVILEGES ON transporte.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;

-- ============================================
-- TESTE
-- ============================================

INSERT INTO grupos_usuarios (id_grupo, nome_grupo, descricao)
VALUES ('ADM', 'Administradores', 'Usuários com acesso total ao sistema')
ON DUPLICATE KEY UPDATE nome_grupo = 'Administradores';

INSERT INTO usuarios (id_usuario, nome, email, senha, id_grupo)
VALUES (1, 'Administrador', 'admin@transporte.com', 'senha123', 'ADM')
ON DUPLICATE KEY UPDATE email = 'admin@transporte.com';

SELECT * FROM usuarios;
SELECT * FROM grupos_usuarios;
SELECT * FROM motoristas;
SELECT * FROM passageiros;
SELECT *FROM veiculos;
SELECT * FROM viagens;
SELECT * FROM rotas;

use transporte;
