let palavraSelecionada, dicaSelecionada, letrasErradas, letrasCorretas, tentativas;

const dicaElemento = document.getElementById("dica");
const tentativasElemento = document.getElementById("tentativasRestantes");
const palavraSecretaElemento = document.getElementById("palavraSecreta");
const letrasErradasElemento = document.getElementById("letrasErradas");
const tecladoElemento = document.getElementById("teclado");
const mensagemFinalElemento = document.getElementById("mensagemFinal");
const mensagemElemento = document.getElementById("mensagem");
const palavraCorretaElemento = document.getElementById("palavraCorreta");
const jogarNovamenteBtn = document.getElementById("jogarNovamente");

async function iniciarJogo() {
  try {
    const resposta = await fetch('/forca/palavra');
    if (!resposta.ok) throw new Error("Erro ao buscar palavra");
    const dados = await resposta.json();

    palavraSelecionada = dados.palavra; // mantém original para mostrar no final
    dicaSelecionada = dados.dica;
    letrasErradas = [];
    letrasCorretas = [];
    tentativas = 6;

    dicaElemento.textContent = dicaSelecionada;
    tentativasElemento.textContent = tentativas;
    mensagemFinalElemento.classList.add("hidden");
    tecladoElemento.innerHTML = "";
    letrasErradasElemento.textContent = "";

    criarTeclado();
    atualizarPalavraSecreta();

  } catch (error) {
    console.error("Erro ao iniciar jogo:", error);
    dicaElemento.textContent = "Não foi possível carregar a palavra.";
  }
}

function criarTeclado() {
  const letras = "abcdefghijklmnopqrstuvwxyz";
  letras.split("").forEach(letra => {
    const botao = document.createElement("button");
    botao.textContent = letra;
    botao.addEventListener("click", () => verificarLetra(letra, botao));
    tecladoElemento.appendChild(botao);
  });
}

async function verificarLetra(letra, botao) {
  botao.disabled = true;

  try {
    const resposta = await fetch('/forca/tentar', {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ letra: letra.toLowerCase() })
    });
    if (!resposta.ok) throw new Error('Erro na tentativa');

    const resultado = await resposta.json();
    // Exemplo: { acertou: true, posicoes: [0, 3] }

    if (resultado.acertou) {
      if (!letrasCorretas.includes(letra.toLowerCase())) {
        letrasCorretas.push(letra.toLowerCase());
      }
    } else {
      if (!letrasErradas.includes(letra.toLowerCase())) {
        letrasErradas.push(letra.toLowerCase());
        tentativas--;
      }
    }

    atualizarPalavraSecreta();
    atualizarEstado();

  } catch (error) {
    console.error("Erro ao tentar letra:", error);
  }
}

function atualizarPalavraSecreta() {
  const palavraMinuscula = palavraSelecionada.toLowerCase();
  palavraSecretaElemento.innerHTML = palavraMinuscula
    .split("")
    .map(letra => (letrasCorretas.includes(letra) ? letra : "_"))
    .join(" ");
}

function atualizarEstado() {
  tentativasElemento.textContent = tentativas;
  letrasErradasElemento.textContent = `Letras erradas: ${letrasErradas.join(", ")}`;

  const palavraMinuscula = palavraSelecionada.toLowerCase();
  const venceu = !palavraMinuscula.split("").some(letra => !letrasCorretas.includes(letra));

  if (venceu) {
    mostrarMensagem("Parabéns! Você venceu!");
  } else if (tentativas === 0) {
    mostrarMensagem("Fim de jogo!");
  }
}

function mostrarMensagem(texto) {
  mensagemElemento.textContent = texto;
  palavraCorretaElemento.textContent = palavraSelecionada;
  mensagemFinalElemento.classList.remove("hidden");
}

jogarNovamenteBtn.addEventListener("click", iniciarJogo);
iniciarJogo();
