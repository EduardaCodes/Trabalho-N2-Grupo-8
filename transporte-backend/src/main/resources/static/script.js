const API_URL = "http://localhost:8080";

// Envio do formulário de login
document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const email = document.getElementById("email").value.trim();
  const senha = document.getElementById("senha").value.trim();
  const errorDiv = document.getElementById("error");

  errorDiv.textContent = ""; // limpa erro anterior

  const tipo = document.querySelector('input[name="tipoLogin"]:checked')?.value || "usuario";

  // Verificação simples de campos
  if (!email || !senha) {
    errorDiv.textContent = "Preencha todos os campos.";
    return;
  }

  let endpoint;
  let payload;

  if (tipo === "usuario") {
    endpoint = `${API_URL}/usuarios/login`;
    payload = { email, senha };
  } else {
    endpoint = `${API_URL}/passageiros/login`;
    payload = { cpf: email, senha };
  }

  try {
    const res = await fetch(endpoint, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    // Se backend estiver offline
    if (!res) {
      errorDiv.textContent = "Servidor não respondeu. Verifique se o backend está rodando.";
      return;
    }

    // Tratamento de erros da API
    if (!res.ok) {
      if (res.status === 401 || res.status === 404) {
        errorDiv.textContent = "Credenciais incorretas.";
      } else if (res.status === 0) {
        errorDiv.textContent = "Erro ao conectar ao servidor.";
      } else {
        errorDiv.textContent = `Erro inesperado: ${res.status}`;
      }
      return;
    }

    const user = await res.json();
    user.tipo = tipo;

    // Salva usuário logado
    localStorage.setItem("usuario", JSON.stringify(user));

    // Redirecionamento
    window.location.href = tipo === "usuario"
      ? "dashboard.html"
      : "dashboard-passageiro.html";

  } catch (err) {
    console.error("Erro no login:", err);

    if (err.message.includes("Failed to fetch")) {
      errorDiv.textContent = "Não foi possível conectar ao servidor. Certifique-se de que o backend está rodando.";
    } else {
      errorDiv.textContent = "Erro inesperado no login.";
    }
  }
});
