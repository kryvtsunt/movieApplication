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


public class CastCrewsDao {
	protected ConnectionManager connectionManager;
	
	private static CastCrewsDao instance = null;
	protected CastCrewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CastCrewsDao getInstance() {
		if(instance == null) {
			instance = new CastCrewsDao();
		}
		return instance;
	}

	public CastCrews create(CastCrews cast) throws SQLException {
		String insertCastCrew = "INSERT INTO Casts(MovieId, PersonId, Role) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;		
		try {
			connection = connectionManager.getConnection();
			// to create castCrew, valid movie-person required;
			Movies movie = MoviesDao.getInstance().getMovieById(cast.getMoive().getMovieId());
			Persons person = PersonsDao.getInstance().getPersonById(cast.getPerson().getPersonId());
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

	public CastCrews delete(CastCrews castCrew) throws SQLException {
		String deleteCastCrew = "DELETE FROM Casts WHERE CastId=?;";
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


	public CastCrews getCastCrewById(int castCrewId) throws SQLException {
		String selectCastCrew = "SELECT * FROM Casts WHERE CastId=?;";
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
				CastCrews.CastCrewRole role = CastCrews.CastCrewRole.valueOf(results.getString("Role"));
				Movies movie = MoviesDao.getInstance().getMovieById(mid);
				Persons person = PersonsDao.getInstance().getPersonById(pid);
				CastCrews CastCrew = new CastCrews(castCrewId, movie, person, role);
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
