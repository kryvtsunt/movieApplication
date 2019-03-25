package movienight.dal;

import movienight.model.*;
import movienight.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Data access object (DAO) class to interact with the underlying Users table in your
 * MySQL instance. This is used to store {@link Users} into your MySQL instance and 
 * retrieve {@link Users} from MySQL instance.
 */

public class UsersDao {

	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static UsersDao instance = null;
	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}
	
	/**
	 * Save the Users instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Users create(Users user) throws SQLException {
		String insertUser = "INSERT INTO User(UserName,Password,FirstName,LastName,Email,Phone) VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			// PreparedStatement allows us to substitute specific types into the query template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			insertStmt.setString(1, user.getUserName());
			insertStmt.setString(2, user.getPassword());
			insertStmt.setString(3, user.getFirstName());
			insertStmt.setString(4, user.getLastName());
			insertStmt.setString(5, user.getEmail());
			insertStmt.setString(6, user.getPhone());
			// Note that we call executeUpdate(). This is used for a INSERT/UPDATE/DELETE
			// statements, and it returns an int for the row counts affected (or 0 if the
			// statement returns nothing). For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// I'll leave it as an exercise for you to write UPDATE/DELETE methods.
			insertStmt.executeUpdate();
		
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	

	public Users delete(Users user) throws SQLException {
		String deleteUser = "DELETE FROM User WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
			deleteStmt.executeUpdate();

			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	

	public Users getUserFromUserName(String userName) throws SQLException {
		String selectUser = "SELECT UserName,Password,FirstName,LastName,Email,Phone FROM User WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
	
			results = selectStmt.executeQuery();
			
			if(results.next()) {

				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				String phone = results.getString("Phone");
				Users user = new Users(resultUserName, password, firstName, lastName, email, phone);
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	

	public Users updateEmail(Users user, String newEmail) throws SQLException {
		String updateUser = "UPDATE User SET Email=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;

		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newEmail);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();
			
			// Update the user's param before returning to the caller.
			user.setEmail(newEmail);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}

	}

	public List<Users> getUsersFromFirstName(String firstName) throws SQLException {
		List<Users> users = new ArrayList<Users>();
		String selectUser = "SELECT * FROM User WHERE FirstName LIKE ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, "%" + firstName + "%");
	
			results = selectStmt.executeQuery();
			
			while(results.next()) {

				String userName = results.getString("UserName");
				String password = results.getString("Password");
				String resultFirstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				String phone = results.getString("Phone");
				Users user = new Users(userName, password, resultFirstName, lastName, email, phone);
				users.add(user);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return users;
	}
}