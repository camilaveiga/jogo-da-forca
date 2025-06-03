package com.example.jogo_da_forca.service;
import com.example.jogo_da_forca.dto.ResultadoTentativa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.example.jogo_da_forca.dto.Palavra;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

@Service
public class PalavraService {

    private Palavra palavraAtual;
    private List<Palavra> palavras;

    public PalavraService() {
        carregarPalavras();
    }

    private void carregarPalavras() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("json/palavras.json");
            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo json/palavras.json não encontrado no classpath");
            }
            this.palavras = mapper.readValue(inputStream, new TypeReference<List<Palavra>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Palavra getPalavraAleatoria() {
        if (palavras == null || palavras.isEmpty()) return null;
        Random rand = new Random();
        palavraAtual = palavras.get(rand.nextInt(palavras.size()));
        return palavraAtual;
    }

    public Palavra getPalavraAtual() {
        return palavraAtual;
    }

    public ResultadoTentativa tentarLetra(char letra) {
        ResultadoTentativa resultado = new ResultadoTentativa();
        resultado.setAcertou(false);

        if (palavraAtual == null || palavraAtual.getPalavra() == null) return resultado;

        String palavra = palavraAtual.getPalavra().toUpperCase();
        letra = Character.toUpperCase(letra);

        List<Integer> posicoes = new java.util.ArrayList<>();

        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == letra) {
                posicoes.add(i);
            }
        }

        if (!posicoes.isEmpty()) {
            resultado.setAcertou(true);
            resultado.setPosicoes(posicoes);
        }

        return resultado;
    }

}
