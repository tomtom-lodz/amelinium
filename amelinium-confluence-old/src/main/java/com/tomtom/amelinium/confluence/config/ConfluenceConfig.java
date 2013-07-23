package com.tomtom.amelinium.confluence.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfluenceConfig {

	/**
	 * User name used to login to Confluence.
	 */
	public String USER="";

	/**
	 * Password used to login to Confluence.
	 */
	public String PASS="";

	/**
	 * URL of the Confluence server.
	 */
	public String SERVER="http://127.0.0.1/";
	
	public ConfluenceConfig(String user, String password, String server) {
		this.USER = user;
		this.PASS = password;
		this.SERVER = server;
	}

	/**
	 * Loads variables from property file
	 */
	public ConfluenceConfig() {
		String configName = System.getProperty("amelinium.config");
		if(configName!=null) {
			Properties properties = new Properties();
			try {
			    properties.load(new FileInputStream(configName));
			    String user = properties.getProperty("confluence.user");
			    if(user!=null) {
			    	USER = user;
			    }
			    String pass = properties.getProperty("confluence.password");
			    if(pass!=null) {
			    	PASS = pass;
			    }
			    String server = properties.getProperty("confluence.server");
			    if(server!=null) {
			    	SERVER = server;
			    }
			} catch (IOException e) {
			}
		}
	}
	
}
