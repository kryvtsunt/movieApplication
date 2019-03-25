CREATE SCHEMA IF NOT EXISTS MovieNightApplication;
USE MovieNightApplication;

DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS Attendances;
DROP TABLE IF EXISTS WatchedMovies;
DROP TABLE IF EXISTS WishListMovies;
DROP TABLE IF EXISTS LikeMovies;
DROP TABLE IF EXISTS MovieGenres;
DROP TABLE IF EXISTS LikePersons;
DROP TABLE IF EXISTS Casts;
DROP TABLE IF EXISTS Persons;
DROP TABLE IF EXISTS MovieNights;
DROP TABLE IF EXISTS Movies;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Genres;

CREATE TABLE Users (
	UserName VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    Email VARCHAR(255) NOT NULL,
    Phone VARCHAR(255),
    CONSTRAINT pk_Users_UserName PRIMARY KEY (UserName),
    CONSTRAINT uc_Users_UserName UNIQUE (UserName),
	CONSTRAINT uc_Users_Email UNIQUE (Email),
	CONSTRAINT uc_Users_Phone UNIQUE (Phone)
);

CREATE TABLE Genres (
	GenreId INT AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
	CONSTRAINT pk_Genres_GenreId PRIMARY KEY (GenreId),
    CONSTRAINT uc_Genres_Name UNIQUE (Name)
);

CREATE TABLE Movies (
	MovieId INT AUTO_INCREMENT,
	Title VARCHAR(255) NOT NULL,
    ReleaseYear INT NOT NULL,
    RunTime INT,
    CONSTRAINT pk_Movies_MovieId PRIMARY KEY (MovieId)
);

