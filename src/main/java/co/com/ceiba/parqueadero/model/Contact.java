package co.com.ceiba.parqueadero.model;

public class Contact {
	
	 Long id;
	 String firstName;
	 String lastName;
	 String phoneNumber;
	 String email;
	 
	 public Contact() {}

	public Contact(Long id, String firstName, String lastName, String phoneNumber, String email) {
		 super();
		 this.id = id;
		 this.firstName = firstName;
		 this.lastName = lastName;
		 this.phoneNumber = phoneNumber;
		 this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	 
}
