package com.piplica.reddit_api.rest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.piplica.reddit_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/all", produces = "application/json")
public class AllController {
    private final PostService postService;
    private final Page page;

    @Autowired
    AllController(Browser browser, PostService postService) {
        BrowserContext browserCtx = browser.newContext();
        this.postService = postService;
        this.page = browserCtx.newPage();
    }

    @GetMapping()
    ResponseEntity<?> defaultRoute(@RequestParam(required = false, defaultValue = "25") int numPosts) {
        final Set<Integer> validPostAmounts = new HashSet<>(Arrays.asList(25, 50, 75, 100));

        if (!validPostAmounts.contains(numPosts)) {
            // 400 Bad Request - Invalid number of posts requested;
            HashMap<String, String> response = new HashMap<>();
            response.put("error", "Invalid post amount. Requested " + numPosts + ", expected " + validPostAmounts);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        page.navigate("https://old.reddit.com/r/all");

        return new ResponseEntity<>(postService.scrapePage(this.page, numPosts), HttpStatus.OK);
    }
}