CREATE TABLE MovieGenres (
	MovieGenreId INT AUTO_INCREMENT,
    GenreId INT NOT NULL,
    MovieId INT NOT NULL,
	CONSTRAINT pk_MovieGenres_MovieGenreId PRIMARY KEY (MovieGenreId),
	CONSTRAINT fk_MovieGenres_GenreId FOREIGN KEY (GenreId) REFERENCES Genres(GenreId) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_MovieGenres_MovieId FOREIGN KEY (MovieId) REFERENCES Movies(MovieId) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE LikeMovies (
	LikeMovieId INT AUTO_INCREMENT,
	MovieId INT NOt NULL,
    UserName VARCHAR(255),
    CONSTRAINT pk_LikeMovies_LikeMovieId PRIMARY KEY (LikeMovieId),
	CONSTRAINT fk_LikeMovies_MovieId FOREIGN KEY (MovieId) REFERENCES Movies(MovieId) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_LikeMovies_UserName FOREIGN KEY (UserName) REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE WatchedMovies (
	WatchedMovieId INT AUTO_INCREMENT,
	MovieId INT,
    UserName VARCHAR(255) NOT NULL,
    CONSTRAINT pk_WatchedMovies_WatchedMovieId PRIMARY KEY (WatchedMovieId),
	CONSTRAINT fk_WatchedMovies_MovieId FOREIGN KEY (MovieId) REFERENCES Movies(MovieId) ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_WatchedMovies_UserName FOREIGN KEY (UserName) REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE WishListMovies (
	WishListMovieId INT AUTO_INCREMENT,
	MovieId INT NOT NULL,
    UserName VARCHAR(255) NOT NULL,
    CONSTRAINT pk_WishListMovies_WishListMovieId PRIMARY KEY (WishListMovieId),
	CONSTRAINT fk_WishListMovies_MovieId FOREIGN KEY (MovieId) REFERENCES Movies(MovieId) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_WishListMovies_UserName FOREIGN KEY (UserName) REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Persons (
	PersonId INT AUTO_INCREMENT,
	FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    DateOfBirth DATE,
    CONSTRAINT pk_Persons_PersonId PRIMARY KEY (PersonId)
);

CREATE TABLE LikePersons (
	LikePersonId INT AUTO_INCREMENT,
	PersonId INT NOT NULL,
    UserName VARCHAR(255),
    CONSTRAINT pk_LikePersons_LikeMovieId PRIMARY KEY (LikePersonId),
	CONSTRAINT fk_LikePersons_PersonId FOREIGN KEY (PersonId) REFERENCES Persons(PersonId) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_LikePersons_UserName FOREIGN KEY (UserName) REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Casts (
	CastId INT AUTO_INCREMENT,
	MovieId INT NOT NULL,
	PersonId INT NOT NULL,
    Role ENUM('Actor','Director'), 
    CONSTRAINT pk_Casts_CastId PRIMARY KEY (CastId),
	CONSTRAINT fk_Casts_MovieId FOREIGN KEY (MovieId) REFERENCES Movies(MovieId) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Casts_PersonId FOREIGN KEY (PersonId) REFERENCES Persons(PersonId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE MovieNights (
	MovieNightId INT AUTO_INCREMENT,
	MovieId INT NOT NULL,
	Date DATE NOT NULL,
    CONSTRAINT pk_MovieNights_MovieNightId PRIMARY KEY (MovieNightId),
	CONSTRAINT fk_MovieNights_MovieId FOREIGN KEY (MovieId) REFERENCES Movies(MovieId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Attendances (
	AttendanceId INT AUTO_INCREMENT,
    MovieNightId INT NOT NULL,
    UserName VARCHAR(255) NOT NULL,
    AttendanceType ENUM('HOST','ATTENDEE'),
    CONSTRAINT pk_Attendances_AttendanceId PRIMARY KEY (AttendanceId),
	CONSTRAINT fk_Attendances_MovieNightId FOREIGN KEY (MovieNightId) REFERENCES MovieNights(MovieNightId) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Attendances_UserName FOREIGN KEY (UserName) REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Reviews (
	ReviewId INT AUTO_INCREMENT,
    Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Content LONGTEXT NOt NULL,
    Rating DECIMAL(2,1) NOT NULL,
    UserName VARCHAR(255),
    MovieId INT NOT NULL,
    CONSTRAINT pk_Reviews_ReviewId PRIMARY KEY (ReviewId),
	CONSTRAINT uq_Reviews_Review UNIQUE (UserName, MovieId),
	CONSTRAINT fk_Reviews_UserName FOREIGN KEY (UserName) REFERENCES Users(UserName) ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_Reviews_MovieId FOREIGN KEY (MovieId) REFERENCES Movies(MovieId) ON UPDATE CASCADE ON DELETE CASCADE);
    
INSERT INTO Users(UserName, Password, FirstName, LastName, Email, Phone) 
VALUES
	('Tim', 'pass', 'Tymoii', 'Kryvtsun', 'kryvtsun.t@husky.neu.edu', '6179010901'),
	('ValCharry','pass', 'Valerie', 'Charry', 'charry.v@husky.neu.edu', '6173409122'),
	('OliveDang','pass', 'Olivia', 'D\'Angelo', 'dangelo.ol@husky.neu.edu', '8609183325'),
	('Pembers67','pass', 'Pemberton', 'Harry', 'harry.pemberton@aol.com', '6473929976'),
	('lardude09','pass', 'Larry', 'James', 'larryj@gmail.com', '9387368392'),
    ('user1','pass', NULL, NULL, 'user@email.com', NULL),
    ('Olivia','pass', NULL, NULL, 'olivia@email.com', NULL);
    
INSERT INTO Genres(GenreId, Name) 
Values (1,'Drama'),(2,'Comedy'),(3,'Action'),(4,'Adventure'),(5,'Music'),(6,'Horror'),(7, 'Mystery'),(8,'Documentary'),(9,'Romance');

INSERT INTO Movies(MovieId, Title, ReleaseYear, Runtime)
VALUES
	(1, "Captain America: The First Avenger", 2011, 124),
    (2, "Captain America: The Winter Soldier", 2014, 136),
    (3, "Iron Man", 2008, 126),
    (4, "Lego Movie", 2014, 101),
    (5, "Halloween", 1970, 110);

INSERT INTO MovieGenres(GenreId, MovieId)
VALUES
	(3, 1), (3, 2), (3, 3), (3, 4), (4, 1), (4, 2), (4, 3), (4, 4), (6, 5);
    
INSERT INTO MovieNights(MovieNightId, MovieId, Date) 
VALUES
	(1, 1, '2019-02-08'),
	(2, 4, '2019-03-28');

INSERT INTO Attendances(MovieNightId, UserName, AttendanceType)
VALUES
	(1, 'Tim', 'Host'),
    (1, 'user1', 'Attendee'),
    (1, 'ValCharry', 'Attendee');
    
INSERT INTO Reviews(Created, Content, Rating, UserName, MovieId) 
VALUES
	('2019-02-01', 'It was okay. Tooooo scary at some parts.', '6', 'Olivia', 2),
    ('2019-02-08', 'Great action! Solid overall.', '8', 'Tim', 1);
    
INSERT INTO Persons(PersonId, FirstName, LastName)
VALUES
	('1', 'Joe', 'Russo'),
    ('2', 'Anthony', 'Russo');

INSERT INTO Casts(MovieId, PersonId, Role)
VALUES
	(2, 1, 'Director'),
    (2, 2, 'Director');

INSERT INTO LikeMovies(MovieId, UserName)
VALUES 
	(1, 'ValCharry'),
    (2, 'ValCharry'),
    (3, 'ValCharry'),
    (4, 'ValCharry'),
    (2, 'Tim'),
    (3, 'Tim'),
    (1, NULL),(1, NULL),(1, NULL),(1, NULL);

INSERT INTO WatchedMovies(MovieId, UserName)
VALUES
	(1, 'Tim'),
    (1, 'ValCharry'),
    (2, 'Tim'),
    (2, 'Olivia');

