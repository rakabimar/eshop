package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private static final String HOME_PAGE_VIEW = "homePage"; // Constant for the home page view

    @GetMapping("")
    public String mainPage() {
        return HOME_PAGE_VIEW;
    }
}
