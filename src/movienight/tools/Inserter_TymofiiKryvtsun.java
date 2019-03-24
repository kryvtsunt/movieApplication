package movienight.tools;

import java.sql.SQLException;
import java.sql.Timestamp;

import movienight.dal.*;
import movienight.model.*;

import java.sql.Date;


public class Inserter_TymofiiKryvtsun {

	public static void main(String[] args) throws SQLException {
		// DAO instances
		MovieDao usersDao = MovieDao.getInstance();
		CreditCardsDao_TymofiiKryvtsun creditCardsDao = CreditCardsDao_TymofiiKryvtsun.getInstance();
		CompaniesDao_TymofiiKryvtsun companiesDao = CompaniesDao_TymofiiKryvtsun.getInstance();
		RestaurantsDao_TymofiiKryvtsun restaurantsDao = RestaurantsDao_TymofiiKryvtsun.getInstance();
		SitDownRestaurantDao_TymofiiKryvtsun sitDownRestaurantDao = SitDownRestaurantDao_TymofiiKryvtsun.getInstance();
		FoodCartRestaurantDao_TymofiiKryvtsun foodCartRestaurantDao = FoodCartRestaurantDao_TymofiiKryvtsun.getInstance();
		TakeOutRestaurantDao_TymofiiKryvtsun takeOutRestaurantDao = TakeOutRestaurantDao_TymofiiKryvtsun.getInstance();
		ReviewsDao_TymofiiKryvtsun reviewsDao = ReviewsDao_TymofiiKryvtsun.getInstance();
		RecommendationsDao_TymofiiKryvtsun recommendationsDao = RecommendationsDao_TymofiiKryvtsun.getInstance();
		ReservationsDao_TymofiiKryvtsun reservationsDao = ReservationsDao_TymofiiKryvtsun.getInstance();


		// INSERT
		Date date =  new Date(System.currentTimeMillis());
		Timestamp time = new Timestamp(date.getTime());
		Users_TymofiiKryvtsun user = new Users_TymofiiKryvtsun("timbo", "qwerty", "tymofii", "kryvtsun", "email", "phone");
		user = usersDao.create(user);
		CreditCards_TymofiiKryvtsun card = new CreditCards_TymofiiKryvtsun(123412341235L, new Date(System.currentTimeMillis()), user);
		card = creditCardsDao.create(card);
		Companies_TymofiiKryvtsun company = new Companies_TymofiiKryvtsun("Company1", "Small tech startup");
		company = companiesDao.create(company);
		Restaurants_TymofiiKryvtsun restaurant1 = new Restaurants_TymofiiKryvtsun("restaurant1","about restaurant","menu","hours", true, Restaurants_TymofiiKryvtsun.CuisineType.AFRICAN, "street1","street2","seattle","wa",98195,"company1");
		restaurant1 = restaurantsDao.create(restaurant1);
		SitDownRestaurant_TymofiiKryvtsun restaurant2 = new SitDownRestaurant_TymofiiKryvtsun("restaurant2","about restaurant","menu","hours", true, Restaurants_TymofiiKryvtsun.CuisineType.AMERICAN, "street1","street2","seattle","wa",98195,"company1", 5);
		restaurant2 = sitDownRestaurantDao.create(restaurant2);
		TakeOutRestaurant_TymofiiKryvtsun restaurant3 = new TakeOutRestaurant_TymofiiKryvtsun("restaurant3","about restaurant","menu","hours", true, Restaurants_TymofiiKryvtsun.CuisineType.ASIAN, "street1","street2","seattle","wa",98195,"company1",30);
		restaurant3 = takeOutRestaurantDao.create(restaurant3);
		FoodCartRestaurant_TymofiiKryvtsun restaurant4 = new FoodCartRestaurant_TymofiiKryvtsun("restaurant4","about restaurant","menu","hours", true, Restaurants_TymofiiKryvtsun.CuisineType.ASIAN, "street1","street2","seattle","wa",98195,"company1", true);
		restaurant4 = foodCartRestaurantDao.create(restaurant4);
		Reviews_TymofiiKryvtsun review = new Reviews_TymofiiKryvtsun(time, "good", 4.5f, user, restaurant2);
		review = reviewsDao.create(review);
		Recommendations_TymofiiKryvtsun recommendation = new Recommendations_TymofiiKryvtsun(user, restaurant2);
		recommendation = recommendationsDao.create(recommendation);
		Reservations_TymofiiKryvtsun reservation = new Reservations_TymofiiKryvtsun(time, time, 5, user, restaurant2);
		reservation = reservationsDao.create(reservation);
		
		
		// READ
		Users_TymofiiKryvtsun u1 = usersDao.getUserByUserName("timbo");
		System.out.format("Reading person: u:%s, p:%s, f:%s, l:%s, e:%s, p:%s\n",
			u1.getUserName(), u1.getPassword(), u1.getFirstName(), u1.getLastName(), u1.getEmail(), u1.getPhone());
		
		CreditCards_TymofiiKryvtsun c1 = creditCardsDao.getCreditCardByCardNumber(123412341235L);
		System.out.format("Reading creditCard: n:%s, e:%s, u:%s\n",
				c1.getCardNumer(), c1.getExpiration(), c1.getUser().getUserName());
		
		Companies_TymofiiKryvtsun cm1 = companiesDao.getCompanyByCompanyName("Company1");
		System.out.format("Reading company: n:%s, a:%s\n",
				cm1.getCompanyName(), cm1.getAbout());
		
		Restaurants_TymofiiKryvtsun r1 = restaurantsDao.getRestaurantsByCuisine(Restaurants_TymofiiKryvtsun.CuisineType.AFRICAN).get(0);
		System.out.format("Reading restaurant: n:%s, d:%s \n",
				r1.getName(), r1.getDescription());
		
		SitDownRestaurant_TymofiiKryvtsun r2 = sitDownRestaurantDao.getSitDownRestaurantsByCompanyName("company1").get(0);
		System.out.format("Reading restaurant: n:%s, d:%s \n",
				r2.getName(), r2.getDescription());
		
		TakeOutRestaurant_TymofiiKryvtsun r3 = takeOutRestaurantDao.getTakeOutRestaurantsByCompanyName("company1").get(0);
		System.out.format("Reading restaurant: n:%s, d:%s \n",
				r3.getName(), r3.getDescription());
		
		FoodCartRestaurant_TymofiiKryvtsun r4 = foodCartRestaurantDao.getFoodCartRestaurantsByCompanyName("company1").get(0);
		System.out.format("Reading restaurant: n:%s, d:%s \n",
				r4.getName(), r4.getDescription());
		
		Reviews_TymofiiKryvtsun rv = reviewsDao.getReviewsByUserName("timbo").get(0);
		System.out.format("Reading review: u:%s, c:%s \n",
				rv.getUser().getUserName(), rv.getContent());
		
		// may not work depending on the ids of the restaurants (controlled by the db).
//		Recommendations_TymofiiKryvtsun rm = recommendationsDao.getRecommendationsByRestaurantId(2).get(0);
//		System.out.format("Reading recommendation: u:%s, r:%s \n",
//				rm.getUser().getUserName(), rm.getRestaurant().getName());
//		
//		Reservations_TymofiiKryvtsun rs = reservationsDao.getReservationById(2);
//		System.out.format("Reading reservation: u:%s, r:%s \n",
//				rs.getUser().getUserName(), rs.getRestaurant().getName());

		//UPDATE
		creditCardsDao.updateExpiration(c1, new Date(System.currentTimeMillis() + 1000));
		companiesDao.updateAbout(cm1, "Big tech startup");
		
		
		//DELETE
		usersDao.delete(u1);
		creditCardsDao.delete(c1);
		companiesDao.delete(cm1);
		restaurantsDao.delete(restaurant1);
		sitDownRestaurantDao.delete(restaurant2);
		takeOutRestaurantDao.delete(restaurant3);
		foodCartRestaurantDao.delete(restaurant4);
		reviewsDao.delete(review);
		recommendationsDao.delete(recommendation);
		reservationsDao.delete(reservation);
	}
}
