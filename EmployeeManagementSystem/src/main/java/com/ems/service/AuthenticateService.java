package com.ems.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


public interface AuthenticateService {

    public String authenticate(String user,String email,String password,Model model,RedirectAttributes redirectAttributes);

}
