package movienight.servlet.person;

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


@WebServlet("/persondelete")
public class PersonDelete extends HttpServlet {
	
	protected PersonsDao personsDao;
	
	@Override
	public void init() throws ServletException {
		personsDao = PersonsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Person");        
        req.getRequestDispatcher("/PersonDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String personIdStr = req.getParameter("personId");
        if (personIdStr == null || personIdStr.trim().isEmpty()) {
            messages.put("title", "Invalid PersonId");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Person.
        	try {
        		int personId = Integer.parseInt(personIdStr);
        		Persons person = new Persons(personId);
        		person = personsDao.delete(person);
        		// Update the message.
		        if (person == null) {
		            messages.put("title", "Successfully deleted " + personId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + personId);
		        	messages.put("disableSubmit", "false");
		        }
        	} catch (NumberFormatException e) {
        		messages.put("title", "Invalid PersonId Format");
                messages.put("disableSubmit", "true");
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PersonDelete.jsp").forward(req, resp);
    }
}
