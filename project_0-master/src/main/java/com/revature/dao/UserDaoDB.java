package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.revature.beans.User;
import com.revature.beans.User.UserType;

/**
 * Implementation of UserDAO that reads/writes to a relational database
 */
public class UserDaoDB implements UserDao {

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
	
public User addUser(User user) {
		// TODO Auto-generated method stub
	getConnection();
	String query = "insert into users (id, first_name, last_name, username, password, user_type) values (?,?,?,?,?,?)";
	try {
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, user.getId());
		pstmt.setString(2, user.getFirstName());
		pstmt.setString(3, user.getLastName());
		pstmt.setString(4, user.getUsername());
		pstmt.setString(5, user.getPassword());
		pstmt.setString(6, user.getUserType().name());
	
		
		pstmt.executeUpdate();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	return user;
	}

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "select * from users where id=" + userId;
		User u = new User();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				u.setId(rs.getInt("id"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				String type = rs.getString("user_type"); // to get varchar from db and store as string
				UserType enumVal = UserType.valueOf(type); //converts string to user type
				u.setUserType(enumVal); //sets usertype
				
			}
			//String query = "select * from accounts where ownerId=" + userId;  to select all accounts thatare associated with the user id
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return u;
	}
	

	public User getUser(String username, String pass) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "select * from users where username='" + username + "'" + "&& password='" + pass+ "'";
		User u = new User();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				u.setId(rs.getInt("id"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				String type = rs.getString("user_type"); // to get varchar from db and store as string
				UserType enumVal = UserType.valueOf(type); //converts string to user type
				u.setUserType(enumVal); //sets usertype
			}

		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return u;
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		getConnection();
		List<User> userList = new ArrayList<User>();
		String query = "select * from users";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				
				
				String type = rs.getString("user_type"); // to get varchar from db and store as string
				UserType enumVal = UserType.valueOf(type); //converts string to user type
				u.setUserType(enumVal); //sets usertype
				userList.add(u);
			}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		return userList;
}

	public User updateUser(User u) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "update users set first_name=?, last_name =?, username =?, password =? where id=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(5, u.getId());
			pstmt.setString(1, u.getFirstName());
			pstmt.setString(2, u.getLastName());
			pstmt.setString(3, u.getUsername());
			pstmt.setString(4, u.getPassword());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		getConnection();
		String query = "Delete from users where id=" + u.getId();
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

}

