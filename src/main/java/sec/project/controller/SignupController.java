package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.domain.Account;
import sec.project.repository.SignupRepository;
import sec.project.repository.AccountRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm( @RequestParam String name, @RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String phone, @RequestParam String address, RedirectAttributes redirectAttributes) {
        Account user = accountRepository.findByUsername(username); 
        
        if ( user == null ) {
            user = new Account();
            user.setUsername(username);
            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode(password));    
        }
    
        Signup signup = new Signup();
        signup.setName(name);
        signup.setEmail(email);
        signup.setPhone(phone);
        signup.setAddress(address);
        signup.setAccount(user);
        
        user.setSignup(signup);
        
        signupRepository.save(signup);
        
        redirectAttributes.addAttribute("name", name);
        redirectAttributes.addAttribute("username", username);
        redirectAttributes.addAttribute("email", email);
        redirectAttributes.addAttribute("phone", phone);
        redirectAttributes.addAttribute("address", address);
        
        return "redirect:/done";
    }
    
    @RequestMapping("/done")
    public String signupDone(@RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String address) {
        return "done";
    }
}
