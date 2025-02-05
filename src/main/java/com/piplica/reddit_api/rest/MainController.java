package com.piplica.reddit_api.rest;

import com.microsoft.playwright.Browser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final Browser browser;

    @Autowired
    MainController(Browser browser) {
        this.browser = browser;
    }

    @GetMapping("/popular")
    String baseURL() {
        var page = browser.newPage();
        page.navigate("http://playwright.dev");
        System.out.println("Page title: " + page.title());

        return "Application started";
    }
}
