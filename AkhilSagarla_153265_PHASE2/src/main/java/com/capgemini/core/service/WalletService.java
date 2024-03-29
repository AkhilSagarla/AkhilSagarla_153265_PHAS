package com.capgemini.core.service;

import java.math.BigDecimal;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.exception.InsufficientBalanceException;
import com.capgemini.core.exception.InvalidInputException;

public interface WalletService {
	public Customer userPassword(String mobileNo,String password);
	public Customer createAccount(String name ,String mobileno, BigDecimal amount,String password) throws InvalidInputException;
	public Customer showBalance (String mobileno) throws InvalidInputException;
	public Customer fundTransfer (String no,String targetMobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException;
	public Customer depositAmount (String mobileNo,BigDecimal amount ) throws InvalidInputException;
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException;

}
