package movienight.dal;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import movienight.model.*;


public class PersonsDao {
	protected ConnectionManager connectionManager;
	
	private static PersonsDao instance = null;
	protected PersonsDao() {
		connectionManager = new ConnectionManager();
	}
	public static PersonsDao getInstance() {
		if(instance == null) {
			instance = new PersonsDao();
		}
		return instance;
	}

	public Persons create(Persons person) throws SQLException {
		String insertPerson = "INSERT INTO Person(FirstName,LastName, DateOfBirth) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPerson,  Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, person.getFirstName());
			insertStmt.setString(2, person.getLastName());
			insertStmt.setDate(3, person.getDateOfBirth());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int  personId = -1;
			if(resultKey.next()) {
				personId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			person.setPersonId(personId);
			return person;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}

	public Persons updateLastName(Persons person, String newLastName) throws SQLException {
		String updatePerson = "UPDATE person SET LastName=? WHERE PersonId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePerson);
			updateStmt.setString(1, newLastName);
			updateStmt.setInt(2, person.getPersonId());
			updateStmt.executeUpdate();
			person.setLastName(newLastName);
			return person;
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

	public Persons delete(Persons person) throws SQLException {
		String deletePerson = "DELETE FROM person WHERE PersonId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePerson);
			deleteStmt.setInt(1, person.getPersonId());
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


	public Persons getPersonById(int personId) throws SQLException {
		String selectPerson = "SELECT FirstName,LastName, PersonId FROM Person WHERE PersonId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPerson);
			selectStmt.setInt(1, personId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				Date dateOdBirth = results.getDate("DateOfBirth");
				Persons person = new Persons(personId, firstName, lastName, dateOdBirth);
				return person;
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

	
	public List<Persons> getpersonsByFirstName(String firstName) throws SQLException {
		List<Persons> persons = new ArrayList<Persons>();
		String selectperson =
			"SELECT * FROM Person WHERE FirstName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectperson);
			selectStmt.setString(1, firstName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int personId = results.getInt("PersonId");
				String lastName = results.getString("LastName");
				Date dateOfBirth = results.getDate("DateOfBirth");
				Persons person = new Persons(personId, firstName, lastName, dateOfBirth);
				persons.add(person);
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
		return persons;
	}
}
