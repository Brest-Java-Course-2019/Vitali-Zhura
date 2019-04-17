package com.epam.courses.paycom.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home MVC controller.
 */
@Controller
public class HomeController {

    /**
     * Redirect to default page -> departments.
     *
     * @return redirect path
     */
    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        return "redirect:main-page";
    }


}