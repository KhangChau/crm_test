package helloservlet.entity;

public class UserEntity {
	private int id;
	private String email;
	private String password;
	private String fullname;
	private String avatar;
	private int role_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int id_role) {
		this.role_id = id_role;
	}
	
	public String getFirstName() {
//		String fullName = "John Erich Daws Black";
		int idx = this.fullname.lastIndexOf(' ');
		String firstname = "";
		if (idx == -1)
		    return this.fullname;
//		String lastname = this.fullname.substring(0, idx);
		firstname  = this.fullname.substring(idx + 1);
		
		return firstname;
	}
	public String getLastName() {
//		String fullName = "John Erich Daws Black";
		int idx = this.fullname.lastIndexOf(' ');
		String lastname = "";
		if (idx == -1)
		    return "";
		lastname = this.fullname.substring(0, idx);
//		String firstname  = this.fullname.substring(idx + 1);
		
		return lastname;
	}
	
}
