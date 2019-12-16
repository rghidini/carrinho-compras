package br.com.sompo.repository.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.sompo.model.Example;

@Repository
public interface IExampleRepository extends CrudRepository<Example, Long>{
	Example findbyId(Long id);
}
