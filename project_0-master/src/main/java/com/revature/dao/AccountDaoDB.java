package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.Account.AccountType;
import com.revature.beans.User;
import com.revature.beans.User.UserType;

/**
 * Implementation of AccountDAO which reads/writes to a database
 */
public class AccountDaoDB implements AccountDao {

	private static Connection conn;
	private static Statement stmt;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
public static void getConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature", "root", "727749RaD");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	public Account addAccount(Account a) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "insert into accounts (ownerID, balance, account_type, approved) values (?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(query);
			//pstmt.setInt(1, a.getId());
			pstmt.setInt(1, a.getOwnerId());
			pstmt.setDouble(2, a.getBalance());
			//bc its an enum and can either be checkings or savings
			pstmt.setString(3, a.getType().name());
			//ask about this line to make sure we are using the correct method
			pstmt.setBoolean(4, a.isApproved());
		
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			//find and track exceptions thrown
			e.printStackTrace();
		}	
		return a;
	}

	public Account getAccount(Integer actId) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "select * from accounts where accountID=" + actId;
		Account a = new Account();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				a.setId(rs.getInt("accountID"));
				a.setOwnerId(rs.getInt("ownerID"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getBoolean("approved"));
				
				String type = rs.getString("account_type"); // to get varchar from db and store as string
				AccountType enumVal = AccountType.valueOf(type); //converts string to acct type
				a.setType(enumVal); //sets acct type
				
			}
			//String query = "select * from accounts where ownerId=" + userId;  to select all accounts thatare associated with the user id
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return a;
		
	}

	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		getConnection();
		List<Account> accountList = new ArrayList<Account>();
		String query = "select * from accounts";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("accountID"));
				a.setOwnerId(rs.getInt("ownerID"));
				a.setBalance(rs.getDouble("balance"));
				a.setApproved(rs.getBoolean("approved"));
				
				String type = rs.getString("account_type"); // to get varchar from db and store as string
				AccountType enumVal = AccountType.valueOf(type); //converts string to account type
				a.setType(enumVal); //sets accounttype
				accountList.add(a);
			}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return accountList;
	}

	public List<Account> getAccountsByUser(User u) {
		// TODO Auto-generated method stub
		getConnection();
		
		String query = "select * from accounts where owner_ID =" + u.getId();
		List<Account> accountList = new ArrayList <Account>();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				
				Account a = new Account();
				a.setId(rs.getInt("owner_ID"));
				a.setBalance(rs.getDouble("balance"));
				
				String type = rs.getString("account_type"); // to get varchar from db and store as string
				AccountType enumVal = AccountType.valueOf(type); //converts string to account type
				a.setType(enumVal); //sets accounttype
				accountList.add(a);
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return accountList;
		}
		
		
	
	public Account updateAccount(Account a) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "update accounts set balance =? where accountID =?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(2, a.getId());
			//converting enum to a string 
			pstmt.setDouble(1, a.getBalance());
			
			
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	public boolean removeAccount(Account a) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "Delete from accounts where accountID=" + a.getId();
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Account empUpdate (Account a) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "update accounts set approved =? where accountID =?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(2, a.getId());
			//converting enum to a string 
			pstmt.setBoolean(1, a.isApproved());
			
		
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
}
