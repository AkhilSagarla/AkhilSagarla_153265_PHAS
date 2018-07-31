package com.capgemini.core.pl;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.beans.Wallet;
import com.capgemini.core.exception.InsufficientBalanceException;
import com.capgemini.core.exception.InvalidInputException;
import com.capgemini.core.service.WalletService;
import com.capgemini.core.service.WalletServiceImpl;
//import com.cg.parallelproject.pl.Client;

public class Client {
	WalletService service;

	Client() {
		service = new WalletServiceImpl();
	}
	
	
	
	public  void Account() {
		Scanner scanner=new Scanner(System.in);
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		
		System.out.println("*******Welcome to PaymentWallet *******");
		System.out.println("Please Enter Your Choice");
		while(true) {
		System.out.println("1. New Customer");
		System.out.println("2. Exsisting Customer");
		System.out.println("3. Exit Apllication");
		
		int choice1=scanner.nextInt();
		switch(choice1) {
		case 1:
			System.out.print("Enter Your Name: ");
			String name = scanner.next();

			System.out.print("Enter mobileNumber: ");
			String mobileNumber = scanner.next();

			System.out.print("Enter Amount: ");
			BigDecimal amount = scanner.nextBigDecimal();
			
			/*System.out.println("Enter your username");
			String username = scanner.next();*/
			
			
			System.out.println("Enter your password");
			String password = scanner.next();
			

			try {
				customer = service.createAccount(name, mobileNumber, amount, password);
				System.out.println("Your account has successfully registered");
			} catch (InvalidInputException e) {
				System.out.println(e.getMessage());
			}
			break;
			
		case 2:System.out.println("please enter your MobileNo ");
		String no=scanner.next();
		System.out.println("please enter your password ");
		String epassword=scanner.next();
		customer=service.userPassword(no, epassword);
		if(customer.getMobileNo().equals(no)&&customer.getPassword().equals(epassword)) {
	
			while(true) {
		System.out.println("Please Enter your choice");
		System.out.println("1. Show Balance");
		System.out.println("2. Deposit Amount");
		System.out.println("3. Withdraw Amount");
		System.out.println("4. Fund Transfer");
		System.out.println("5. Exit Application");
		System.out.println("6.go to Home Page");

		Scanner sc = new Scanner(System.in);

		
		int choice = sc.nextInt();

		switch (choice) {
		case 1:
			/*System.out.println("Enter mobile number");
			mobileNumber = sc.next();*/

			try {
				customer = service.showBalance(no);
				System.out.print("The balance in account " + customer.getName());
				System.out.println(" is " + customer.getWallet().getBalance());
			} catch (InvalidInputException e3) {
				System.out.println(e3.getMessage());
			}
			break;
		case 2:
			/*System.out.println("Enter mobile number");
			mobileNumber = sc.next();*/

			System.out.println("Enter amount to be deposited");
			amount = sc.nextBigDecimal();

			try {
				customer = service.depositAmount(no, amount);
				System.out.println("Successfully deposited");
				System.out.println("Account balance is: " + customer.getWallet().getBalance());
			} catch (InvalidInputException e2) {
				System.out.println(e2.getMessage());
			}
			break;
		case 3:
			/*System.out.println("Enter mobile number");
			mobileNumber = sc.next();*/

			System.out.println("Enter amount to be withdrawn");
			amount = sc.nextBigDecimal();

			try {
				customer = service.withdrawAmount(no, amount);
				System.out.println("Successfully withdrawn");
				System.out.println("Account balance is: " + customer.getWallet().getBalance());
			} catch (InvalidInputException e1) {
				System.out.println(e1.getMessage());
			} catch (InsufficientBalanceException e) {
				System.out.println(e.getMessage());
			}
			break;

		case 4:
			/*System.out.print("Enter source mobile number: ");
			String sourceMobile = sc.next();*/

			System.out.print("Enter target mobile number: ");
			String targetMobile = sc.next();

			System.out.println("Enter amount to be transferred");
			amount = sc.nextBigDecimal();

			try {
				customer = service.fundTransfer(no, targetMobile, amount);
				System.out.println("amount "+ amount +" has successfully transferred from your account " + customer.getName());
				System.out.println("And now your balance is " + customer.getWallet().getBalance());

			} catch (InvalidInputException e) {
				System.out.println(e.getMessage());
			} catch (InsufficientBalanceException e) {
				System.out.println(e.getMessage());

			}
			break;
		case 5:
			System.out.println("Thank you for using our services");
			System.out.println("Good Bye");
			System.exit(0);
			break;
			
		case 6:
			Account();
			break;

		default:
			System.out.println("Please enter valid choice");
			break;
		}
			}
		}else {
			System.out.println("Please Enter valid username and password");
		}break;
		
		case 3:
			System.out.println("Thank you for using our services");
		System.out.println("Good Bye");
		System.exit(0);
		
		default:
			System.out.println("Please enter valid choice");
			break;

			
		}
	}
	}

	public static void main(String[] args) {
		Client client=new Client();
		while (true) {
			client.Account();
		}
	}

}
