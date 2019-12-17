package com.altran.shoppingcart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.altran.shoppingcart.model.Carrinho;

@Repository
public interface CarrinhoRepository extends MongoRepository<Carrinho, Long>{
	List<Carrinho> findByIdUsuario(String userId);
}
