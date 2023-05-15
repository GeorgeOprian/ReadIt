//package com.dis.readit.security;
//
//import com.google.firebase.auth.UserRecord;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
//public class User implements Authentication {
//
//	private final UserRecord userRecord;
//
//	public User(UserRecord userRecord) {
//		this.userRecord = userRecord;
//	}
//
//	@Override public Collection<? extends GrantedAuthority> getAuthorities() {
//		return null;
//	}
//
//	@Override public Object getCredentials() {
//		return null;
//	}
//
//	@Override public Object getDetails() {
//		return null;
//	}
//
//	@Override public Object getPrincipal() {
//		return null;
//	}
//
//	@Override public boolean isAuthenticated() {
//		return false;
//	}
//
//	@Override public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//
//	}
//
//	@Override public String getName() {
//		return null;
//	}
//}
