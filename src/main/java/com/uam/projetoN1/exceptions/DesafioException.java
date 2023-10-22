package com.uam.projetoN1.exceptions;

public class DesafioException extends RuntimeException {

    private static final long serialVersionUID = -7491873932486289748L;

    private String menssagem;

    public DesafioException(String menssagem) {
        this.menssagem = menssagem;
    }

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }
}
