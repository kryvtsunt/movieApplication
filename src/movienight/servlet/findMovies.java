package movienight.servlet;

import movienight.dal.MoviesDao;
import movienight.model.Movies;

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


@WebServlet("/findmovies")
public class findMovies extends HttpServlet {
	
	protected MoviesDao movieDao;
	
	@Override
	public void init() throws ServletException {
		movieDao = MoviesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Movies> movies = new ArrayList<Movies>();
        
        String title = req.getParameter("title");
        if (title == null || title.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	try {
            	movies = movieDao.getMovieByTitle(title);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + title);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousFirstName", title);
        }
        req.setAttribute("movies", movies);
        
        req.getRequestDispatcher("/FindMovies.jsp").forward(req, resp);
	}
	
//	@Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp)
//    		throws ServletException, IOException {
//        // Map for storing messages.
//        Map<String, String> messages = new HashMap<String, String>();
//        req.setAttribute("messages", messages);
//
//        List<BlogUsers> blogUsers = new ArrayList<BlogUsers>();
//        
//        // Retrieve and validate name.
//        // firstname is retrieved from the form POST submission. By default, it
//        // is populated by the URL query string (in FindUsers.jsp).
//        String firstName = req.getParameter("firstname");
//        if (firstName == null || firstName.trim().isEmpty()) {
//            messages.put("success", "Please enter a valid name.");
//        } else {
//        	// Retrieve BlogUsers, and store as a message.
//        	try {
//            	blogUsers = blogUsersDao.getBlogUsersFromFirstName(firstName);
//            } catch (SQLException e) {
//    			e.printStackTrace();
//    			throw new IOException(e);
//            }
//        	messages.put("success", "Displaying results for " + firstName);
//        }
//        req.setAttribute("blogUsers", blogUsers);
//        
//        req.getRequestDispatcher("/FindUsers.jsp").forward(req, resp);
//    }
}
