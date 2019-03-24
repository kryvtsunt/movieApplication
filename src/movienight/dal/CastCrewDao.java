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


public class CastCrewDao {
	protected ConnectionManager connectionManager;
	
	private static CastCrewDao instance = null;
	protected CastCrewDao() {
		connectionManager = new ConnectionManager();
	}
	public static CastCrewDao getInstance() {
		if(instance == null) {
			instance = new CastCrewDao();
		}
		return instance;
	}

	public CastCrew create(CastCrew cast) throws SQLException {
		String insertCastCrew = "INSERT INTO CastCrew(MovieId, PersonId, Role) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		try {
			connection = connectionManager.getConnection();
			// to create castCrew, valid movie-person required;
			Movie movie = MovieDao.getInstance().getMovieById(cast.getMoive().getMovieId());
			Person person = PersonDao.getInstance().getPersonById(cast.getPerson().getPersonId());
			if (movie == null | person == null) {
				return null;
			}
			insertStmt = connection.prepareStatement(insertCastCrew,  Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, cast.getMoive().getMovieId());
			insertStmt.setInt(2, cast.getPerson().getPersonId());
			insertStmt.setString(3, cast.getRole().name());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int  castCrewId = -1;
			if(resultKey.next()) {
				castCrewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			cast.setCastId(castCrewId);
			return cast;
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

	public CastCrew delete(CastCrew castCrew) throws SQLException {
		String deleteCastCrew = "DELETE FROM CastCrew WHERE CastId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCastCrew);
			deleteStmt.setInt(1, castCrew.getCastId());
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


	public CastCrew getCastCrewById(int castCrewId) throws SQLException {
		String selectCastCrew = "SELECT * FROM CastCrew WHERE CastId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCastCrew);
			selectStmt.setInt(1, castCrewId);
			results = selectStmt.executeQuery();
			if(results.next()) {

				int mid = results.getInt("MovieId");
				int pid = results.getInt("PesronId");
				CastCrew.CastCrewRole role = CastCrew.CastCrewRole.valueOf(results.getString("CastCrewRole"));
				Movie movie = MovieDao.getInstance().getMovieById(mid);
				Person person = PersonDao.getInstance().getPersonById(pid);
				CastCrew CastCrew = new CastCrew(castCrewId, movie, person, role);
				return CastCrew;
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
