package com.capgemini.core.service;

import java.math.BigDecimal;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.beans.Wallet;
import com.capgemini.core.exception.InsufficientBalanceException;
import com.capgemini.core.exception.InvalidInputException;
import com.capgemini.core.repo.WalletRepo;
import com.capgemini.core.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{

	private WalletRepo repo;

	public WalletServiceImpl() {
		repo = new WalletRepoImpl();
	}
	public Customer userPassword(String mobileNo,String password) {
		try {
			if(isValid(mobileNo)) {
				Customer customer=repo.findOne(mobileNo);
				if(customer!=null) {
					if(customer.getMobileNo().equals(mobileNo)&&customer.getPassword().equals(password)) {
						return customer;
					}
				}
				
			}
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount,String password) throws InvalidInputException {
		if(isValid(mobileNo)) {
			Wallet wallet = new Wallet();
			Customer customer = new Customer();
		
			wallet.setBalance(amount);
			customer.setName(name);
			customer.setMobileNo(mobileNo);
			customer.setWallet(wallet);
			customer.setPassword(password);
		
			repo.save(customer);

			return customer;
		}
		else throw new InvalidInputException();

	}

	public Customer showBalance(String mobileNo) throws InvalidInputException {
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException {
		if(isValid(sourceMobileNo) == false || isValid(targetMobileNo) == false) throw new InvalidInputException();
		Customer customer = withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		return customer;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException 
	{
		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValid(mobileNo)) 
		{
			Customer customer = repo.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().add(amount));
		
			repo.remove(mobileNo);
		
			if(repo.save(customer)) {
				return customer;
			}
		}
		return null;
	}

	public boolean isValid(String mobileNo) {
		if(mobileNo.matches("[1-9][0-9]{9}")) 
		{
			return true;
		}		
		else 
			return false;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException 
	{	
		
		Customer customer = repo.findOne(mobileNo);
		Wallet wallet = customer.getWallet();
		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(amount.compareTo(wallet.getBalance())>0) {
			throw new InsufficientBalanceException("Amount is not sufficient in your account");
		}
		
		if(isValid(mobileNo)) 
		{
			
			wallet.setBalance(wallet.getBalance().subtract(amount));
		
		
			repo.remove(mobileNo);
		
			if(repo.save(customer)) {
				return customer;
			}
			return null;
		}
		else throw new InvalidInputException("Enter valid mobile number");
	}
}
