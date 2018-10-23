package com.zypc.weekly.util;

import java.io.Serializable;

public class Token implements Serializable {
	
	private static final long serialVersionUID = -3882696279560499584L;
	//{"access_token":"bff2bbc115cbc24f9e4e477075cf45a8c78f5b1ae2485bd29a0fe5e99b9632da","token_type":"bearer","created_at":1532927936}
	private String access_token;
	private String token_type;
	private String created_at;
	private String username;
	private String group;
	private String dispname;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getDispname() {
		return dispname;
	}
	public void setDispname(String dispname) {
		this.dispname = dispname;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
