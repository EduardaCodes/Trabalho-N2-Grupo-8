// ===============================
// BANCO NoSQL - MONGODB
// SISTEMA DE TRANSPORTE PRIVADO
// ===============================

use('transporte');

// Criar coleções (se ainda não existirem)
db.createCollection('log_eventos');
db.createCollection('avaliacoes');

// Registrar eventos do sistema (auditoria)
function registrarEvento(componente, mensagem) {
  db.log_eventos.insertOne({
    componente: componente,
    mensagem: mensagem,
    timestamp: new Date()
  });
}

function avaliarMotorista(motoristaId, passageiroId, nota, comentario) {
    db.avaliacoes.insertOne({
        motorista_id: motoristaId,
        passageiro_id: passageiroId,
        nota: nota,
        comentario: comentario,
        data: new Date()
    });

    db.log_eventos.insertOne({
        componente: "AVALIACAO",
        mensagem: `Passageiro ${passageiroId} avaliou motorista ${motoristaId} com nota ${nota}`,
        timestamp: new Date()
    });
}


// Exemplos — Execute manualmente se quiser testar:
// avaliarMotorista("MOT123", "Lucas", 5, "Motorista muito educado");
// registrarEvento("LOGIN", "Usuário admin entrou no sistema");
