package movienight.model;

public class Genres {
	protected int genreId;
	protected String name;
	public Genres(int genreId, String name) {
		super();
		this.genreId = genreId;
		this.name = name;
	}
	public Genres(String name) {
		super();
		this.name = name;
	}
	public int getGenreId() {
		return genreId;
	}
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
