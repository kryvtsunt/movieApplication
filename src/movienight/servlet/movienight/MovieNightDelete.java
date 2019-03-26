package movienight.servlet.movienight;

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


@WebServlet("/movienightdelete")
public class MovieNightDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete MovieNight");        
        req.getRequestDispatcher("/MovieNightDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String movienightId = req.getParameter("movienightId");
        if (movienightId == null || movienightId.trim().isEmpty()) {
            messages.put("title", "Invalid id");
            messages.put("disableSubmit", "true");
        } else {
	        try {
	        	MovieNights mv = dao.delete(dao.getMovieNightById(Integer.parseInt(movienightId)));
	        	// Update the message.
		        if (mv == null) {
		            messages.put("title", "Successfully deleted " + movienightId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + movienightId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/MovieNightDelete.jsp").forward(req, resp);
    }
}
