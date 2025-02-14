package com.piplica.reddit_api.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PlayWrightConfig {
    private Playwright playwright;
    @Value("${app.bright.data.username}")
    private String BRIGHT_DATA_USERNAME;
    @Value("${app.bright.data.password}")
    private String BRIGHT_DATA_PASSWORD;

    @Bean
    Browser playWright() {
        playwright = Playwright.create();
        String BRIGHT_DATA_PROXY = "wss://" + BRIGHT_DATA_USERNAME + ":" + BRIGHT_DATA_PASSWORD + "@brd.superproxy.io:9222";

        // NOTE: Bright data websocket browser
//        return playwright.chromium().connectOverCDP(BRIGHT_DATA_PROXY);

        return playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(List.of("--disable-images", "--disable-extensions", "--blink-settings=imagesEnabled=false")));
    }

    @PreDestroy
    void closePlaywrightConn() {
        playwright.close();
    }
}
