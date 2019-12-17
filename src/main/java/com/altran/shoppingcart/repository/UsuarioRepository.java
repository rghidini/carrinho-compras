package com.altran.shoppingcart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.altran.shoppingcart.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	List<Usuario> findAll();
}
