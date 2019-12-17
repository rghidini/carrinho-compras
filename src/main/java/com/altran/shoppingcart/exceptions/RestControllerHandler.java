package com.altran.shoppingcart.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.GatewayTimeout;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class RestControllerHandler {
	
	@ExceptionHandler({})
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Não encontrado")
	public void handleNotFound(final Exception ex) {} 
	
	@ExceptionHandler({NoContentException.class})
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Não há conteúdo para enviar para esta solicitação")
	public void handleNoContent(final Exception ex) {} 
	
	@ExceptionHandler({NullPointerException.class, DuplicateKeyException.class, IllegalStateException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Erro interno no servidor")
	public void handleInternalServerError(final Exception ex) {
		log.error(ex.getMessage(), ex);
	}
	
	@ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Requisição inválida")
	public void handleBadRequest(final Exception ex) {
		log.error(ex.getMessage(), ex);
	}
	
	@ExceptionHandler({GatewayTimeout.class})
	@ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT, reason = "Tempo limite da requisição excedido")
	public void handleTimeout(final Exception ex) {
		log.error(ex.getMessage(), ex);
	}
	
	@ExceptionHandler({AsyncRequestTimeoutException.class})
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Serviço indisponível")
	public void handleServiceUnavailable(final Exception ex) {
		log.error(ex.getMessage(), ex);
	}

}
