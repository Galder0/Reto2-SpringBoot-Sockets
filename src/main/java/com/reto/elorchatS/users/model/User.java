package com.reto.elorchatS.users.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.roles.model.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String surnames;

    @Column
    private String DNI;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String direction;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "fct_dual")
    private Boolean fctDual;

    @Column(nullable = false)
    private String password;
   
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "chat_user",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id")
    )
    // @JsonBackReference
    private List<Chat> chats;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    
    private List<Role> roles;
    
    public User() {
    	
    }
    public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
    
//  @Column
//  private String rememberToken;
  

	public User(Integer id, String name, String surnames, String dNI, String email, String direction,
			String phoneNumber, Boolean fctDual, String password, List<Chat> chats) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		DNI = dNI;
		this.email = email;
		this.direction = direction;
		this.phoneNumber = phoneNumber;
		this.fctDual = fctDual;
		this.password = password;
		this.chats = chats;
	}
	
	public User(String name, String surnames, String dNI, String email, String direction,
			String phoneNumber, Boolean fctDual, String password) {
		super();
		this.name = name;
		this.surnames = surnames;
		this.DNI = dNI;
		this.email = email;
		this.direction = direction;
		this.phoneNumber = phoneNumber;
		this.fctDual = fctDual;
		this.password = password;
	}

	public User(Integer id, String name, String surnames, String dNI, String email, String direction,
			String phoneNumber, Boolean fctDual, String password, List<Chat> chats, List<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		DNI = dNI;
		this.email = email;
		this.direction = direction;
		this.phoneNumber = phoneNumber;
		this.fctDual = fctDual;
		this.password = password;
		this.chats = chats;
		this.roles = roles;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Integer getId() {
		return id;
	}

	public List<Chat> getChats() {
		return chats;
	}

	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getFctDual() {
		return fctDual;
	}

	public void setFctDual(Boolean fctDual) {
		this.fctDual = fctDual;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
	    return "User [id=" + id + ", name=" + name + ", surnames=" + surnames + ", DNI=" + DNI + ", email=" + email
	            + ", direction=" + direction + ", phoneNumber=" + phoneNumber + ", fctDual=" + fctDual
	            + ", password=" + password + ", chats=" + getChatIds() + ", roles=" + getRoleNames() + "]";
	}
	
	private String getChatIds() {
	    if (chats != null) {
	        return chats.stream().map(chat -> String.valueOf(chat.getId())).collect(Collectors.toList()).toString();
	    }
	    return "null";
	}

	private String getRoleNames() {
	    if (roles != null) {
	        return roles.stream().map(Role::getName).collect(Collectors.toList()).toString();
	    }
	    return "null";
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// los permisos o autoridades que tiene. de momento nada. Aun sin roles
		return null;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		// esta funcion tiene que devolver el campo que hace de username. En este caso el email
		return email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// si se hubiese expirado
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// si no esta bloqueada
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// si las credenciales no han sido expiradas
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// si la cuenta esta activada. Si se tiene en BBDD una columna enabled, usariamos dicha columna
		return true;
	}
}
