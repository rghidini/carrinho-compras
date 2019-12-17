package com.altran.shoppingcart.service.interfaces;

import java.util.List;

import com.altran.shoppingcart.model.Carrinho;


public interface ICarrinhoService {
	
	List<Carrinho> getCarrinhoByUserId(Long id);
	List<Carrinho> createCart(List<Carrinho> carrinho);
	List<Carrinho> updateCart(List<Carrinho> carrinho);
	
}
