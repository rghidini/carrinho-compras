package com.altran.shoppingcart.service.interfaces;

import java.util.List;

import com.altran.shoppingcart.model.Usuario;
import com.altran.shoppingcart.model.vo.UsuarioVO;

public interface IUsuarioService {
	
	List<Usuario> getAll();
	Usuario getUsuarioById(String id);
	Usuario createUser(UsuarioVO user);
	void deleteUser(String id);
	Usuario updateUsuario(Usuario usuario);

}
