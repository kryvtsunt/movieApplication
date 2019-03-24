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


public class GenreDao {
	protected ConnectionManager connectionManager;
	
	private static GenreDao instance = null;
	protected GenreDao() {
		connectionManager = new ConnectionManager();
	}
	public static GenreDao getInstance() {
		if(instance == null) {
			instance = new GenreDao();
		}
		return instance;
	}

	public Genre create(Genre genre) throws SQLException {
		String insertgenre = "INSERT INTO Genre(Name) VALUES(?);";
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

	public Genre updateName(Genre genre, String newName) throws SQLException {
		String updategenre = "UPDATE genre SET Name=? WHERE genreId=?;";
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

	public Genre delete(Genre genre) throws SQLException {
		String deletegenre = "DELETE FROM genre WHERE genreId=?;";
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


	public Genre getGenreById(int genreId) throws SQLException {
		String selectgenre = "SELECT * FROM genre WHERE genreId=?;";
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
				Genre genre = new Genre(genreId, name);
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
