package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm( @RequestParam String name, @RequestParam String email, @RequestParam String phone, @RequestParam String address, RedirectAttributes redirectAttributes) {
        signupRepository.save(new Signup(name, email, phone, address));
        
        redirectAttributes.addAttribute("name", name);
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
