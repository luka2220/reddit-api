package com.piplica.reddit_api.rest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.piplica.reddit_api.dto.Post;
import com.piplica.reddit_api.service.CountryCodeService;
import com.piplica.reddit_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
        System.out.println("Connected to reddit!");
    }

    @GetMapping()
    ResponseEntity<?> baseURL(@RequestParam(required = false) String countryCode, @RequestParam(required = false, defaultValue = "25") int numPosts) {
        final Set<Integer> validPostAmounts = new HashSet<>(Arrays.asList(25, 50, 75, 100));
        var countryCodeSearch = (countryCode == null) ? "GLOBAL" : countryCode;

        if (!countryCodeService.isValidCountryCode(countryCodeSearch)) {
            // 400 Bad Request invalid country code;
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "Invalid country code requests, or an unavailable country was requested: " + countryCodeSearch);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (!validPostAmounts.contains(numPosts)) {
            // 400 Bad Request invalid post's amount requested;
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("error", "Invalid post amount sent. Can only be the values: 25, 50, 75, 100");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        this.page.navigate("https://old.reddit.com/r/popular/?geo_filter=" + countryCodeSearch);
        return new ResponseEntity<>(scrapePage(numPosts), HttpStatus.OK);
    }

    @GetMapping("/test")
    ResponseEntity<Map<String, String>> testBrightData() {
        Map<String, String> response = new HashMap<>();
        this.page.navigate("https://old.reddit.com");
        var pageContent = this.page.content();

        System.out.println("Page content: " + pageContent);
        response.put("message", pageContent);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ArrayList<Post> scrapePage(int numPosts) {
        ArrayList<Post> posts = new ArrayList<>();
        var numPages = numPosts / 25;

        for (int i = 0; i < numPages; i++) {
            var listings = page.locator("[data-context='listing']");
            posts.addAll(postService.scrapePostData(listings));

            // Navigate to next page for posts
            var nextPage = page.locator(".next-button");
            var nextPageLink = nextPage.locator(":scope a[rel='nofollow next']").getAttribute("href");
            page.navigate(nextPageLink);
        }

        return posts;
    }
}
