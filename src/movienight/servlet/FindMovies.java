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
public class FindMovies extends HttpServlet {
	
	protected MoviesDao moviesDao;
	
	@Override
	public void init() throws ServletException {
		moviesDao = MoviesDao.getInstance();
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
            	movies = moviesDao.getMoviesByTitle(title);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + title);
        	messages.put("previousFirstName", title);
        }
        req.setAttribute("movies", movies);
        
        req.getRequestDispatcher("/FindMovies.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Movies> movies = new ArrayList<Movies>();
        
        String title = req.getParameter("title");
        if (title == null || title.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	try {
            	movies = moviesDao.getMoviesByTitle(title);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + title);
        }
        req.setAttribute("movies", movies);
        
        req.getRequestDispatcher("/FindMovies.jsp").forward(req, resp);
    }
}
