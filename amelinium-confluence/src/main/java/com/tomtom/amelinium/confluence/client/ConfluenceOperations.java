package com.tomtom.amelinium.confluence.client;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.swift.confluence.cli.ConfluenceClient;


/**
 * Performs operations on Confluence pages.
 * 
 * @author Natasza.Nowak@tomtom.com
 *
 */
public class ConfluenceOperations {

	/**
	 * Retrieves a specified Confluence Page from the server and returns contents as string.
	 * 
	 * @param server
	 *            The URL of the Confluence server.
	 * @param user
	 *            User name.
	 * @param password
	 *            Password.
	 * @param title
	 *            The name of the page.
	 * @param space
	 *            The name of the space where page is located.
	 * @return the page source from the specified location
	 */

	public static String getPageSource(String server, String user, String password, String space, String title) {
		try {
			title = URLDecoder.decode(title, "UTF-8");
			space = URLDecoder.decode(space, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Did not recognize UTF8?", e);
		}
		
		String action = "getPageSource";

		String[] params = { "--server", server, "--user", user, "--password", password, "--action", action, "--space", space, "--title", title };

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		ByteArrayOutputStream errbaos = new ByteArrayOutputStream();
		PrintStream errps = new PrintStream(errbaos);
		
		ConfluenceClient client = new MyConfluenceClient();
		client.setOut(ps);
		client.setErr(errps);

		client.doWork(params);

		String content;
		try {
			content = baos.toString("UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Did not recognize UTF8?",e);
		}

		String errcontent;
		try {
			errcontent = errbaos.toString("UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Did not recognize UTF8?",e);
		}
		if(!errcontent.isEmpty()) {
			throw new ConfluenceException(errcontent);
		}
		
		return content;
	}

	/**
	 * Replaces a specified page on the Confluence with the one given as a
	 * parameter.
	 * 
	 * @param server
	 *            The URL of the Confluence server.
	 * @param user
	 *            User name.
	 * @param password
	 *            Password.
	 * @param title
	 *            The name of the page.
	 * @param space
	 *            The name of the space where page is located.
	 * @param content
	 *            New content that will replace the old one.
	 * @return the page source from the specified location
	 */

	public static void updatePageSource(String server, String user, String password, String space, String title, String content) {
		try {
			title = URLDecoder.decode(title, "UTF-8");
			space = URLDecoder.decode(space, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Did not recognize UTF8?", e);
		}

		ByteArrayOutputStream errbaos = new ByteArrayOutputStream();
		PrintStream errps = new PrintStream(errbaos);
		
		String action = "addPage";

		String[] params = { "--server", server, "--user", user, "--password", password, "--action", action, "--space", space, "--title", title, "--content", content, "--replace" };

		ConfluenceClient client = new MyConfluenceClient();
		client.setErr(errps);

		client.doWork(params);
		
		String errcontent;
		try {
			errcontent = errbaos.toString("UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Did not recognize UTF8?",e);
		}
		if(!errcontent.isEmpty()) {
			throw new ConfluenceException(errcontent);
		}
	}

	public static void addPage(String server, String user, String password, String space, String parent, String title, String content) {
		try {
			title = URLDecoder.decode(title, "UTF-8");
			space = URLDecoder.decode(space, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Did not recognize UTF8?", e);
		}

		ByteArrayOutputStream errbaos = new ByteArrayOutputStream();
		PrintStream errps = new PrintStream(errbaos);
		
		String action = "addPage";

		String[] params = { "--server", server, "--user", user, "--password", password, "--action", action, "--space", space, "--parent", parent, "--title", title, "--content", content, "--replace" };

		ConfluenceClient client = new MyConfluenceClient();
		client.setErr(errps);

		client.doWork(params);
		
		String errcontent;
		try {
			errcontent = errbaos.toString("UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Did not recognize UTF8?",e);
		}
		if(!errcontent.isEmpty()) {
			throw new ConfluenceException(errcontent);
		}
	}
	
}
