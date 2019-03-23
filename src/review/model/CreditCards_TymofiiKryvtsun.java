package review.model;

import java.sql.Date;


public class CreditCards_TymofiiKryvtsun {
	
	protected long cardNumer;
	protected Date expiration;
	protected Users_TymofiiKryvtsun user;
	
	public CreditCards_TymofiiKryvtsun(long cardNumer, Date expiration, Users_TymofiiKryvtsun user) {
		this.cardNumer = cardNumer;
		this.expiration = expiration;
		this.user = user;
	}
	
	public CreditCards_TymofiiKryvtsun(long cardNumer) {
		this.cardNumer = cardNumer;
	}
	
	public long getCardNumer() {
		return cardNumer;
	}
	public void setCardNumer(long cardNumer) {
		this.cardNumer = cardNumer;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public Users_TymofiiKryvtsun getUser() {
		return user;
	}
	public void setUser(Users_TymofiiKryvtsun user) {
		this.user = user;
	}
	
	

}
