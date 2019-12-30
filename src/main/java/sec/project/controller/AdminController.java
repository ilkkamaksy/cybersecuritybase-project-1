/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import sec.project.repository.SignupRepository;
import sec.project.repository.AccountRepository;
import sec.project.domain.Signup;
import sec.project.domain.Account;
/**
 *
 * @author ilkka
 */
@Controller
public class AdminController {
    
    @Autowired
    private SignupRepository signupRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @RequestMapping(value = "/app-admin", method = RequestMethod.GET)
    public String defaultMapping(Model model) {
        model.addAttribute("signups", signupRepository.findAll());       
        return "app-admin";
    }
    
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/app-admin/attendees/{itemId}", method = RequestMethod.DELETE)
    public String remove(@PathVariable Long itemId) {
        signupRepository.delete(itemId);
        return "redirect:/app-admin";
    }
    
    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/app-admin/attendees/{itemId}", method = RequestMethod.POST)
    public String editItem(@PathVariable Long itemId, @RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String address) {
        Signup item = signupRepository.findOne(itemId);
        if ( item != null ) {
            item.setName(name);
            item.setEmail(email);
            item.setPhone(phone);
            item.setAddress(address);
            signupRepository.save(item);
        }
        
        return "redirect:/app-admin";
    }
    
}
