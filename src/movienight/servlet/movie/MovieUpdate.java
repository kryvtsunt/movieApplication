package movienight.servlet.movie;

import movienight.dal.*;
import movienight.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/movieupdate")
public class MovieUpdate extends HttpServlet {
	
	protected MoviesDao moviesDao;
	
	@Override
	public void init() throws ServletException {
		moviesDao = MoviesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve movie and validate.
        String movieIdStr = req.getParameter("movieId");
        if (movieIdStr == null || movieIdStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Movie ID.");
        } else {
        	try {
        		int movieId = Integer.parseInt(movieIdStr);
        		Movies movie = moviesDao.getMovieById(movieId);
        		if(movie == null) {
        			messages.put("success", "Movie does not exist.");
        		}
        		req.setAttribute("movie", movie);
        	} catch (NumberFormatException e) {
        		messages.put("success", "Invalid MovieId Format");
        	} catch (SQLException e) {
        		e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/MovieUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve movie and validate.
        String movieIdStr = req.getParameter("movieId");
        if (movieIdStr == null || movieIdStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Movie ID.");
        } else {
        	try {
        		int movieId = Integer.parseInt(movieIdStr);
        		Movies movie = moviesDao.getMovieById(movieId);
        		if(movie == null) {
        			messages.put("success", "Movie does not exist. No update to perform.");
        		} else {
        			String newTitle = req.getParameter("title");
        			if (newTitle == null || newTitle.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid title.");
        	        } else {
        	        	movie = moviesDao.updateTitle(movie, newTitle);
        	        	messages.put("success", "Successfully updated " + movie.getTitle());
        	        }
        		}
        		req.setAttribute("movie", movie);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/MovieUpdate.jsp").forward(req, resp);
    }
}
