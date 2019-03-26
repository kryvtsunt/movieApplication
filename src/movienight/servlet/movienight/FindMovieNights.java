package movienight.servlet.movienight;

import movienight.dal.MovieNightsDao;
import movienight.model.MovieNights;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
        
        String date = req.getParameter("date");
        if (date == null || date.trim().isEmpty()) {
            messages.put("success", "Please enter a valid date.");
        } else {
        	try {
            	mns = (dao.getMovieNightByDate(java.sql.Date.valueOf(date)));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + date);
        	messages.put("previousDate", date);
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
        
        String date = req.getParameter("date");
        if (date == null || date.trim().isEmpty()) {
            messages.put("success", "Please enter a valid date.");
        } else {
        	try {
            	mns = (dao.getMovieNightByDate(java.sql.Date.valueOf(date)));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + date);
        }
        req.setAttribute("movieNights", mns);
        
        req.getRequestDispatcher("/FindMovieNights.jsp").forward(req, resp);
    }
}