/**
 * 
 */
package com.scr.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author pavanibaradi
 *
 */
public abstract class PropertyLoader {

	private static Properties dbProperties = null;
	private static Properties confProperties = null;

	/**
	 * @return the dbProperties
	 */
	public static Properties getDbProperties() {
		if (dbProperties != null) {
			return dbProperties;
		}

		try {
			dbProperties = new Properties();
			FileInputStream inputStream = new FileInputStream("/StudentCourseRegistrationSystem/src/main/resources/dbqueries.properties");
			dbProperties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbProperties;
	}

	/**
	 * @return the confProperties
	 */
	public static Properties getConfProperties() {
		if (confProperties != null) {
			return confProperties;
		}

		try {
			confProperties = new Properties();
			FileInputStream inputStream = new FileInputStream("conf.properties");
			confProperties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return confProperties;
	}

}
