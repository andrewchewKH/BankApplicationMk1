package net.javaguides.banking.controller;

import jakarta.validation.Valid;
import net.javaguides.banking.dto.CustomerDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
        @GetMapping("/index")
        public String display()
        {
            return "index";
        }

         @PostMapping("/index")
         public String loginAdministration(@Valid @ModelAttribute("userName") String userName,
                                @Valid @ModelAttribute("password") String password,
                                BindingResult result,
                                Model model){
        if(result.hasErrors()){
            model.addAttribute("userName", userName);
            model.addAttribute("password", password);
            return "index";
        }
             System.out.println("User Name: "+userName);
             System.out.println("Password: "+password);
        if (userName.equals("Admin") && password.equals("admin123")) {
            System.out.println("mainMenu: ");
            return "redirect:/index/mainMenu";

        }
        else
        return "index";
    }

    @GetMapping("/index/mainMenu")
    public String displayMainMenu()
    {
        return "admin_menu";
    }
}
