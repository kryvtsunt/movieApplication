package movienight.servlet.person;

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


@WebServlet("/personlikes")
public class PersonLikes extends HttpServlet {
	
	protected LikePersonsDao likePersonsDao;
	
	@Override
	public void init() throws ServletException {
		likePersonsDao = LikePersonsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve person and validate.
        String personIdStr = req.getParameter("personId");
        if (personIdStr == null || personIdStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Person ID.");
        } else {
        	try {
        		int personId = Integer.parseInt(personIdStr);
        		PersonsDao personsDao = PersonsDao.getInstance();
        		Persons person = personsDao.getPersonById(personId);
        		if(person == null) {
        			messages.put("success", "Person does not exist.");
        		}
        		List<LikePersons> likePersons = likePersonsDao.getLikePersonsForPerson(person);
        		req.setAttribute("likes", likePersons);
        	} catch (NumberFormatException e) {
        		messages.put("success", "Invalid PersonId Format");
        	} catch (SQLException e) {
        		e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/LikePerson.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<LikePersons> likePersons = new ArrayList<LikePersons>();
        
     // Retrieve person and validate.
        String personIdStr = req.getParameter("personId");
        if (personIdStr == null || personIdStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid Person ID.");
        } else {
        	try {
        		int personId = Integer.parseInt(personIdStr);
        		PersonsDao personsDao = PersonsDao.getInstance();
        		Persons person = personsDao.getPersonById(personId);
        		if(person == null) {
        			messages.put("success", "Person does not exist.");
        		}
        		likePersons = likePersonsDao.getLikePersonsForPerson(person);
        	} catch (NumberFormatException e) {
        		messages.put("success", "Invalid PersonId Format");
        	} catch (SQLException e) {
        		e.printStackTrace();
				throw new IOException(e);
	        }
        }
        req.setAttribute("likes", likePersons);
        
        req.getRequestDispatcher("/LikePerson.jsp").forward(req, resp);
    }
}
