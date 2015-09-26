package com.workpoint.mwallet.server.util;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class CryptoUtils {
	static CryptoUtils instance = new CryptoUtils();
	BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
	
	public String encryptPassword(String plainPass){
		return passwordEncryptor.encryptPassword(plainPass);
	}
	
	public static CryptoUtils getInstance(){
		return instance;
	}
	
	public boolean checkPassword(String plainPass, String encryptedPassword){
		return passwordEncryptor.checkPassword(plainPass, encryptedPassword);
	}
	
}
