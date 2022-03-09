package com.revature.services;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.dao.AccountDao;
import com.revature.exceptions.OverdraftException;

/**
 * This class should contain the business logic for performing operations on Accounts
 */
public class AccountService {
	
	public AccountDao actDao;
	public static final double STARTING_BALANCE = 25d;
	
	public AccountService(AccountDao dao) {
		this.actDao = dao;
	}
	
	/**
	 * Withdraws funds from the specified account
	 * @throws OverdraftException if amount is greater than the account balance
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void withdraw(Account a, Double amount) {
		
		double newBalance = 0;
		
		newBalance = a.getBalance() - amount;
		
		if(newBalance > 0 && amount > 0) {
			a.setBalance(newBalance);
			
			System.out.println("Succesfully withdrawn from account");
			
			actDao.updateAccount(a);
		}
		else {
			System.out.println("You are trying to withdraw more $ than is in your account or have entered a negative dollar amount");
		}
		
	}
	
	/**
	 * Deposit funds to an account
	 * @throws UnsupportedOperationException if amount is negative
	 */
	public void deposit(Account a, Double amount) {
		if (!a.isApproved()) {
			
			System.out.println("Sorry, your account is not approved!");
			
			//throw new UnsupportedOperationException();
			
		}
		else if(amount > 0) {
		double newBalance = 0;
		
		newBalance = a.getBalance() + amount;
		
		a.setBalance(newBalance);
		
		System.out.println("Succesfully deposited to account");
		
		actDao.updateAccount(a);
		}
		
		else {
			System.out.println("The amount entered was a negative dollar amount. Please try again");
		}
	
	}
	
	/**
	 * Transfers funds between accounts
	 * @throws UnsupportedOperationException if amount is negative or 
	 * the transaction would result in a negative balance for either account
	 * or if either account is not approved
	 * @param fromAct the account to withdraw from
	 * @param toAct the account to deposit to
	 * @param amount the monetary value to transfer
	 */
	public void transfer(Account fromAct, Account toAct, double amount) {
		
		double newAmount = 0;
		double afterDeposit = 0;
		
		afterDeposit = fromAct.getBalance() - amount;
		
		newAmount = amount + toAct.getBalance();
		
		if(afterDeposit > 0) {
		
		fromAct.setBalance(afterDeposit);
		toAct.setBalance(newAmount);
		
		System.out.println("Funds Successfully Transfered");
		
		actDao.updateAccount(fromAct);
		actDao.updateAccount(toAct);
		}
		else {
			System.out.println("Insufficient Funds to Transfer");
		}
	}
	
	/**
	 * Creates a new account for a given User
	 * @return the Account object that was created
	 */
	public Account createNewAccount(User u) {
		return null;
	}
	
	/**
	 * Approve or reject an account.
	 * @param a
	 * @param approval
	 * @throws UnauthorizedException if logged in user is not an Employee
	 * @return true if account is approved, or false if unapproved
	 */
	public boolean approveOrRejectAccount(Account a, boolean approval) {
		return false;
	}
}
