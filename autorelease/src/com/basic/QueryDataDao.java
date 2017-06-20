package com.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class QueryDataDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List queryDataBySql(String sql){
		List list = jdbcTemplate.queryForList(sql);
		return list ;
	}
	
	public List queryObjectBySql(String sql,Class c){
		System.out.println(sql);
		List list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(c));
		return list ;
	}
	
	public void updateDataBySql(String sql){
		jdbcTemplate.update(sql);
	}
	
	public List queryDataBySql(String dataSource,String sql){
		DynamicDataSourceHolder.setDataSourceType(dataSource);
        System.out.println("------------>"+dataSource);
		List list = jdbcTemplate.queryForList(sql);
		return list ;
	}
}
