package com.epam.courses.paycom.rest_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home MVC controller.
 */
@RestController
public class VersionController {

    private final static String VERSION = "0.0.1";

    /**
     * Version.
     *
     * @return app version
     */
    @GetMapping(value = "/version")
    public String version() {
        return VERSION;
    }
}