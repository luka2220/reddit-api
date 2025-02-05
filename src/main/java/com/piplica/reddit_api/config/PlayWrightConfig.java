package com.piplica.reddit_api.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayWrightConfig {
    private Playwright playwright;


    @Bean
    Browser playWright() {
        playwright = Playwright.create();
        return playwright.chromium().launch();
    }

    @PreDestroy
    public void cleanup() {
        playwright.close();
    }
}
