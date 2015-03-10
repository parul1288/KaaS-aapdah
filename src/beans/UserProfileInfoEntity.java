package beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="userProfile")
public class UserProfileInfoEntity {
	//Primary key for the table. This will be auto-generated 
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	String id;

	String firstName;
	String lastName;

	String email;
	String password;

	String city;
	String state;

	String emailNotification;
	String popUpNotification;

	public String getEmailNotification() {
		return emailNotification;
	}
	public void setEmailNotification(String emailNotification) {
		this.emailNotification = emailNotification;
	}
	
	public String getPopUpNotification() {
		return popUpNotification;
	}
	public void setPopUpNotification(String popUpNotification) {
		this.popUpNotification = popUpNotification;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}	

}
