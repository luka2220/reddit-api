package com.piplica.reddit_api.rest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.piplica.reddit_api.service.CountryCodeService;
import com.piplica.reddit_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/popular", produces = "application/json")
public class PopularController {
    private final PostService postService;
    private final CountryCodeService countryCodeService;
    private final Page page;

    @Autowired
    PopularController(Browser browser, PostService postService, CountryCodeService countryCodeService) {
        BrowserContext browserCtx = browser.newContext();
        this.postService = postService;
        this.countryCodeService = countryCodeService;
        this.page = browserCtx.newPage();
        // this.page.navigate(redditBaseURL);
    }

    @GetMapping()
    ResponseEntity<?> baseURL(@RequestParam(required = false) String countryCode) {
        StringBuilder url = new StringBuilder("https://old.reddit.com/r/popular/?geo_filter=");
        var countryCodeSearch = (countryCode == null) ? "GLOBAL" : countryCode;

        if (countryCodeSearch.equals("GLOBAL")) {
            url.append(countryCodeSearch);
            page.navigate(url.toString());
            var listings = page.locator("[data-context='listing']");
            return new ResponseEntity<>(postService.scrapePostData(listings), HttpStatus.OK);
        }

        if (!countryCodeService.isValidCountryCode(countryCodeSearch)) {
            // 400 Bad Request invalid country code;
            System.out.println("Invalid country code: " + countryCodeSearch);
            HashMap<String, String> message = new HashMap<>();
            message.put("error", "Invalid country code requests, or an unavailable country was requested");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        url.append(countryCodeSearch);
        page.navigate(url.toString());
        var listings = page.locator("[data-context='listing']");
        return new ResponseEntity<>(postService.scrapePostData(listings), HttpStatus.OK);
    }
}
