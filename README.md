# Halloween-Haunt-Ticket-Booking-System

Halloween Haunt Ticket Booking System is a comprehensive ticket booking system for an event named Halloween Haunt. The application is built on the Spring Boot framework and Thymeleaf template engine for the front-end, while Spring Security handles the authentication and authorization based on user roles. The database employed in the system is MySQL.

The system provides users with a user-friendly interface to book tickets for the event by providing necessary details such as age, gender, student status, pets preference, and ticket type selection. The system incorporates various security measures based on user authorization levels. Vendors (admin) can add, view, edit, and delete tickets while guests (users who register with guest authorization) can only purchase their own tickets. Additionally, vendors have access to records and reports on ticket sales that are not available to guests. The system also provides an interface for administrators to manage the status of each ticket.

# Installation

To install and run the Halloween Haunt Ticket Booking System on your local machine, follow these steps:

Clone the repository to your local machine.
Import the project into your preferred IDE (such as Eclipse or IntelliJ).
Configure the application.properties file with your MySQL database details.
Run the application by executing the main method in the HalloweenHauntApplication class.

# Usage
To use the Halloween Haunt Ticket Booking System, open your web browser and navigate to http://localhost:8080.

To login to the system as an admin, use the following credentials:

Username: vender
Password: 123

Once logged in as an admin, you can add, view, edit, and delete tickets. Guests can only purchase their own tickets. The system also provides an interface for administrators to manage the status of each ticket.

# Contributing
If you would like to contribute to the Halloween Haunt Ticket Booking System, please feel free to fork the repository and submit a pull request. We welcome all contributions, big or small!
