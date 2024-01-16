package com.reto.elorchatS.users.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.reto.elorchatS.chats.model.Chat;

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

    @Column
    private String phone_number;

    @Column
    private Boolean fct_dual;

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
			String phone_number, Boolean fct_dual, String password, List<Chat> chats) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		DNI = dNI;
		this.email = email;
		this.direction = direction;
		this.phone_number = phone_number;
		this.fct_dual = fct_dual;
		this.password = password;
		this.chats = chats;
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

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Boolean getFct_dual() {
		return fct_dual;
	}

	public void setFct_dual(Boolean fct_dual) {
		this.fct_dual = fct_dual;
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
				+ ", direction=" + direction + ", phone_number=" + phone_number + ", fct_dual=" + fct_dual
				+ ", password=" + password + ", chats=" + chats + "]";
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// los permisos o autoridades que tiene. de momento nada. Aun sin roles
		return null;
	}

	@Override
	public String getUsername() {
		// esta funcion tiene que devolver el campo que hace de username. En este caso el email
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// si se hubiese expirado
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// si no esta bloqueada
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// si las credenciales no han sido expiradas
		return true;
	}

	@Override
	public boolean isEnabled() {
		// si la cuenta esta activada. Si se tiene en BBDD una columna enabled, usariamos dicha columna
		return true;
	}
}
