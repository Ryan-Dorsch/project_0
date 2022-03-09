package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Account;
import com.revature.beans.Transaction;
import com.revature.beans.Transaction.TransactionType;
import com.revature.beans.User;
import com.revature.beans.User.UserType;

public class TransactionDaoDB implements TransactionDao {

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
	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		getConnection();
		List<Transaction> transactionList = new ArrayList<Transaction>();
		String query = "select * from transactions";
		AccountDaoDB acctdb = new AccountDaoDB();//creates a class of ADDB to be able to use the getAccount methood
		
		
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				Transaction t = new Transaction();
				//Account acct = acctdb.getAccount(int);
				
				int fromAcctId = rs.getInt("from_acct_id"); // retrieve from acct id from db
				Account acct = acctdb.getAccount(fromAcctId); // use the accountdaodb class's getAccount method to retrieve the account from the db
				t.setSender(acct); // use the transaction class's setSender method to set the sender to the account we just pulled
				
				int toAcctId = rs.getInt("to_acct_id");
				Account acct2 = acctdb.getAccount(toAcctId);
				t.setRecipient(acct2);
				
				Timestamp tStamp = rs.getTimestamp("timestamp");
				LocalDateTime Ldt = tStamp.toLocalDateTime();//taking the variable of timestamp and converting it to LocalDateTime(
				t.setTimestamp(Ldt);
				
						
				//t.setSender((Account) rs.getObject("from_acct_id"));
				//t.setRecipient((Account) rs.getObject("to_acct_id"));
				t.setAmount(rs.getDouble("amount"));
				//t.setTimestamp((LocalDateTime) rs.getObject("timestamp"));
				
				String type = rs.getString("type"); // to get varchar from db and store as string
				TransactionType enumVal = TransactionType.valueOf(type); //converts string to user type
				t.setType(enumVal); //sets trans rtype
				transactionList.add(t);
			}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		return transactionList;
	}
//	public Transaction addTrans(Transaction t) {
//		// TODO Auto-generated method stub
//		getConnection();
//		String query = "insert into transactions (id, amount, type, timestamp) values (?,?,?,?)";
//		try {
//			pstmt = conn.prepareStatement(query);
//			//pstmt.setInt(1, a.getId());
//			pstmt.setInt(1, a.getOwnerId());
//			pstmt.setDouble(2, a.getBalance());
//			//bc its an enum and can either be checkings or savings
//			pstmt.setString(3, a.getType().name());
//			//ask about this line to make sure we are using the correct method
//			pstmt.setBoolean(4, a.isApproved());
//		
//			pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			//find and track exceptions thrown
//			e.printStackTrace();
//		}	
//		return a;
//	}
}
