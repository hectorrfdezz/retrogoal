package com.retrogoal.retrogoal.repository;

import com.retrogoal.retrogoal.model.RecentSearch;
import com.retrogoal.retrogoal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {
    List<RecentSearch> findTop3ByUserOrderBySearchedAtDesc(User user);
    List<RecentSearch> findByUserOrderBySearchedAtDesc(User user);
    Optional<RecentSearch> findByUserAndQueryIgnoreCase(User user, String query);
}
