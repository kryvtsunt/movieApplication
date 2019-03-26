package movienight.tools;

import java.sql.SQLException;

import movienight.dal.*;
import movienight.model.*;

import java.util.Calendar;
import java.util.Date;


public class Inserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances
		MoviesDao moviesDao = MoviesDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();
		PersonsDao personsDao = PersonsDao.getInstance();
		GenresDao genresDao = GenresDao.getInstance();
		MovieGenresDao movieGenresDao = MovieGenresDao.getInstance();
		MovieNightsDao movieNightsDao = MovieNightsDao.getInstance();
		ReviewsDao reviewsDao = ReviewsDao.getInstance();
		WatchedMoviesDao watchedMoviesDao = WatchedMoviesDao.getInstance();
		LikeMoviesDao likeMoviesDao = LikeMoviesDao.getInstance();
		WishListMoviesDao wishListMoviesDao = WishListMoviesDao.getInstance();
		AttendancesDao attendancesDao = AttendancesDao.getInstance();
		
		// Create Users
		Users val = new Users("valchar", "passwd", "Val", "Charry", "val@val.com", "555-5555");
		val = usersDao.create(val);
		Users tim = new Users("tim", "passwd", "Tim", "Tim", "tim@tim.com", "555-6666");
		tim = usersDao.create(tim);
		Users olivia = new Users("liv", "passwd", "Olivia", "Olivia", "olivia@olivia.com", "555-7777");
		olivia = usersDao.create(olivia);
		
		// Create Movies
		Movies harryPotter1 = new Movies("Harry Potter 1", 1996, 120);
		harryPotter1 = moviesDao.create(harryPotter1);
		Movies harryPotter2 = new Movies("Harry Potter 2", 1998, 120);
		harryPotter2 = moviesDao.create(harryPotter2);
		Movies harryPotter3 = new Movies("Harry Potter 3", 2000, 120);
		harryPotter3 = moviesDao.create(harryPotter3);
		Movies harryPotter4 = new Movies("Harry Potter 4", 2003, 120);
		harryPotter4 = moviesDao.create(harryPotter4);
		
		Movies fastAndFurious1 = new Movies("Fast and Furious 1", 1996, 120);
		fastAndFurious1 = moviesDao.create(fastAndFurious1);
		Movies fastAndFurious2 = new Movies("Fast and Furious 2", 1998, 120);
		fastAndFurious2 = moviesDao.create(fastAndFurious2);
		Movies fastAndFurious3 = new Movies("Fast and Furious 3", 2000, 120);
		fastAndFurious3 = moviesDao.create(fastAndFurious3);
		Movies fastAndFurious4 = new Movies("Fast and Furious 4", 2003, 120);
		fastAndFurious4 = moviesDao.create(fastAndFurious4);

		Movies starWars1 = new Movies("Star Wars 1", 1996, 120);
		starWars1 = moviesDao.create(starWars1);
		Movies starWars2 = new Movies("Star Wars 2", 1998, 120);
		starWars2 = moviesDao.create(starWars2);
		Movies starWars3 = new Movies("Star Wars 3", 2000, 120);
		starWars3 = moviesDao.create(starWars3);
		Movies starWars4 = new Movies("Star Wars 4", 2003, 120);
		starWars4 = moviesDao.create(starWars4);
		
		// Create Persons
		Calendar date = Calendar.getInstance();
		date.set(1989, 4, 14);
		Persons danielRadcliffe = new Persons("Daniel", "Radcliffe", date.getTime());
		danielRadcliffe = personsDao.create(danielRadcliffe);
		Persons emmaWatson = new Persons("Emma", "Watson", date.getTime());
		emmaWatson = personsDao.create(emmaWatson);
		Persons theRock = new Persons("Dwayne", "Johnson", date.getTime());
		theRock = personsDao.create(theRock);
		Persons vinDiesel = new Persons("Vin", "Diesel", date.getTime());
		vinDiesel = personsDao.create(vinDiesel);
		Persons carrieFisher = new Persons("Carrie", "Fisher", date.getTime());
		carrieFisher = personsDao.create(carrieFisher);
		Persons harrisonFord = new Persons("Harrison", "Ford", date.getTime());
		harrisonFord = personsDao.create(harrisonFord);
		
		// Create Genres
		Genres sciFi = new Genres("Sci-Fi");
		sciFi = genresDao.create(sciFi);
		Genres action = new Genres("Action");
		action = genresDao.create(action);
		Genres adventure = new Genres("Adventure");
		adventure = genresDao.create(adventure);
		Genres fantasy = new Genres("Fantasy");
		fantasy = genresDao.create(fantasy);
		Genres romance = new Genres("Romance");
		romance = genresDao.create(romance);
		
		// Create MovieGenres
		movieGenresDao.create(new MovieGenres(fantasy, starWars1));
		movieGenresDao.create(new MovieGenres(fantasy, starWars2));
		movieGenresDao.create(new MovieGenres(fantasy, starWars3));
		movieGenresDao.create(new MovieGenres(fantasy, starWars4));
		movieGenresDao.create(new MovieGenres(fantasy, harryPotter1));
		movieGenresDao.create(new MovieGenres(fantasy, harryPotter2));
		movieGenresDao.create(new MovieGenres(fantasy, harryPotter3));
		movieGenresDao.create(new MovieGenres(fantasy, harryPotter4));
		movieGenresDao.create(new MovieGenres(action, fastAndFurious1));
		movieGenresDao.create(new MovieGenres(action, fastAndFurious2));
		movieGenresDao.create(new MovieGenres(action, fastAndFurious3));
		movieGenresDao.create(new MovieGenres(action, fastAndFurious4));

		// Create MovieNights
		Calendar today = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		Calendar nextWeek = Calendar.getInstance();
		nextWeek.add(Calendar.WEEK_OF_MONTH, 1);
		
		MovieNights hp1MovieNight = movieNightsDao.create(new MovieNights(today.getTime(), harryPotter1));
		MovieNights ff3MovieNight = movieNightsDao.create(new MovieNights(tomorrow.getTime(), fastAndFurious3));
		MovieNights sw2MovieNight = movieNightsDao.create(new MovieNights(nextWeek.getTime(), starWars2));
		
		// Create Attendances
		Attendances hp1attendance1 = attendancesDao.create(new Attendances(hp1MovieNight, val, Attendances.AttendanceType.valueOf("host")));
		Attendances hp1attendance2 = attendancesDao.create(new Attendances(hp1MovieNight, olivia, Attendances.AttendanceType.valueOf("attendee")));
		Attendances hp1attendance3 = attendancesDao.create(new Attendances(hp1MovieNight, tim, Attendances.AttendanceType.valueOf("attendee")));
		Attendances sw2attendance1 = attendancesDao.create(new Attendances(sw2MovieNight, tim, Attendances.AttendanceType.valueOf("host")));
		Attendances sw2attendance2 = attendancesDao.create(new Attendances(sw2MovieNight, olivia, Attendances.AttendanceType.valueOf("attendee")));

		
		// Create Reviews
		Reviews hp1GoodReview = reviewsDao.create(new Reviews("AMAZING", 9.0, val, harryPotter1));
		Reviews hp1MehReview = reviewsDao.create(new Reviews("Meh", 5.0, tim, harryPotter1));
		Reviews ff1GoodReview = reviewsDao.create(new Reviews("Incredible!", 8.0, tim, fastAndFurious1));
		Reviews ff1MehReview = reviewsDao.create(new Reviews("It was alright", 4.0, olivia, fastAndFurious1));
		Reviews sw4GoodReview = reviewsDao.create(new Reviews("Wonderful", 9.0, olivia, starWars4));
		Reviews sw4MehReview = reviewsDao.create(new Reviews("Not a total waste of time", 6.0, val, starWars4));
		
		// Create WatchedMovies
		WatchedMovies valWatchedHp1 = watchedMoviesDao.create(new WatchedMovies(harryPotter1, val));
		WatchedMovies valWatchedHp2 = watchedMoviesDao.create(new WatchedMovies(harryPotter2, val));
		WatchedMovies valWatchedSw4 = watchedMoviesDao.create(new WatchedMovies(starWars4, val));
		
		WatchedMovies timWatchedHp1 = watchedMoviesDao.create(new WatchedMovies(harryPotter1, tim));
		WatchedMovies timWatchedFf1 = watchedMoviesDao.create(new WatchedMovies(fastAndFurious1, tim));
		WatchedMovies timWatchedFf2 = watchedMoviesDao.create(new WatchedMovies(fastAndFurious2, tim));
		WatchedMovies timWatchedSw4 = watchedMoviesDao.create(new WatchedMovies(starWars4, tim));
		
		WatchedMovies oliviaWatchedFf1 = watchedMoviesDao.create(new WatchedMovies(fastAndFurious1, olivia));
		WatchedMovies oliviaWatchedSw4 = watchedMoviesDao.create(new WatchedMovies(starWars4, olivia));
		
		// Create LikeMovies
		LikeMovies valLikesHp1 = likeMoviesDao.create(new LikeMovies(harryPotter1, val));
		LikeMovies valLikesHp2 = likeMoviesDao.create(new LikeMovies(harryPotter2, val));
		
		LikeMovies timLikesFf2 = likeMoviesDao.create(new LikeMovies(fastAndFurious2, tim));
		LikeMovies timLikesSw4 = likeMoviesDao.create(new LikeMovies(starWars4, tim));
		
		LikeMovies oliviaLikesSw4 = likeMoviesDao.create(new LikeMovies(starWars4, olivia));
		
		// Create WishListMovies
		WishListMovies valWishListHp3 = wishListMoviesDao.create(new WishListMovies(harryPotter3, val));
		WishListMovies valWishListHp4 = wishListMoviesDao.create(new WishListMovies(harryPotter4, val));

		WishListMovies timWishListFf3 = wishListMoviesDao.create(new WishListMovies(fastAndFurious3, tim));
		WishListMovies timWishListFf4 = wishListMoviesDao.create(new WishListMovies(fastAndFurious4, tim));
		
		WishListMovies oliviaWishListSw2 = wishListMoviesDao.create(new WishListMovies(starWars2, olivia));
		WishListMovies oliviaWishListSw3 = wishListMoviesDao.create(new WishListMovies(starWars3, olivia));

		
				
		// READ.
		List<Persons> pList1 = personsDao.getPersonsByFirstName("Emma");
			for(Persons p : pList1) {
				System.out.format("Looping persons: pid:%d f:%s l:%s \n",
					p.getPersonId(), p.getFirstName(), p.getLastName());
		}
				
		List<Reviews> rList1 = reviewsDao.getReviewsForUser(val);
			for(Reviews r : rList1) {
				System.out.format("Looping persons: rid:%d r:%s c:%s \n",
					r.getReviewId(), r.getRating(), r.getContent());
		}

		
		//UPDATE.
		
		// Update Genre Name
		sciFi = genresDao.updateName(sciFi, "Science Fiction");
		// Update Movie Title
		starWars1 = moviesDao.updateTitle(starWars1, "Star Wars 6");
		// Update Person Last Name
		emmaWatson = personsDao.updateLastName(emmaWatson, "Watsen");
		// Update Reviews Content
		hp1MehReview = reviewsDao.updateContent(hp1MehReview, "ehhhhhhhh");
		// Update Users Email
		olivia = usersDao.updateEmail(olivia, "oliviajdangelo@gmail.com");
		
		
		
		//DELETE.
		
		attendancesDao.delete(hp1attendance1);
		genresDao.delete(action);
		likeMoviesDao.delete(valLikesHp1);
		watchedMoviesDao.delete(oliviaWatchedFf1);
		
		
		
	}
}
