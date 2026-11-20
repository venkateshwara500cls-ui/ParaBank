package com.parabank.utils;

import java.util.Random;

public class RegistrationData {

	private static final Random random = new Random();
	
	public static String  firstName;
	public static String lastName;
	public static String address;
	public static String city;
	public static String state;
	public static String zipCode;
	public static String phoneNumber;
	public static String ssn;
	public static String username;
	public static String password;
	
	public static void generateRegistrationData() {
		
		firstName = "Komal";
		lastName = "Sutar";
		address = random.nextInt(9999) + " Main St";
		city = "New York";
		state = "NY";
		zipCode = String.format("%05d", random.nextInt(100000));
		phoneNumber = String.format("%03d-%03d-%04d", random.nextInt(1000), random.nextInt(1000), random.nextInt(10000));
		ssn = String.format("%03d-%02d-%04d", random.nextInt(1000), random.nextInt(100), random.nextInt(10000));
		username = generateUsername();
		password = generatePassword();
	}
	
	private static String generateUsername() {
		int number = 1000+ random.nextInt(10000);
	    return "Komal" + number;
	}
	
	private static String generatePassword() {
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower = "abcdefghijklmnopqrstuvwxyz";
		String number = "0123456789";
		String special = "@#$%";
		
		String all = upper + lower + number + special;
		StringBuilder pwd = new StringBuilder();
		
		pwd.append(upper.charAt(random.nextInt(upper.length())));
		pwd.append(lower.charAt(random.nextInt(lower.length())));
		pwd.append(number.charAt(random.nextInt(number.length())));
		pwd.append(special.charAt(random.nextInt(special.length())));
		
		while (pwd.length() < 10) {
			pwd.append(all.charAt(random.nextInt(all.length())));
		}
	
		
		return pwd.toString(); 
		
		
	}
		
	
}
