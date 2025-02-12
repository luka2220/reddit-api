package com.piplica.reddit_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Post {
    String id;
    String title;
    int rank;
    String link;
}
