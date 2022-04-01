package com.hoaxify.ws.user;

import java.util.Collection;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.shared.Views;

import lombok.Data;

@Data
@Entity
public class User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5542903059402132457L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "{hoaxify.constraint.username.NotNull.message}")
	@Size(min=4, max=255)
	//@Column(unique=true)
	@UniqueUsername
	@JsonView(Views.Base.class)
	private String username;
	
	@NotNull
	@Size(min=4, max=255)
	@JsonView(Views.Base.class)
	private String displayName;
	
	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constraint.password.Pattern.message}")
	@Size(min=8, max=255)
	//@JsonIgnore
	@JsonView(Views.Sensitive.class)
	private String password;
	
	@JsonView(Views.Base.class)
	private String image;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	

}
