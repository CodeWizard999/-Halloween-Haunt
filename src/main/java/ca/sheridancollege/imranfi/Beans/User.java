//This is User bean used to retrieve user's information. 

package ca.sheridancollege.imranfi.Beans;

public class User {

	private Long userId;
	private String userName;
	private String encryptedPassword;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public User(Long userId, String userName, String encryptedPassword) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.encryptedPassword = encryptedPassword;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", encryptedPassword=" + encryptedPassword + "]";
	}

}
