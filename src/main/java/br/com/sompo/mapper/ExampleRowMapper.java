package br.com.sompo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.sompo.model.Example;

public class ExampleRowMapper implements RowMapper<Example>{

	@Override
	public Example mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		//TODO classe necessaria apenas se for utilizar JDBCTemplate e query no arquivo XML
		
		Example example = new Example();
		
		example.setExampleId(rs.getLong(""));
		example.setExampleDescription(rs.getString(""));
		
		return example;
	}
	
}
