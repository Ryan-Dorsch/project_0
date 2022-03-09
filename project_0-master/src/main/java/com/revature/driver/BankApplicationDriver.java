package com.revature.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
import com.revature.beans.User;
import com.revature.beans.User.UserType;
import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoDB;
import com.revature.dao.UserDaoDB;
import com.revature.dao.UserDaoFile;
import com.revature.exceptions.UsernameAlreadyExistsException;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import com.revature.utils.SessionCache;
/**
 * This is the entry point to the application
 */
public class BankApplicationDriver {

	
	
	static User user = new User();
	static UserDaoDB uddb = new UserDaoDB();
	static AccountDaoDB addb = new AccountDaoDB();
	static UserService uService = new UserService(uddb, addb);
	static AccountService aService = new AccountService(addb);
	
	public static Scanner input = new Scanner(System.in);
//	static Logger log = Logger.getLogger(BankApplicationDriver.class);
	
	
	public static void main(String[] args) throws  SQLException, ClassNotFoundException {
		// your code here...
//		Scanner input = new Scanner(System.in);
		
		// TODO Auto-generated method stub
				//Step 1 = loading and registering the driver
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				String dbUrl = "jdbc:mysql://localhost:3306/revature";
				String username = "root";
				String password = "727749RaD";
				
				//step 2: establish connection to the DB server 
				Connection conn = DriverManager.getConnection(dbUrl, username, password);
				
				//step 3 = creating and running the queries
				String query = "select * from users";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
		
		
//		UserDaoDB userDao = new UserDaoDB();
//		User ryan = new User();
//		
//		ryan.setId(0);
//		ryan.setFirstName("ryan");
//		ryan.setLastName("dorsch");
//		ryan.setUsername("ryan");
//		ryan.setPassword("123");
//		ryan.setUserType(UserType.CUSTOMER);
//		
//		
////		User quinn = new User();
////		
////		quinn.setFirstName("quinn");
////		quinn.setLastName("mcconnell");
	
		
//		userDao.addUser(ryan);
//		userDao.addUser(quinn);
//		
//		userDao.getAllUsers();
		
		int choice = 0;
		int choose = 0;
		
		int id = 0;
		String firstName = null;
		String lastName = null;
		String username1 = null;
		String password1 = null;
		String email = null;
		User newUser = null;
		Boolean run = true;
		
		User currentUser = new User();
		
		while (choice <= 5 && run.equals(true)) {

			System.out.println("Welcome to the Bank of Ryan");
			System.out.println("                ");
			System.out.println("Please select the number that corresponds to which action you would like to perform : ");
			System.out.println("1. Create New User");
			System.out.println("2. Login With Existing User");
			System.out.println("3. List of Existing Users");
			System.out.println("4. Exit");
			System.out.println("5. Employee Actions");
			choice = input.nextInt();
			
			switch (choice) {
			
			case 1:
				
				userAdd();
				
				break;
				
			case 2:
				
				User u = userLogin();
				
				System.out.println("Would you like to continue?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				
				choose = input.nextInt();
				
				switch (choose) {
				
				case 1:
					
					loggedIn();
				
					break;
					
				case 2:
					
					run = false;
					System.out.println("Exiting Program");
				
					break;
					
				
				}
				break;
				
			case 3:
				
				getUserList();
				
				break;
				
			case 4:
				
				run = false;
				System.out.println("Exiting Program");
			
				break;
			
			case 5:
				employeeActions();
				
				break;
				
			
			}
		}
		
		
		
		
		
		//to use the get a list of all users 
//		List<User> userList = userDao.getAllUsers();
		
//		System.out.println(userList);
		
		//step 5 = closing resources 
		if (rs!= null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();		
	}



public static void userAdd() { 
	
	User TestUser = new User();
	
	
	UserDaoDB udb = new UserDaoDB();
	UserService usr = new UserService(udb, null);
	
//	String Username;
	
	System.out.println("Please enter first name : ");
	TestUser.setFirstName(input.next());
	
	System.out.println("Please enter last name : ");
	TestUser.setLastName(input.next());
	
	System.out.println("Please enter username you wish to use for this account : ");
	TestUser.setUsername(input.next());
	
	System.out.println("Please enter password you wish to use for this account : ");
	TestUser.setPassword(input.next());
	
	TestUser.setId(0);
	TestUser.setUserType(UserType.CUSTOMER);
	
	//to check to see if it already exists 
	try {
		usr.register(TestUser);

		//need to now make the TestUser become the User and get put into DB
	}
	catch (UsernameAlreadyExistsException e) {
		
		System.out.println("Username Already Exists!");
	}
	
}	
public static User userLogin() { //getUser
	
	
	System.out.println("Please enter Username : ");
	String username = input.next();
	
	System.out.println("Please enter Password : ");
	String password = input.next();
	
	user = uService.login(username, password);
	
	return user;
	// next should go to a users screen
	
}

public static void getUserList() {
	
	uddb.getAllUsers();
	
	System.out.println(uddb.getAllUsers());
}

public static void loggedIn() {
	
	int action = 0;
	Boolean run = true;
	User loggedUser = SessionCache.getCurrentUser().get();
	
	while (action <= 6 && run.equals(true)) {
	
		
		System.out.println("What actions would you like to perform in your account?");
		
		System.out.println("1. Check Balance of Account");
		System.out.println("2. Deposit Funds");
		System.out.println("3. Withdraw Funds");
		System.out.println("4. Transfer Funds");
		System.out.println("5. Create New Checking or Savings Account");
		System.out.println("6. Exit");
		action = input.nextInt();
		
		switch (action) {
			
		case 1:
			viewAcct();
			
			break;
			
		case 2:
			depositTo();
			
			break;
			
		case 3:
			withdrawFrom();
			break;
			
		case 4:
			transferTo();
			
			break;
			
		case 5:
			accountAdd(loggedUser);
			
			break;
		
		case 6:
			
			run = false;
			System.out.println("Exiting Program");

			break;
		}
		
}

}
public static void accountAdd(User loggedUser) {
	
	Account newAcct = new Account();
	
	System.out.println("Would you like to create a checkings or savings Account?");
	System.out.println("1. Checking");
	System.out.println("2. Savings");
	int action = input.nextInt();
	
	switch(action) {
	case 1:
		System.out.println("How much money would you like to put into your new checking account?");
		newAcct.setBalance(input.nextDouble());
		
		newAcct.setType(AccountType.CHECKING);
		newAcct.setApproved(true);
		newAcct.setOwnerId(loggedUser.getId());
		
		System.out.println("New Checkings Account Created with your Depost");
		addb.addAccount(newAcct);
		
		break;
		
	case 2:
		
		System.out.println("How much money would you like to put into your new savings account?");
		newAcct.setBalance(input.nextDouble());
		
		newAcct.setType(AccountType.SAVINGS);
		newAcct.setApproved(true);
		newAcct.setOwnerId(loggedUser.getId());
		
		System.out.println("New Savings Account Created with your Depost");
		addb.addAccount(newAcct);
		
		break;
	}
}

public static void depositTo() {
	
	Account acctId = new Account();
	int id = 0;
	double depositAmt = 0;
	
	System.out.println("Please enter Account Id");
	id = input.nextInt();
	
	System.out.println("How much would you like to deposit?");
	depositAmt = input.nextDouble();
	
	acctId = addb.getAccount(id);
	
	
	aService.deposit(acctId, depositAmt);
	
	
}

public static void withdrawFrom() {
	
	Account acctId = new Account();
	int id = 0;
	double withdrawAmt = 0;
	
	System.out.println("Please enter Account Id");
	id = input.nextInt();
	
	System.out.println("How much would you like to Withdraw?");
	withdrawAmt = input.nextDouble();
	
	acctId = addb.getAccount(id);
	
	aService.withdraw(acctId, withdrawAmt);
	
}

public static void viewAcct() {
	
	Account acctId = new Account();
	int id = 0;
	
	System.out.println("Please enter the Account Id for the Account you would like to view");
	id = input.nextInt();
	
	acctId = addb.getAccount(id);
	
	System.out.println(acctId);
	
}

public static void transferTo() {
	
	Account toAcctId = new Account();
	int toId = 0;
	
	Account fromAcctId = new Account();
	int fromId = 0;
	
	double transAmt = 0;
	
	System.out.println("Please enter the Account Id for the Account you would like to transfer from");
	fromId = input.nextInt();
	
	System.out.println("Please enter the Account Id for the Account you would like to transfer to");
	toId = input.nextInt();
	
	System.out.println("Enter the Amount you would like to Transfer");
	transAmt = input.nextDouble();
	
	toAcctId = addb.getAccount(toId);
	fromAcctId = addb.getAccount(fromId);
	
	aService.transfer(fromAcctId, toAcctId, transAmt);
	
}

public static void employeeActions() {
	Boolean run = true;
	int action = 0;
	
	while(run.equals(true) && action <= 3) {
	System.out.println("What employee actions would you like to perform?");
	System.out.println("1. Approve/ Reject Account");
	System.out.println("2. View a log of all Transactions");
	System.out.println("3. Exit");
	action = input.nextInt();
	
	switch(action) {
	
	case 1:
		employeeUpdate();
		
		break;
		
	case 2:
		
		break;
		
	case 3:
		run = false;
		System.out.println("Exiting Program");
		
		break;
	}
	}
}

public static void employeeUpdate() {
	
	Account acctId = new Account();
	int id = 0;
	Boolean newStatus = false;
	
	
	System.out.println("enter the Account Id of the Account you wish to un-approve");
	id = input.nextInt();
	
	acctId = addb.getAccount(id);
	
	acctId.setApproved(newStatus);
	
	System.out.println("The Account is now Not Approved");
	
	addb.empUpdate(acctId);
	}
	



}