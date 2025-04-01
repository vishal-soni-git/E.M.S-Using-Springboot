package com.ems.service;

import org.springframework.ui.Model;


public interface AuthenticateService {

    public String authenticate(String user,String email,String password,Model model);

}
