package com.tomtom.amelinium.db.logic;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.tomtom.amelinium.db.config.Config;

/**
 * Handles initialization of the MyBatis SqlSessionFactory.
 * 
 * @author Natasza.Nowak@tomtom.com
 */
public class Initializer {
	private Reader reader = null;
	
	/**
	 * Initializes MyBatis SqlSessionFactory mapper.
	 */
	public SqlSessionFactory initializeSqlMapper() {
		SqlSessionFactory sqlMapper = null;
		try {
			this.reader = Resources.getResourceAsReader(Config.RESOURCE);
			sqlMapper = new SqlSessionFactoryBuilder().build(this.reader);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return sqlMapper;
	}
}
