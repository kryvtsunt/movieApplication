package movienight.servlet.movie;

import movienight.dal.*;
import movienight.model.*;

import java.io.IOException;
import java.sql.SQLException;
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


@WebServlet("/moviedelete")
public class MovieDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Movie");        
        req.getRequestDispatcher("/MovieDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String movieIdStr = req.getParameter("movieId");
        if (movieIdStr == null || movieIdStr.trim().isEmpty()) {
            messages.put("title", "Invalid MovieId");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Movie.
        	try {
        		int movieId = Integer.parseInt(movieIdStr);
        		Movies movie = new Movies(movieId);
        		movie = moviesDao.delete(movie);
        		// Update the message.
		        if (movie == null) {
		            messages.put("title", "Successfully deleted " + movieId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + movieId);
		        	messages.put("disableSubmit", "false");
		        }
        	} catch (NumberFormatException e) {
        		messages.put("title", "Invalid MovieId Format");
                messages.put("disableSubmit", "true");
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/MovieDelete.jsp").forward(req, resp);
    }
}
