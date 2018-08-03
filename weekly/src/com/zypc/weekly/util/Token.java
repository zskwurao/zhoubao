package com.zypc.weekly.util;

import java.io.Serializable;

public class Token implements Serializable {
	
	//{"access_token":"bff2bbc115cbc24f9e4e477075cf45a8c78f5b1ae2485bd29a0fe5e99b9632da","token_type":"bearer","created_at":1532927936}
	private String access_token;
	private String token_type;
	private String created_at;
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
	
}
