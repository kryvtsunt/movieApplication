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


@WebServlet("/personcreate")
public class PersonCreate extends HttpServlet {
	
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
        //Just render the JSP.   
        req.getRequestDispatcher("/PersonCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");

        if (firstname == null || firstname.trim().isEmpty()) {
            messages.put("success", "Invalid First Name");
        } else if (lastname == null || lastname.trim().isEmpty()) {
            messages.put("success", "Invalid Last Name");
        } else {
			// Create the Person.
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	String stringDob = req.getParameter("dob");
        	Date dob = new Date();
        	try {
        		dob = dateFormat.parse(stringDob);
        	} catch (ParseException e) {
        		e.printStackTrace();
				throw new IOException(e);
        	}
        
		    try {
		    	Persons person = new Persons(firstname, lastname, dob);
		    	person = personsDao.create(person);
		    	messages.put("success", "Successfully created " + person.getFirstName() + " " + person.getLastName());
		    } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
		    }
		}
			
        
        req.getRequestDispatcher("/PersonCreate.jsp").forward(req, resp);
    }
}
