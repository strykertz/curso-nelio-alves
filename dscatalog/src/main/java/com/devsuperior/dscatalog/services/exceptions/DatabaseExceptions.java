package com.devsuperior.dscatalog.services.exceptions;

public class DatabaseExceptions extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DatabaseExceptions(String msg) {
		super(msg);
	}


/*
 * Passo 1: Criar uma classe de exception que herda de RuntimeException e crie um construtor que receba a string com o erro herdado.
 * Passo 2: No service o objeto optional deve ser criado uma lamba com throwOrElse instanciando este erro.
 * Passo 3: Criar uma classe StandardError que deverá compor o corpo de retorno da requisição.
 * Passo 4: Criar uma classe ResourceExceptionHandler que será o controllerAdvice. Neste método é feito a parametrização de
 * informações personalizadas que irá constar no body do retorno da requisição.
 * 
 * 
 * */
}
