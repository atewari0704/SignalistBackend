package com.signallist.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;


@Data
@Document(collection = "watchlists")
public class Watchlist {
    @Id
    private String id;

    private String userId; // The userId corresponds to the email

    private List<WatchListItem> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WatchListItem {
        private String symbol;
        private String company;
        private Instant addedAt;
    }
}
