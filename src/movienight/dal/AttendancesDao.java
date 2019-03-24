//package movienight.dal;
//
//import movienight.model.*;
//import movienight.model.Attendances.AttendanceType;
//import movienight.dal.ConnectionManager;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.Timestamp;
//import java.sql.Types;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Data access object (DAO) class to interact with the underlying CreditCard table in your
// * MySQL instance. 
// */
//
//public class AttendancesDao {
//
//	protected ConnectionManager connectionManager;
//	
//	// Single pattern: instantiation is limited to one object.
//	private static AttendancesDao instance = null;
//	protected AttendancesDao() {
//		connectionManager = new ConnectionManager();
//	}
//	public static AttendancesDao getInstance() {
//		if(instance == null) {
//			instance = new AttendancesDao();
//		}
//		return instance;
//	}
//	
//	
//	public Attendances create(Attendances attendance) throws SQLException {
//		String insertAtteandance =
//			"INSERT INTO Attendances(MovieNightId,UserName,AttendanceType) " +
//			"VALUES(?,?,?);";
//		Connection connection = null;
//		PreparedStatement insertStmt = null;
//		ResultSet resultKey = null;
//		try {
//			connection = connectionManager.getConnection();
//			// BlogPosts has an auto-generated key. So we want to retrieve that key.
//			insertStmt = connection.prepareStatement(insertAttendance,
//				Statement.RETURN_GENERATED_KEYS);
//			insertStmt.setInt(1, attendance.getMovieNight().getMovieNightId());
//			insertStmt.setString(2,  attendance.getUser().getUserName());
//			insertStmt.setString(3, attendance.getAttendanceType().name());
//			insertStmt.executeUpdate();
//			
//			// Retrieve the auto-generated key and set it, so it can be used by the caller.
//			// For more details, see:
//			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
//			resultKey = insertStmt.getGeneratedKeys();
//			int postId = -1;
//			if(resultKey.next()) {
//				postId = resultKey.getInt(1);
//			} else {
//				throw new SQLException("Unable to retrieve auto-generated key.");
//			}
//			attendance.setAttendanceId(postId);
//			return attendance;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//			if(insertStmt != null) {
//				insertStmt.close();
//			}
//			if(resultKey != null) {
//				resultKey.close();
//			}
//		}
//	}
//
//	/**
//	 * Thought the following might be useful but handling string inputs for enums may be more complicated than we want: 
//	 * think best way to implement would be to add a try-catch statement
//	 * 
//	 * Update the attendance type of the Attendances instance.
//	 * This runs a UPDATE statement.
//	 */
//	
//	/**
//	public Attendances updateAttendanceType(Attendances attendance, String newAttendanceType) throws SQLException {
//		String updateAttendance = "UPDATE Attendances SET AttendanceType=? WHERE AttendanceId=?;";
//		Connection connection = null;
//		PreparedStatement updateStmt = null;
//		try {
//			connection = connectionManager.getConnection();
//			updateStmt = connection.prepareStatement(updateAttendance);
//			updateStmt.setInt(1, attendance.getAttendanceId());
//			updateStmt.setInt(2, attendance.getMovieNight().getMovieNightId());
//			updateStmt.setString(3, attendance.getUser().getUserName());
//			updateStmt.setString(4, AttendanceType.valueOf(newAttendanceType).name()); // Add try-catch here
//			updateStmt.executeUpdate();
//
//			// Update the blogPost param before returning to the caller.
//			attendance.setAttendanceType(AttendanceType.valueOf(newAttendanceType));
//			return attendance;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//			if(updateStmt != null) {
//				updateStmt.close();
//			}
//		}
//	}
//	*/
//	
//
//	/**
//	 * Delete the Attendance instance.
//	 * This runs a DELETE statement.
//	 */
//	public Attendances delete(Attendances attendance) throws SQLException {
//		String deleteAttendance = "DELETE FROM Attendances WHERE AttendanceId=?;";
//		Connection connection = null;
//		PreparedStatement deleteStmt = null;
//		try {
//			connection = connectionManager.getConnection();
//			deleteStmt = connection.prepareStatement(deleteAttendance);
//			deleteStmt.setInt(1, attendance.getAttendanceId());
//			deleteStmt.executeUpdate();
//
//			// Return null so the caller can no longer operate on the BlogPosts instance.
//			return null;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//			if(deleteStmt != null) {
//				deleteStmt.close();
//			}
//		}
//	}
//
//	/**
//	 * Get the Attendances record by fetching it from your MySQL instance.
//	 * This runs a SELECT statement and returns a single Attendances instance.
//	 * Note that we use UsersDao to retrieve the referenced Users instance.
//	 * One alternative (possibly more efficient) is using a single SELECT statement
//	 * to join the Attendances, Users tables and then build each object.
//	 */
//	public Attendances getBlogAttendanceById(int attendanceId) throws SQLException {
//		String selectAttendance =
//			"SELECT AttendanceId,MovieNightId,UserName,AttendanceType" +
//			"FROM Attendances " +
//			"WHERE AttendanceId=?;";
//		Connection connection = null;
//		PreparedStatement selectStmt = null;
//		ResultSet results = null;
//		try {
//			connection = connectionManager.getConnection();
//			selectStmt = connection.prepareStatement(selectAttendance);
//			selectStmt.setInt(1, attendanceId);
//			results = selectStmt.executeQuery();
//			UsersDao usersDao = usersDao.getInstance();
//			MovieNightsDao movieNightsDao = movieNightsDao.getInstance(); // need DAL for MovieNights
//			
//			if(results.next()) {
//				int resultAttendanceId = results.getInt("AttendanceId");
//				String movieNightId = results.getString("MovieNightId");
//				String userName = results.getString("UserName");
//				Attendances.AttendanceType attendanceType = Attendances.AttendanceType.valueOf(
//								results.getString("AttendanceType"));
//				
//				MovieNights movieNight = movieNightsDao.getMovieNightById(movieNightId);
//				Users user = usersDao.getUserFromUserName(userName);
//				Attendances attendance = new Attendances(resultAttendanceId, movieNight, user, attendanceType);
//				return attendance;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//			if(selectStmt != null) {
//				selectStmt.close();
//			}
//			if(results != null) {
//				results.close();
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * Get the all the Attendances for a user.
//	 */
//	public List<Attendances> getBlogPostsForUser(Users user) throws SQLException {
//		List<Attendances> attendances = new ArrayList<Attendances>();
//		String selectAttendances =
//			"SELECT AttendanceId,MovieNightId,UserName,AttendanceType" +
//			"FROM Attendances " +
//			"WHERE UserName=?;";
//		Connection connection = null;
//		PreparedStatement selectStmt = null;
//		ResultSet results = null;
//		try {
//			connection = connectionManager.getConnection();
//			selectStmt = connection.prepareStatement(selectAttendances);
//			selectStmt.setString(1, user.getUserName());
//			results = selectStmt.executeQuery();
//			while(results.next()) {
//				int resultAttendanceId = results.getInt("AttendanceId");
//				String movieNightId = results.getString("MovieNightId");
//		
//				Attendances.AttendanceType attendanceType = Attendances.AttendanceType.valueOf(
//								results.getString("AttendanceType"));
//				
//				MovieNights movieNight = movieNightsDao.getMovieNightFromId(movieNightId); // need DAL for MovieNights
//				Attendances attendance = new Attendances(resultAttendanceId, movieNight, user, attendanceType);
//				attendances.add(attendance);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//			if(selectStmt != null) {
//				selectStmt.close();
//			}
//			if(results != null) {
//				results.close();
//			}
//		}
//		return attendances;
//	}
//}
