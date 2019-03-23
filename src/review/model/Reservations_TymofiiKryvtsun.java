package review.model;

import java.sql.Timestamp;

public class Reservations_TymofiiKryvtsun {
	
	protected int reservationId;
	protected Timestamp start;
	protected Timestamp end;
	protected int size;
	protected Users_TymofiiKryvtsun user;
	protected Restaurants_TymofiiKryvtsun restaurant;
	
	public Reservations_TymofiiKryvtsun(int reservationId, Timestamp start, Timestamp end, int size, Users_TymofiiKryvtsun user,
			Restaurants_TymofiiKryvtsun restaurant) {
		this.reservationId = reservationId;
		this.start = start;
		this.end = end;
		this.size = size;
		this.user = user;
		this.restaurant = restaurant;
	}
	
	public Reservations_TymofiiKryvtsun(int reservationId) {
		this.reservationId = reservationId;
	}
	
	public Reservations_TymofiiKryvtsun(Timestamp start, Timestamp end, int size, Users_TymofiiKryvtsun user,
			Restaurants_TymofiiKryvtsun restaurant) {
		this.start = start;
		this.end = end;
		this.size = size;
		this.user = user;
		this.restaurant = restaurant;
	}
	
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Users_TymofiiKryvtsun getUser() {
		return user;
	}
	public void setUser(Users_TymofiiKryvtsun user) {
		this.user = user;
	}
	public Restaurants_TymofiiKryvtsun getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurants_TymofiiKryvtsun restaurant) {
		this.restaurant = restaurant;
	}
	
	
	
	
}
