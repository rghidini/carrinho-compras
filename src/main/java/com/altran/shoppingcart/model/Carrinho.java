package com.altran.shoppingcart.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Document
public class Carrinho implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@NotBlank(message = "Id do usuário deve estar preenchido")
	private Long idUsuario;
	
	@NotBlank(message = "Id do item deve estar preenchido")
	private Long idItem;
	
	@Positive(message = "Quantidade precisa ser maior que zero")
	private Integer quantidade;
	
	@Positive(message = "O valor precisa ser maior que zero")
	private BigDecimal valorUnitario;
	
	@Positive(message = "O valor precisa ser maior que zero")
	private BigDecimal valorTotal;
	
}
