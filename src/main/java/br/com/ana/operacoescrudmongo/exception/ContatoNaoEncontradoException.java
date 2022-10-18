package br.com.ana.operacoescrudmongo.exception;

public class ContatoNaoEncontradoException extends RuntimeException {
	
	public static final long serialVersionUID = 1L;
	
	public ContatoNaoEncontradoException (String mensagem) {
		super(mensagem);
	}
	
	

}
