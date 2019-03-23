package review.model;

public class Companies_TymofiiKryvtsun {
	protected String companyName;
	protected String about;
	
	public Companies_TymofiiKryvtsun(String companyName, String about) {
		this.companyName = companyName;
		this.about = about;
	}
	
	public Companies_TymofiiKryvtsun(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
	
}
