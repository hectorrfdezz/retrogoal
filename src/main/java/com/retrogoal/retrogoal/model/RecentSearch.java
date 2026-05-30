package com.retrogoal.retrogoal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Stores the last searches made by each logged-in user.
 */
@Entity
@Table(name = "recent_searches",
        indexes = {
                @Index(name = "idx_recent_search_user_date", columnList = "user_id,searched_at")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "search_query", nullable = false, length = 120)
    private String query;

    @Column(name = "searched_at", nullable = false)
    private LocalDateTime searchedAt;
}
