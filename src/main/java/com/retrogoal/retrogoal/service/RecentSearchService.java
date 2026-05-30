package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.RecentSearch;
import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.repository.RecentSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Keeps the last three searches made by a user.
 */
@Service
@RequiredArgsConstructor
public class RecentSearchService {

    private final RecentSearchRepository recentSearchRepository;

    @Transactional
    public void registerSearch(User user, String query) {
        if (user == null || query == null || query.isBlank()) {
            return;
        }
        String cleaned = query.trim();
        if (cleaned.length() > 120) {
            cleaned = cleaned.substring(0, 120);
        }

        RecentSearch search = recentSearchRepository.findByUserAndQueryIgnoreCase(user, cleaned)
                .orElse(RecentSearch.builder()
                        .user(user)
                        .query(cleaned)
                        .build());
        search.setSearchedAt(LocalDateTime.now());
        recentSearchRepository.save(search);

        List<RecentSearch> allSearches = recentSearchRepository.findByUserOrderBySearchedAtDesc(user);
        for (int i = 3; i < allSearches.size(); i++) {
            recentSearchRepository.delete(allSearches.get(i));
        }
    }

    @Transactional(readOnly = true)
    public List<RecentSearch> findLastThree(User user) {
        if (user == null) {
            return List.of();
        }
        return recentSearchRepository.findTop3ByUserOrderBySearchedAtDesc(user);
    }
}
