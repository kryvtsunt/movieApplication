package movienight.servlet.movienight;

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


@WebServlet("/movienightcreate")
public class MovieNightCreate extends HttpServlet {
	
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
        //Just render the JSP.   
        req.getRequestDispatcher("/MovieNightCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        String movieId = req.getParameter("movieId");
		String date = req.getParameter("date");
        if (movieId == null || movieId.trim().isEmpty()) {
            messages.put("success", "Invalid movieId");
        } 
        else if (date == null || date.trim().isEmpty()) {
            messages.put("success", "Invalid date");
        } 
        else
			try {

			    	Movies mv = MoviesDao.getInstance().getMovieById(Integer.parseInt(movieId));
			    	MovieNights mn = new MovieNights(java.sql.Date.valueOf(date), mv);
			    	MovieNights nmn = dao.create(mn);
			    	messages.put("success", "Successfully created movieNight with id " + nmn.getMovieNightId());
				    
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        
        req.getRequestDispatcher("/MovieNightCreate.jsp").forward(req, resp);
    }
}
