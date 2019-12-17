package com.altran.shoppingcart.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
public class ItemCarrinhoVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Nome não pode ser em branco")
	private String nome;
	
	@Positive(message = "O valor precisa ser maior que zero")
	private BigDecimal valor;
	
	@Positive(message = "Quantidade precisa ser maior que zero")
	private Integer quantidade;
	
}
