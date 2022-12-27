package com.lupo.rsaencrypter;

import java.awt.Desktop;
import java.math.BigInteger;
import java.net.URI;
import java.util.Base64;
import java.util.Random;

public class RSA {
	
	
	public static BigInteger[][] generateKeys() {
		
		BigInteger[][] keyArray = new BigInteger[2][2];
		
		Random random = new Random();
		
		
		BigInteger prime1 = BigInteger.probablePrime(1024, random);
		BigInteger prime2 = BigInteger.probablePrime(1024, random);
		System.out.println("first prime number: " + prime1.toString());
		System.out.println("second prime number: " + prime2.toString());
		
		BigInteger composite = prime1.multiply(prime2);
		System.out.println("composite number: " + composite.toString());
		
		BigInteger totient = prime1.subtract(new BigInteger("1")).multiply(prime2.subtract(new BigInteger("1")));
		System.out.println("totient: " + totient.toString());
		
//		int[] fermatArray = new int[]{2 + 1, 2^2 + 1, 2^2^2 + 1, 2^2^2^2 + 1, 2^2^2^2^2 + 1};
//		int e = random.nextInt(fermatArray.length);
		
		BigInteger publicExponent = new BigInteger("65537");
		

		
//		while(true) {
//			
//			publicExponent = BigInteger.probablePrime(4, random);
//			System.out.println("Checking Prime: " + publicExponent);
//			System.out.println(totient.mod(publicExponent).toString());
//			
//			if (totient.mod(publicExponent).equals(new BigInteger("0"))) {
//				System.out.println("probably works");
//
//				if (totient.compareTo(publicExponent) == 1) break;	
//			}
//				
//		}
		
		System.out.println("e: " + publicExponent);
		
		
		
		
		
		BigInteger privateExponent = null;
		//BigInteger starter = BigInteger.probablePrime(1024, random);
		int k = 0;
		
		while(true) {
			
			BigInteger[] privateExponentArray = (new BigInteger("1")).add(totient.multiply(new BigInteger(Integer.toString(k)))).divideAndRemainder(publicExponent);
			
			System.out.println("Possible D: " + (new BigInteger("1")).add(totient.multiply(new BigInteger(Integer.toString(k)))));
			System.out.println("Possible D mod: " + privateExponentArray[1].toString());
			
			if (privateExponentArray[1].equals(new BigInteger("0"))) {
				
				privateExponent = privateExponentArray[0];
				break;
				
			} else {
				
				k++;
				
			}
				
		}
		
		
		
		
		keyArray[0][0] = publicExponent;
		keyArray[0][1] = composite;
		keyArray[1][0] = privateExponent;
		keyArray[1][1] = composite;
		
		return keyArray;
		
	}
	
	public static String encryptMessage(String message, String publicKey, String header) {
		
		try {
			
			publicKey = publicKey.trim();
			
			BigInteger messageInt = new BigInteger(message.getBytes());
			
			System.out.println(messageInt);
			
			String[] publicKeyArray = publicKey.substring(1, publicKey.length() - 1).split(":");
			
			BigInteger cypherInt = messageInt.modPow(Base62.decodeInt(publicKeyArray[0]), Base62.decodeInt(publicKeyArray[1]));
			
			System.out.println(cypherInt);
			
			if (header.equals("")) header = "No Description";
			
			return "{[ " + header + " ] [" + Base62.encodeInt(cypherInt) + "]}";
			
		} catch (Exception e) {
			
			return "Make sure your key is formatted correctly. Did you copy and paste it correctly.";
			
			
		}
		
	}
	
	public static String decryptMessage(String message) {
		
		try {
			
			message = message.trim();

			String contentTrailing = message.split("\\[")[2];
			
			System.out.println(contentTrailing + "bbbbbbb");
			String content = contentTrailing.substring(0, contentTrailing.length() - 2);
			System.out.println(content + "cccccccc");
			
			
			
			BigInteger cypherInt = Base62.decodeInt(content);
			
			
			
			BigInteger messageInt = cypherInt.modPow(Main.privateKey[0], Main.privateKey[1]);
			
			
			
			String messageString = new String(messageInt.toByteArray());
			
			if(messageString.length() >= 8) {
			
				if(messageString.substring(0, 8).contentEquals("https://")) Desktop.getDesktop().browse(new URI(messageString));
				
			}
				
			return messageString;
			
		} catch (Exception e) {
			
			return "Please makes sure you format your message correctly. Did you copy and paste it correctly?";
			
		}
		
	}
	
//	public static String encodeInt(BigInteger integer) {
//		
//		return integer.toString(10); 
//		
////		byte[] bytes = integer.toByteArray();
////		return Base64.getEncoder().encodeToString(bytes);
//		
//		
//		
//	}
//	
//	public static BigInteger decodeInt(String string) {
//		
//		return new BigInteger(string, 10);
//		
////		byte[] bytes = Base64.getDecoder().decode(string);
////		return new BigInteger(bytes);
//		
//		
//	}
	
}


