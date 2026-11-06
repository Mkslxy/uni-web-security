package chnu.edu.websecurity26.controller;

/*
  @author   oleksii
  @project  web-security26
  @class    AdminController
  @version  1.0.0 
  @since    07.10.2026
*/

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, admin!";
    }
}
