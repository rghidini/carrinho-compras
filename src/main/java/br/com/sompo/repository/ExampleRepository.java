package br.com.sompo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.sompo.mapper.ExampleRowMapper;
import br.com.sompo.model.Example;

@Repository
@PropertySource("classpath:Example.xml") //TODO Indicar o nome do arquivo onde esta sua query
public class ExampleRepository{
	
	//TODO classe necessaria apenas se for utilizar JDBCTemplate e query no arquivo XML
	
	private static final String DTVECTOFIM = "@DTVECTOFIM"; //TODO alterar pelo nome dos parametros da sua query
	private static final String DTVECTOINI = "@DTVECTOINI";
	private static final String NRCOLAB = "@NRCOLAB";
	private static final String NRCORRT = "@NRCORRT";
	private static final String CODSUCUR = "@CODSUCUR";

	@Autowired
	private Environment env;
	
	@Qualifier("jdbcSompo")
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Example> consultaParcela(Long codigoSucursal, 
			Long numeroCorretor, Long numeroColaborador, String dataVencimentoIni, String dataVencimentoFim){
		
		String query = env.getProperty("query"); //TODO Indicar o nome do entry correspondente a query
		query = query.replaceAll(CODSUCUR, codigoSucursal.toString());
		query = query.replaceAll(NRCORRT, numeroCorretor.toString());
		query = query.replaceAll(NRCOLAB, numeroColaborador.toString());
		query = query.replaceAll(DTVECTOINI, dataVencimentoIni);
		query = query.replaceAll(DTVECTOFIM, dataVencimentoFim);
		
		return jdbcTemplate.query(query, new ExampleRowMapper());
		
	}
	
}
