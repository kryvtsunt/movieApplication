package movienight.model;

public class CastCrew {
	
	protected int castId;
	protected Movie moive;
	protected Person person;
	protected CastCrewRole role;
	
	public enum CastCrewRole {
		ACTOR, DIRECTOR
	}

	public CastCrew(int castId, Movie moive, Person person, CastCrewRole role) {
		super();
		this.castId = castId;
		this.moive = moive;
		this.person = person;
		this.role = role;
	}

	public CastCrew(Movie moive, Person person, CastCrewRole role) {
		super();
		this.moive = moive;
		this.person = person;
		this.role = role;
	}

	public CastCrew(int castId) {
		super();
		this.castId = castId;
	}
	
	
	
	
	

}
