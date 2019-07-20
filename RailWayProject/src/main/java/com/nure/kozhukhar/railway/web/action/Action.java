package com.nure.kozhukhar.railway.web.action;


import com.nure.kozhukhar.railway.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Abstract class for Actions
 *
 * @author Anatol Kozhukhar
 */
public abstract class Action implements Serializable {
	private static final long serialVersionUID = 8879403039606311780L;

	/**
	 * Execution method for command.
	 * 
	 * @return Address to go once the command is executed.
	 */
	public abstract String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException; //AppException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}