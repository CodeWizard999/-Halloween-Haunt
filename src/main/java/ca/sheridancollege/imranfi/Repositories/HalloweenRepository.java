//This is a repository that provides a database access layer for performing CRUD operations on the TICKETS table of a database.

package ca.sheridancollege.imranfi.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.imranfi.Beans.Ticket;
import ca.sheridancollege.imranfi.Beans.User;

@Repository
public class HalloweenRepository {

	//a class from Spring Framework's JDBC support to interact with the database.
	private NamedParameterJdbcTemplate jdbc;

	//contructor that takes NamedParameterJdbcTemplate  as a parameter
	public HalloweenRepository(NamedParameterJdbcTemplate jdbc) {
		super();
		this.jdbc = jdbc;
	}

	// Adds a new ticket to the database.
	public void addTicket(Ticket ticket) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO TICKETS (name, age, gender, pet, status, price)"
				+ " VALUES (:name,:age,:gender,:pet,:status, :price)";

		parameters.addValue("name", ticket.getName());
		parameters.addValue("age", ticket.getAge());
		parameters.addValue("gender", ticket.getGender());
		parameters.addValue("pet", ticket.getPet());
		parameters.addValue("status", ticket.getStatus());
		parameters.addValue("price", ticket.getPrice());
		

		jdbc.update(query, parameters);
	}

	// Retrieves all tickets from the database.
	public ArrayList<Ticket> getTickets() {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "SELECT * FROM TICKETS";

		ArrayList<Ticket> tickets = (ArrayList<Ticket>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<Ticket>(Ticket.class));

		return tickets;
	}

	// Retrieves a ticket from the database based on its ID.
	public Ticket getTicketById(int id) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "SELECT * FROM TICKETS WHERE id =:id";

		parameters.addValue("id", id);

		ArrayList<Ticket> tickets = (ArrayList<Ticket>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<Ticket>(Ticket.class));

		if (!(tickets.isEmpty())) {

			return tickets.get(0);
		}
		return null;

	}

	// Updates a ticket in the database.
	public void editTicket(Ticket ticket) {

		String query = "UPDATE TICKETS SET name= :name, age= :age, gender= :gender,pet= :pet, status = status ,  price= :price WHERE id =:id ";
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("id", ticket.getId());
		parameters.addValue("name", ticket.getName());
		parameters.addValue("age", ticket.getAge());
		parameters.addValue("gender", ticket.getGender());
		parameters.addValue("pet", ticket.getPet());
		parameters.addValue("status", ticket.getStatus());
		parameters.addValue("price", ticket.getPrice());
		

		jdbc.update(query, parameters);
		
	}

	// Deletes a ticket from the database based on its ID.
	public void deleteTicketById(int id) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "DELETE FROM TICKETS WHERE id= :id";

		parameters.addValue("id", id);

		jdbc.update(query, parameters);
	}

	// Finds a user account in the database based on the username.
	public User findUserAccount(String userName) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user WHERE userName=:name";
		parameters.addValue("name", userName);

		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,

				new BeanPropertyRowMapper<User>(User.class));

		return (users.size() > 0) ? users.get(0) : null;

	}

	//  Retrieves all user accounts from the database.
	public ArrayList<User> getUsers() {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where not userName=:name ORDER BY userName";
		parameters.addValue("name", "vender");

		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,

				new BeanPropertyRowMapper<User>(User.class));

		return users;

	}

	//  Retrieves all tickets associated with a given username.
	public ArrayList<Ticket> getTicketsByUserName(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM tickets where name=:name";
		parameters.addValue("name", userName);

		ArrayList<Ticket> tickets = (ArrayList<Ticket>) jdbc.query(query, parameters,

				new BeanPropertyRowMapper<Ticket>(Ticket.class));

		return tickets;
	}

	// Retrieves the roles associated with a given user ID.
	public List<String> getRolesById(Long userId) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT user_role.userId, sec_role.roleName " + "FROM user_role, sec_role "
				+ "WHERE user_role.roleId = sec_role.roleId AND userId=:Id";
		parameters.addValue("Id", userId);

		ArrayList<String> roles = new ArrayList<String>();

		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			roles.add((String) row.get("roleName"));
		}
		return roles;

	}
	// 	Adds new user to the database
	public void addNewUser(User user) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO SEC_USER (userName, encryptedPassword, ENABLED )" + " VALUES (:name,:pass, 1)";
		parameters.addValue("name", user.getUserName());
		parameters.addValue("pass", passwordEncoder().encode(user.getEncryptedPassword()));

		jdbc.update(query, parameters);

	}

	// Assings new role to the user in the database
	public void addUserRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO USER_ROLE (userId, roleId )" + " VALUES (:userId,:roleId)";

		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);

		jdbc.update(query, parameters);

	}

	// encrypts the password
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//these methods are used to calculate different statistics

	// Counts the number of tickets in a given list of tickets.
	public String countTickets(ArrayList<Ticket> tickets) {

		int ammount = 0;
		for (Ticket ticket : tickets) {
			ammount++;
		}
		return Integer.toString(ammount);
	}

	// Calculates the sum of ticket prices in a given list of tickets.
	public String sumOfTickets(ArrayList<Ticket> tickets) {

		double sum = 0;
		for (Ticket ticket : tickets) {
			sum += ticket.getPrice();
		}
		return Double.toString(sum);
	}

	//  Calculates the number of tickets in a given list of tickets that are for individuals 18 years old or younger.
	public String total18AndUnder(ArrayList<Ticket> tickets) {
		int sum = 0;
		for (Ticket ticket : tickets) {
			if (ticket.getAge() <= 18) {
				sum++;
			}
		}
		return Integer.toString(sum);
	}

	// Calculates the number of tickets in a given list of tickets that are for individuals over 18 years old.
	public String totalOver18(ArrayList<Ticket> tickets) {
		int sum = 0;
		for (Ticket ticket : tickets) {
			if (ticket.getAge() > 18) {
				sum++;
			}
		}
		return Integer.toString(sum);
	}

	// Calculates the number of tickets in a given list of tickets that include pets.
	public String totalPets(ArrayList<Ticket> tickets) {

		int sum = 0;
		for (Ticket ticket : tickets) {
			if (ticket.getPet().equalsIgnoreCase("yes")) {
				sum++;
			}
		}
		return Integer.toString(sum);
	}

}