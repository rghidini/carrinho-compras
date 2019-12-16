package br.com.sompo.utils;

import java.util.Locale;
import java.util.ResourceBundle;


public final class Utils {

	private static final ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle("messages", new Locale("pt","BR"));

	private Utils() {}

	public static String getMessage(String key) {
		return MESSAGE_BUNDLE.getString(key);
	}
	
}