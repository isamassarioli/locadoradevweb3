package br.edu.ifes.locadora.exceptions;

public class ClasseNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClasseNaoEncontradoException(String msg) {
        super(msg);
    }
}
