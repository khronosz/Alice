package cronos.alice.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(	name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Username cannot be blank!")
	@Size(max = 20, message = "Username cannot be longer than 20 characters!")
	@Column
	private String username;

	@NotBlank(message = "Job cannot be Blank!")
	@Size(max = 50, message = "Job cannot be longer than 50 characters!")
	@Column
	private String job;

	@NotBlank(message = "Department cannot be blank!")
	@Size(max = 50, message = "Department cannot be longer than 50 characters!")
	@Column
	private String department;

	@NotBlank(message = "Email cannot be blank!")
	@Size(max = 50, message = "Email cannot be longer than 50 characters!")
	@Email(regexp = ".+@.+\\..+", message = "Invalid Email format!")
	@Column
	private String email;

	@Size(max = 120)
	@Column(name = "passwd")
	@JsonIgnore
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@NotBlank(message = "City cannot be blank!")
	@Size(max = 20, message = "City cannot be longer than 20 characters!")
	@Column
	private String city;

	@NotBlank(message = "Level cannot be blank!")
	@Size(max = 20, message = "Level cannot be longer than 20 characters!")
	@Column(name = "user_level")
	private String level;

	@Size(max = 255, message = "Note cannot be longer than 255 characters!")
	@Column(name = "note")
	private String notes;

	@Column(name = "direct_manager")
	private Long directManagerId;

	@Column(name = "last_validation")
	private LocalDate lastValidation;

	@Column(name = "last_login")
	private LocalDate lastLogin;

	public User() {
	}

	public User(@NotBlank @Size(max = 20) String username,
				@NotBlank @Size(max = 50) String job,
				@NotBlank @Size(max = 50) String department,
				@NotBlank @Size(max = 50) @Email String email,
				@NotBlank @Size(max = 20) String city,
				@NotBlank @Size(max = 20) String level) {
		this.username = username;
		this.job = job;
		this.department = department;
		this.email = email;
		this.city = city;
		this.level = level;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		return id != null && id.equals(((User) o).getId());
	}

	@Override
	public int hashCode() {
		return 8;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getDirectManagerId() {
		return directManagerId;
	}

	public void setDirectManagerId(Long directManagerId) {
		this.directManagerId = directManagerId;
	}

	public LocalDate getLastValidation() {
		return lastValidation;
	}

	public void setLastValidation(LocalDate lastValidation) {
		this.lastValidation = lastValidation;
	}

	public LocalDate getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(final LocalDate lastLogin) {
		this.lastLogin = lastLogin;
	}
}
