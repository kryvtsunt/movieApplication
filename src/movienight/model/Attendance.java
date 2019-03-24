package movienight.model;
public class Attendance {
	
	protected int movieNightId;
	protected String userName;
	protected AttendanceType attendanceType;
	protected int attendanceId;
	
	public enum AttendanceType {
		host, attendee
	}
	
	public Attendance(int attendanceId, int movieNightId, String userName, AttendanceType attendanceType) {
		this.attendanceId = attendanceId;
		this.movieNightId = movieNightId;
		this.userName = userName;
		this.attendanceType = attendanceType;
	}
	
	public Attendance(int movieNightId, String userName, AttendanceType attendanceType) {
		this.movieNightId = movieNightId;
		this.userName = userName;
		this.attendanceType = attendanceType;
	}
	
	public Attendance(int attendanceId) {
		this.attendanceId = attendanceId;
	}
	
	/** Getters and setters. */
	
	public int getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}

	public int getMovieNightId() {
		return movieNightId;
	}

	public void setMovieNightId(int movieNightId) {
		this.movieNightId = movieNightId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public AttendanceType getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(AttendanceType attendanceType) {
		this.attendanceType = attendanceType;
	}

}
