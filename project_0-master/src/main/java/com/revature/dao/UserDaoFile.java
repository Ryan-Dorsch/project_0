package com.revature.dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.beans.User;

/**
 * Implementation of UserDAO that reads and writes to a file
 */
public class UserDaoFile implements UserDao {
	
	public static String fileLocation = "/Users/Ryan/Documents/project0.txt";

	private static int id = 0;
	
	//creating a list to store all user names
	private static Set<User> usersList = new HashSet<User>();
	
	
	
	public User addUser(User user) {
		// TODO write user object to a file
		List<User> allUsers = this.getAllUsers();
		allUsers.add(user);
	
		try {
//			FileWriter writer = new FileWriter(fileLocation);
//			writer.close();
			
			//writing file to the external doc that includes users
			FileOutputStream fileOutputUser = new FileOutputStream(fileLocation);
			
			ObjectOutputStream objectFileUser = new ObjectOutputStream(fileOutputUser); 
			
			for(User usr : allUsers) {
			
			objectFileUser.writeObject(usr);
			}
			System.out.println("New User Successfully Registered!!");

			objectFileUser.close();
			} 
		catch (IOException e) {
			System.out.println("You have an input / output exception: " + e);
		}
		return null;
	}

	public User getUser(Integer userId) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public User getUser(String username, String pass) {
		// TODO Auto-generated method stub
		User requestedUser = null;
		return null;
		}
		
	
																																																																																																																						

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
//		Object obj = null;
//		while ((obj = objectIS.readObject()) != null) { 
		List<User> acctList = new ArrayList<User>();
		FileInputStream allUserFiles = null;
		ObjectInputStream objectIS= null;
		
		
		try {
			
			allUserFiles = new FileInputStream(fileLocation);
			
			objectIS = new ObjectInputStream(allUserFiles);
			
			
			
			User obj = null;
//			while ((obj = (User)objectIS.readObject()) != null) {
//				acctList.add(obj);
//			}
			while(true) {
				  User usr = (User)objectIS.readObject();
				  acctList.add(usr);
				}
			
//			objectIS.close();
//			return acctList;
			

		
			}
		catch (EOFException e) {
			
			
			return acctList;
			
		}
		catch (Exception e2) {
			System.out.println("You have an exception" + e2);
		}
		finally {
			
			System.out.println("These are all user accounts");
		}
		return null;
		}
	

	public User updateUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeUser(User u) {
		// TODO Auto-generated method stub
		
		
		return false;
	}

}
