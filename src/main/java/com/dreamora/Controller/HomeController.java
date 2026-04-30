package com.dreamora.Controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redirect to admin dashboard by default
        return "admin-dashboard"; // This will load admin.html from templates
    }
}
