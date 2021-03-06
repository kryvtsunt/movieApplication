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


public class GenresDao {
	protected ConnectionManager connectionManager;
	
	private static GenresDao instance = null;
	protected GenresDao() {
		connectionManager = new ConnectionManager();
	}
	public static GenresDao getInstance() {
		if(instance == null) {
			instance = new GenresDao();
		}
		return instance;
	}

	public Genres create(Genres genre) throws SQLException {
		String insertgenre = "INSERT INTO Genres(Name) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertgenre,  Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, genre.getName());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int  genreId = -1;
			if(resultKey.next()) {
				genreId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			genre.setGenreId(genreId);
			return genre;
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

	public Genres updateName(Genres genre, String newName) throws SQLException {
		String updategenre = "UPDATE Genres SET Name=? WHERE GenreId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updategenre);
			updateStmt.setString(1, newName);
			updateStmt.setInt(2, genre.getGenreId());
			updateStmt.executeUpdate();
			genre.setName(newName);
			return genre;
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

	public Genres delete(Genres genre) throws SQLException {
		String deletegenre = "DELETE FROM Genres WHERE GenreId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletegenre);
			deleteStmt.setInt(1, genre.getGenreId());
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


	public Genres getGenreById(int genreId) throws SQLException {
		String selectgenre = "SELECT * FROM Genres WHERE GenreId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectgenre);
			selectStmt.setInt(1, genreId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String name = results.getString("Name");
				Genres genre = new Genres(genreId, name);
				return genre;
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

}
