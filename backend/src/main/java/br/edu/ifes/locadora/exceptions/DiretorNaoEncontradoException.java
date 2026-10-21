package br.edu.ifes.locadora.exceptions;

public class DiretorNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DiretorNaoEncontradoException(String msg) {
        super(msg);
    }
}
