package com.piplica.reddit_api.rest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final BrowserContext browserCtx;
    private final String redditURL = "https://old.reddit.com/";
    private final Page page;

    @Autowired
    MainController(Browser browser) {
        this.browserCtx = browser.newContext();
        this.page = browserCtx.newPage();
    }

    @GetMapping("/popular")
    String baseURL() {
        page.navigate(redditURL);
        var listings = page.locator("[data-context='listing']").all();
        StringBuilder response = new StringBuilder();

        for (Locator listing : listings) {
            if (listing.locator(":scope .sponsored-indicator").isVisible()) {
                System.out.println("encountered an ad...");
                continue;
            }

            var titleLocator = listing
                    .locator(":scope [data-event-action='title']");
            var rankLocator = listing.locator(":scope .rank");
            var id = listing.getAttribute("id");

            if (titleLocator.count() == 0 || rankLocator.count() == 0) continue;


            response.append(rankLocator.textContent())
                    .append(" - ")
                    .append(id)
                    .append(" - ")
                    .append(titleLocator.textContent())
                    .append("<br>");
        }

        return response.toString();
    }

    // TODO: Properly parse out id from string thing_t3_i11iu23 the last word is the id...
    private String cleanId(String id) {
        var arr = id.split("_");
        System.out.println(arr);
        return "";
    }
}
