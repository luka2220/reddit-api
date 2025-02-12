package com.piplica.reddit_api.rest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.piplica.reddit_api.dto.Post;
import com.piplica.reddit_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/popular", produces = "application/json")
public class PopularController {
    private final BrowserContext browserCtx;
    private final PostService postService;
    private final String redditURL = "https://old.reddit.com/";
    private final Page page;

    @Autowired
    PopularController(Browser browser, PostService postService) {
        this.browserCtx = browser.newContext();
        this.postService = postService;
        this.page = browserCtx.newPage();
    }

    @GetMapping()
    ArrayList<Post> baseURL() {
        page.navigate(redditURL);
        var listings = page.locator("[data-context='listing']");
        return postService.scrapePostData(listings);
    }
}
