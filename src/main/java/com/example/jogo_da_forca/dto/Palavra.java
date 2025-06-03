package com.example.jogo_da_forca.dto;


public class Palavra {

    private String palavra;

    private String tema;

    private String dica;


    public String getPalavra(){
        return palavra;
    }

    public void setPalavra(String palavra){
        this.palavra = palavra;
    }

    public String getTema(){
        return tema;
    }

    public void setTema(String tema){
        this.tema = tema;
    }

    public String getDica(){
        return dica;
    }

    public void setDica(String dica){
        this.dica = dica;
    }
}
