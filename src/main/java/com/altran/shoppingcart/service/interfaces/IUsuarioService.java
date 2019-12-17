package com.altran.shoppingcart.service.interfaces;

import java.util.List;

import com.altran.shoppingcart.model.Usuario;

public interface IUsuarioService {
	
	List<Usuario> getAll();
	Usuario getUsuarioById(Long id);
	Usuario createUser(Usuario user);
	void deleteUser(Long id);
	Usuario updateUsuario(Usuario usuario);

}
