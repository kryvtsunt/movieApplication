package movienight.servlet.movienight;

import movienight.dal.MovieNightsDao;
import movienight.dal.UsersDao;
import movienight.model.MovieNights;
import movienight.model.Users;

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


@WebServlet("/findmovienights")
public class FindMovieNights extends HttpServlet {
	
	protected MovieNightsDao dao;
	
	@Override
	public void init() throws ServletException {
		dao = MovieNightsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<MovieNights> mns = new ArrayList<MovieNights>();
        
        String id = req.getParameter("MovieNightId");
        if (id == null || id.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	try {
            	mns.add(dao.getMovieNightById(Integer.parseInt(id)));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + id);
        	messages.put("previousId", id);
        }
        req.setAttribute("movieNights", mns);
        
        req.getRequestDispatcher("/FindMovieNights.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<MovieNights> mns = new ArrayList<MovieNights>();
        
        String id = req.getParameter("MovieNightId");
        if (id == null || id.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	try {
            	mns.add(dao.getMovieNightById(Integer.parseInt(id)));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + id);
        }
        req.setAttribute("movieNights", mns);
        
        req.getRequestDispatcher("/FindMovieNights.jsp").forward(req, resp);
    }
}