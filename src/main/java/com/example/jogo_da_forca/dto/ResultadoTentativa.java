package com.example.jogo_da_forca.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ResultadoTentativa {
    private boolean acertou;
    private List<Integer> posicoes;

    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public List<Integer> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(List<Integer> posicoes) {
        this.posicoes = posicoes;
    }
}
