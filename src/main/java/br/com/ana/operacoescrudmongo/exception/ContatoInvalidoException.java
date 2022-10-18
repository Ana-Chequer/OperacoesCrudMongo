package br.com.ana.operacoescrudmongo.exception;

public class ContatoInvalidoException extends RuntimeException {
	
	public static final long serialVersionUID = 1L;
	
	public ContatoInvalidoException (String mensagem) {
		super(mensagem);
	}
	
	

}
