
//This is Ticket bean used to retrieve ticket information. 
package ca.sheridancollege.imranfi.Beans;

import java.util.Arrays;

public class Ticket {
	private int id;
	private String name;
	private int age;
	private String gender;
	private String status;
	private String[] statuses = { "Student", "Not a Student"};
	private String[] genders = { "male", "female" };
	private String[] pets = { "yes", "no" };
	private String pet;
	private double price;
	private double[] prices = { 15.00, 20.50, 30.00 };

	public Ticket() {
		super();
	}
	

	public Ticket(int id, String name, int age, String gender, String status, String[] statuses, String[] genders,
			String[] pets, String pet, double price, double[] prices) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.status = status;
		this.statuses = statuses;
		this.genders = genders;
		this.pets = pets;
		this.pet = pet;
		this.price = price;
		this.prices = prices;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
		
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getStatuses() {
		return statuses;
	}

	public void setStatuses(String[] statuses) {
		this.statuses = statuses;
	}

	public String[] getGenders() {
		return genders;
	}

	public void setGenders(String[] genders) {
		this.genders = genders;
	}

	public String[] getPets() {
		return pets;
	}

	public void setPets(String[] pets) {
		this.pets = pets;
	}

	public String getPet() {
		return pet;
	}

	public void setPet(String pet) {
		this.pet = pet;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double[] getPrices() {
		return prices;
	}

	public void setPrices(double[] prices) {
		this.prices = prices;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", status=" + status
				+ ", statuses=" + Arrays.toString(statuses) + ", genders=" + Arrays.toString(genders) + ", pets="
				+ Arrays.toString(pets) + ", pet=" + pet + ", price=" + price + ", prices=" + Arrays.toString(prices)
				+ "]";
	}
	
	

}
