package com.piplica.reddit_api.service;

import com.microsoft.playwright.Locator;
import com.piplica.reddit_api.dto.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {

    public ArrayList<Post> scrapePostData(Locator listings) {
        ArrayList<Post> posts = new ArrayList<>();

        for (Locator listing : listings.all()) {
            if (listing.locator(":scope .sponsored-indicator").isVisible()) {
                System.out.println("encountered an ad...");
                continue;
            }

            // Extract all data attributes from current tag and child nodes
            var id = cleanId(listing.getAttribute("id"));
            var titleLocator = listing
                    .locator(":scope [data-event-action='title']");
            var rankLocator = listing.locator(":scope .rank");
            var link = listing.getAttribute("data-url");

            if (titleLocator.count() == 0 || rankLocator.count() == 0) continue;

            int rank = -1;

            try {
                rank = Integer.parseInt(rankLocator.textContent());
            } catch (NumberFormatException e) {
                System.out.println("Number format exception error when trying to parse string to int: " + e);
            }

            posts.add(new Post(id, titleLocator.textContent(), rank, link));
        }

        return posts;
    }

    private String cleanId(String id) {
        var arr = id.split("_");
        return arr[arr.length - 1];
    }
}
