package com.tomtom.amelinium.confluence.client;

import org.swift.confluence.cli.ConfluenceClient;


/**
 * This class is only created to override
 * {@link MyConfluenceClient#standardFinish(String, String, String)} method to
 * return the <code>result</code>. It is used within {@link ConfluenceOperations} class.
 */

public class MyConfluenceClient extends ConfluenceClient {

	/**
	 * Overrides default implementation of the
	 * standardFinish(String,String,String) so that it returns only a
	 * <code>result</code> without the additional <code>message</code>.
	 * 
	 * @return the parameter <code>result</code>
	 * 
	 */

	@Override
	public String standardFinish(String message, String result, String encoding) throws ClientException {
		return result;
	}

}
