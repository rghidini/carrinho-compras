package br.com.sompo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sompo.model.Example;
import br.com.sompo.repository.interfaces.IExampleRepository;
import br.com.sompo.service.interfaces.IExampleService;

@Service
public class ExampleService implements IExampleService{

	private IExampleRepository repository;

	@Autowired
	private ExampleService(IExampleRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Example getExampleById(Long id) {
		return repository.findbyId(id);
	}

}