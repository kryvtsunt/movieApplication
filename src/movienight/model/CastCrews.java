package movienight.model;

public class CastCrews {
	
	protected int castId;
	protected Movies moive;
	protected Persons person;
	protected CastCrewRole role;
	
	public enum CastCrewRole {
		ACTOR, DIRECTOR
	}

	public CastCrews(int castId, Movies moive, Persons person, CastCrewRole role) {
		super();
		this.castId = castId;
		this.moive = moive;
		this.person = person;
		this.role = role;
	}

	public CastCrews(Movies moive, Persons person, CastCrewRole role) {
		super();
		this.moive = moive;
		this.person = person;
		this.role = role;
	}

	public CastCrews(int castId) {
		super();
		this.castId = castId;
	}

	public int getCastId() {
		return castId;
	}

	public void setCastId(int castId) {
		this.castId = castId;
	}

	public Movies getMoive() {
		return moive;
	}

	public void setMoive(Movies moive) {
		this.moive = moive;
	}

	public Persons getPerson() {
		return person;
	}

	public void setPerson(Persons person) {
		this.person = person;
	}

	public CastCrewRole getRole() {
		return role;
	}

	public void setRole(CastCrewRole role) {
		this.role = role;
	}
	
	
	
	
	
	
	

}
