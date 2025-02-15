package com.piplica.reddit_api.service;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.piplica.reddit_api.dto.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {

    public ArrayList<Post> scrapePage(Page page, int numPosts) {
        ArrayList<Post> posts = new ArrayList<>();
        var numPages = numPosts / 25;

        for (int i = 0; i < numPages; i++) {
            var listings = page.locator("[data-context='listing']");
            posts.addAll(this.scrapePostData(listings));

            // Navigate to next page for posts
            var nextPage = page.locator(".next-button");
            var nextPageLink = nextPage.locator(":scope a[rel='nofollow next']").getAttribute("href");
            page.navigate(nextPageLink);
        }

        return posts;
    }

    private ArrayList<Post> scrapePostData(Locator listings) {
        ArrayList<Post> posts = new ArrayList<>();

        for (Locator listing : listings.all()) {
            if (listing.locator(":scope .sponsored-indicator").isVisible()) {
                System.out.println("encountered an ad...");
                continue;
            }

            // Extract all data attributes from listing attribute
            var id = cleanId(listing.getAttribute("id"));
            var url = listing.getAttribute("data-url");
            var permalinkUrl = listing.getAttribute("data-permalink");
            var author = listing.getAttribute("data-author");
            var dataScore = listing.getAttribute("data-score");
            var dataCommentsCount = listing.getAttribute("data-comments-count");
            var subreddit = listing.getAttribute("data-subreddit-prefixed");
            // Extract all child data attributes of listing
            var titleLocator = listing
                    .locator(":scope [data-event-action='title']");
            var rankLocator = listing.locator(":scope .rank");

            if (titleLocator.count() == 0 || rankLocator.count() == 0) continue;

            // Default integer values indicating error
            int rank = -1;
            int score = -1;
            int commentsCount = -1;

            try {
                rank = Integer.parseInt(rankLocator.textContent());
                score = Integer.parseInt(dataScore);
                commentsCount = Integer.parseInt(dataCommentsCount);
            } catch (NumberFormatException e) {
                System.out.println("Number format exception error when trying to parse string to int: " + e);
            }

            posts.add(new Post(id, titleLocator.textContent(), rank, url, permalinkUrl, author, score, commentsCount, subreddit));
        }

        return posts;
    }

    private String cleanId(String id) {
        var arr = id.split("_");
        return arr[arr.length - 1];
    }
}
