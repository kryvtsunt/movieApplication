package movienight.dal;
import movienight.model.*;

import movienight.model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LikePersonsDao {
	protected ConnectionManager connectionManager;

	private static LikePersonsDao instance = null;
	protected LikePersonsDao() {
		connectionManager = new ConnectionManager();
	}
	public static LikePersonsDao getInstance() {
		if(instance == null) {
			instance = new LikePersonsDao();
		}
		return instance;
	}

	public LikePersons create(LikePersons likePerson) throws SQLException {
		String insertLikePerson =
			"INSERT INTO LikePersons(movie,user) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLikePerson,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, likePerson.getPerson().getPersonId());
			insertStmt.setString(2, likePerson.getUser().getUserName());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int likePersonId = -1;
			if(resultKey.next()) {
				likePersonId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			likePerson.setLikePersonId(likePersonId);
			return likePerson;
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

	/**
	 * Delete the LikePersons instance.
	 * This runs a DELETE statement.
	 */
	public LikePersons delete(LikePersons likePerson) throws SQLException {
		String deleteLikePerson = "DELETE FROM LikePersons WHERE LikePersonId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteLikePerson);
			deleteStmt.setInt(1, likePerson.getLikePersonId());
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



	public LikePersons getLikePersonById(int likePersonId) throws SQLException {
		String selectLikePerson =
			"SELECT LikePersonId, PersonId, UserName " +
			"FROM LikePersons " +
			"WHERE LikePersonId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLikePerson);
			selectStmt.setInt(1, likePersonId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			PersonsDao personsDao = PersonsDao.getInstance();
			if(results.next()) {
				int resultLikePersonId = results.getInt("LikePersonId");
				int personId = results.getInt("PersonId");
				String userName = results.getString("UserName");
				
				Persons person = personsDao.getPersonById(personId);
				Users user = usersDao.getUserFromUserName(userName);
				LikePersons likePerson = new LikePersons(resultLikePersonId, person, user);
				return likePerson;
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

	/**
	 * Get the all the LikePersons for a user.
	 */
	public List<LikePersons> getLikePersonsForUser(Users user) throws SQLException {
		List<LikePersons> likePersons = new ArrayList<LikePersons>();
		String selectLikePersons =
			"SELECT LikePersonId, MovieId, UserName" +
			"FROM LikePersons" + 
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLikePersons);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			PersonsDao personsDao = PersonsDao.getInstance();
			while(results.next()) {
				int personId = results.getInt("PersonId");
				Persons person = personsDao.getPersonById(personId);
				int likePersonId = results.getInt("LikeMovieId");
				
				LikePersons likePerson = new LikePersons(likePersonId, person, user);
				likePersons.add(likePerson);
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
		return likePersons;
	}
}
