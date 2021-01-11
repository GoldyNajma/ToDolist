package com.example.to_dolist.api_responses;

import com.example.to_dolist.data.model.User;

public class LoginResponse {
    public String token;
    public String token_type;
    public int expires_in;
    public User user;
}
