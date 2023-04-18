/* This is a Spring Boot controller class. 
It contains methods that handle HTTP requests for various endpoints of a web application related to Halloween tickets. 
 */
package ca.sheridancollege.imranfi.Controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ca.sheridancollege.imranfi.Beans.Ticket;
import ca.sheridancollege.imranfi.Beans.User;
import ca.sheridancollege.imranfi.Repositories.HalloweenRepository;

@Controller
public class HalloweenController {

	private HalloweenRepository halRepo;

	// constructor has a repository object (HalloweenRepository), which is used to perform database operations.
	public HalloweenController(HalloweenRepository halRepo) {
		super();
		this.halRepo = halRepo;
	}

	// goHome() This method returns the home.html template in response to a GET request to the root URL /.
	@GetMapping("/")
	public String goHome() {
		return "home.html";
	}

	// goAdd() returns add.html in response to GET request to URL /add, with model attributes and authentication object.
	@GetMapping("/add")
	public String goAdd(Model model, Authentication authentication) {
		
		String username = authentication.getName();
		
		ArrayList<String> roles = new ArrayList<String>();
		
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			roles.add(ga.getAuthority());
		}

		for (String role : roles) {
			if (role.equalsIgnoreCase("ROLE_GUEST")) {
				model.addAttribute("role", "ROLE_GUEST");
			}else{
				model.addAttribute("role", "ROLE_VENDER");
			}
		}
		model.addAttribute("guestName",username );
		model.addAttribute("users", halRepo.getUsers());
		model.addAttribute("ticket", new Ticket());
		return "add.html";
	}
	// goLogin() returns login.html in response to GET request to URL /login.
	@GetMapping("/login")
	public String goLogin() {
		return "login.html";
	}

	// goAccessDenied() returns accessDenied.html in response to GET request to URL /access.
	@GetMapping("/access")
	public String goAccessDenied() {
		return "accessDenied.html";
	}
	// goRegister() returns register.html in response to GET request to URL /register, with empty User object in the model.
	@GetMapping("/register")
	public String goRegister(Model model) {
		model.addAttribute("user", new User());
		return "register.html";
	}

	// proccessRegister() receives User object from POST request to URL /register, adds user to database, assigns "ROLE_GUEST" role, and redirects to /register.
	@PostMapping("/register")
	public String proccessRegister(@ModelAttribute User user) {
		halRepo.addNewUser(user);
		long userId = halRepo.findUserAccount(user.getUserName()).getUserId();
		halRepo.addUserRole(userId, 2);
		return "redirect:/register";
	}

	// proccessAdd() receives Ticket object from GET request to URL /proccessAdd, adds ticket to database, and redirects to /add.
	@GetMapping("/proccessAdd")
	public String proccessAdd(@ModelAttribute Ticket ticket) {
		halRepo.addTicket(ticket);
		return "redirect:/add";
	}

	// goView() returns view.html in response to GET request to URL /view, with model attributes and authentication object.
	@GetMapping("/view")
	public String goView(Model model, Authentication authentication) {
		String username = authentication.getName();
		ArrayList<String> roles = new ArrayList<String>();
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		
		
		ArrayList<Ticket> tickets = halRepo.getTickets();

		for (String role : roles) {
			if (role.equalsIgnoreCase("ROLE_GUEST")) {
				ArrayList<Ticket> userTickets = halRepo.getTicketsByUserName(username);
				model.addAttribute("tickets",userTickets );
				double sum =0.00; 
				double tax= 0.00;
				double total = 0.00;
				for(Ticket t: userTickets) {
					sum += t.getPrice();
				}
				tax = 0.13 * sum; 
				total= tax + sum;
				model.addAttribute("sum", sum);
				model.addAttribute("tax", tax);
				model.addAttribute("total", total);
				model.addAttribute("role", role);
				
			} else {
				model.addAttribute("tickets", tickets);
			}
		}
		return "view.html";
	}

	// editTicket() returns edit.html in response to GET request to URL /edit/{id}, with corresponding Ticket object added to model.
	@GetMapping("/edit/{id}")
	public String editTicket(Model model, @PathVariable int id) {

		Ticket ticket = halRepo.getTicketById(id);

		model.addAttribute("ticket", ticket);

		return "edit.html";
	}

	// proccessEdit() receives Ticket object from GET request to URL /proccessEdit, updates corresponding ticket in database, and redirects to /view.
	@GetMapping("/proccessEdit")
	public String proccessEdit(@ModelAttribute Ticket ticket) {

		halRepo.editTicket(ticket);
		// this redirects page to view
		return "redirect:/view";
		
	}

	// deleteTicket() receives ID of ticket to be deleted as path variable in GET request to URL /delete/{id}, 
	// deletes corresponding ticket from database, and redirects to /view.
	@GetMapping("/delete/{id}")
	public String deleteTicket(@PathVariable int id) {

		halRepo.deleteTicketById(id);

		return "redirect:/view";
	}

	// goStats() returns stats.html in response to GET request to URL /stats, with necessary model attributes.
	@GetMapping("/stats")
	public String goStats(Model model) {
		model.addAttribute("count", halRepo.countTickets(halRepo.getTickets()));
		model.addAttribute("sum", halRepo.sumOfTickets(halRepo.getTickets()));
		model.addAttribute("under18", halRepo.total18AndUnder(halRepo.getTickets()));
		model.addAttribute("over18", halRepo.totalOver18(halRepo.getTickets()));
		model.addAttribute("withPets", halRepo.totalPets(halRepo.getTickets()));

		return "stats.html";
	}

	// endSession() receives HttpSession object, invalidates it, and redirects to root URL /.
	@GetMapping("/logout")
	public String endSession(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}