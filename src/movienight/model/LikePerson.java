package movienight.model;

public class LikePerson {
	protected int likePersonId;
	protected int personId;
	protected String userName;
	
	public LikePerson(int likePersonId, int personId, String userName) {
		this.likePersonId = likePersonId;
		this.personId = personId;
		this.userName = userName;
	}
	
	public LikePerson(int personId, String userName) {
		this.personId = personId;
		this.userName = userName;
	}
	
	public LikePerson(int likePersonId) {
		this.likePersonId = likePersonId;
	}
	
	/** Getters and setters. */

	public int getLikePersonId() {
		return likePersonId;
	}

	public void setLikePersonId(int likePersonId) {
		this.likePersonId = likePersonId;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
