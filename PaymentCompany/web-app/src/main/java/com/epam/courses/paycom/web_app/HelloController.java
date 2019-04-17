package com.epam.courses.paycom.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Hello MVC controller.
 */
@Controller
public class HelloController {

    @GetMapping(value = "/main-page")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "action") String name,
                        Model model) {
        model.addAttribute("name", name);
        return "main-page";
    }

    @GetMapping(value = "/search")
    public String hello1(@RequestParam(value = "name1", required = false, defaultValue = "action1") String name1,
                        Model model) {
        model.addAttribute("name1", name1);
        return "search";
    }

}