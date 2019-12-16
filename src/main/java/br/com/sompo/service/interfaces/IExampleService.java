package br.com.sompo.service.interfaces;

import org.springframework.stereotype.Service;

import br.com.sompo.model.Example;

@Service
public interface IExampleService {
	
	Example getExampleById(Long id);
	
}
