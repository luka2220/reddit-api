package com.piplica.reddit_api.rest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final BrowserContext browserCtx;
    private final String redditURL = "https://old.reddit.com/";

    @Autowired
    MainController(Browser browser) {
        this.browserCtx = browser.newContext();
    }

    @GetMapping("/popular")
    String baseURL() {
        var page = browserCtx.newPage();
        page.navigate(redditURL);
        var listings = page.locator("[data-context='listing']").all();
        StringBuilder response = new StringBuilder();

        for (Locator listing : listings) {
            if (listing.locator(":scope .sponsored-indicator").isVisible()) {
                System.out.println("encountered an ad...");
                continue;
            }

            var title = listing
                    .locator(":scope [data-event-action='title']")
                    .textContent();
            var rank = listing.locator(":scope .rank").textContent();

            response.append(rank)
                    .append(" - ")
                    .append(title)
                    .append("<br>");
        }

        return response.toString();
    }
}
