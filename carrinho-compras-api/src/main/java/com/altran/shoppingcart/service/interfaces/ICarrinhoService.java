package com.altran.shoppingcart.service.interfaces;

import java.util.List;

import com.altran.shoppingcart.model.Carrinho;
import com.altran.shoppingcart.model.vo.CarrinhoVO;


public interface ICarrinhoService {
	
	List<Carrinho> getCarrinhoByUserId(String id);
	Carrinho fecharCompras(CarrinhoVO carrinho);
	Carrinho updateCart(Carrinho carrinho);
	
}
