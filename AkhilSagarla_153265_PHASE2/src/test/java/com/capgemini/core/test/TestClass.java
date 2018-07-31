package com.capgemini.core.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.*;

import com.capgemini.core.beans.Customer;
import com.capgemini.core.beans.Wallet;
import com.capgemini.core.exception.InsufficientBalanceException;
import com.capgemini.core.exception.InvalidInputException;
import com.capgemini.core.service.WalletService;
import com.capgemini.core.service.WalletServiceImpl;

public class TestClass {

static WalletService walletService;
@BeforeClass
public static void setUpBeforeClass() throws Exception 
{
walletService = new WalletServiceImpl();
 
}

@AfterClass
public static void tearDownAfterClass() throws Exception {
}

@Before
public void setUp() throws Exception {
}

@After
public void tearDown() throws Exception {
}

@Test
public void testCreateAccount() throws InvalidInputException {
Customer customer = new Customer();
Wallet wallet = new Wallet();
String name = "Akhil";
String number = "9951610705";
BigDecimal amount = new BigDecimal(5000);
 
customer.setName(name);
customer.setMobileNo(number);
wallet.setBalance(amount);
customer.setWallet(wallet);
String password="Sag@213";
 
customer = walletService.createAccount(name, number, amount,password);
 
assertNotEquals(null, customer);
}
 
@Test
public void testshowBalance() throws InvalidInputException {
String number = "9951610705";
 
Customer customer = walletService.showBalance(number);
 
assertNotEquals(null,customer);
}
 
@Test(expected = InsufficientBalanceException.class)
public void testWithdraw() throws InvalidInputException, InsufficientBalanceException {
String name = "Akhil";
String mobileNumber = "9951610705";
BigDecimal balance = new BigDecimal("2000");
String password="Sag@213";
 
walletService.createAccount(name, mobileNumber, balance,password);
 
BigDecimal amount = new BigDecimal("3000");
 
 
walletService.withdrawAmount(mobileNumber,amount);
}
 
@Test
public void testDepositAmount() throws InvalidInputException {
String name = "Akhil";
String mobileNumber = "9951610705";
BigDecimal balance = new BigDecimal(3000);
String password="Sag@213";
 
Customer customer = walletService.createAccount(name, mobileNumber, balance,password);
Customer customer1 = walletService.depositAmount(mobileNumber, new BigDecimal("3000"));
BigDecimal balanceOne=new BigDecimal("6000");
assertNotSame(balanceOne , customer1.getWallet().getBalance());
 
}
 
@Test(expected = InvalidInputException.class)
public void testMobileNumber() throws InvalidInputException, InsufficientBalanceException {
String name = "Akhil";
String mobileNumber = "995161";
BigDecimal balance = new BigDecimal("3000");
String password="Sag@213";
 
walletService.createAccount(name, mobileNumber, balance,password);
 
BigDecimal amount = new BigDecimal("3000");
 
walletService.withdrawAmount(mobileNumber, amount);
}


}