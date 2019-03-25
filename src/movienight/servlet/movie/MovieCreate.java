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


@WebServlet("/moviecreate")
public class MovieCreate extends HttpServlet {
	
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
        //Just render the JSP.   
        req.getRequestDispatcher("/MovieCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String title = req.getParameter("title");
        if (title == null || title.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
			// Create the Movie.
			String runTime = req.getParameter("runtime");
			String relYear = req.getParameter("releaseYear");
			
			int runtime, releaseYear;
			try {
        		runtime = Integer.parseInt(runTime);
        		releaseYear = Integer.parseInt(relYear);
        	} catch (NumberFormatException e) {
        		e.printStackTrace();
				throw new IOException(e);
        	}
		    try {
		    	Movies movie = new Movies(title, runtime, releaseYear);
		    	movie = moviesDao.create(movie);
		    	messages.put("success", "Successfully created " + title);
		    } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
		    }
		}
			
        
        req.getRequestDispatcher("/MovieCreate.jsp").forward(req, resp);
    }
}
