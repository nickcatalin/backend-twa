package com.example.backend.controllers.admin;

import com.example.backend.dtos.DashboardStatsDto;
import com.example.backend.dtos.RatingDistributionDto;
import com.example.backend.services.LinkService;
import com.example.backend.services.RatingService;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/dashboard")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminDashboardController {

    private final UserService userService;
    private final LinkService linkService;
    private final RatingService ratingService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getDashboardStats(@RequestParam(defaultValue = "30") int days) {
        // Get the date range
        LocalDateTime startDate = LocalDateTime.now().minus(days, ChronoUnit.DAYS);
        
        // Get total counts
        long totalUsers = userService.getTotalUsers();
        long totalLinks = linkService.getTotalLinks();
        long totalRatings = ratingService.getTotalRatings();
        
        // Get new users count in the time period
        long newUsers = userService.getNewUsersCount(startDate);
        
        // Create and return the stats DTO
        DashboardStatsDto stats = DashboardStatsDto.builder()
                .totalUsers(totalUsers)
                .totalLinks(totalLinks)
                .totalRatings(totalRatings)
                .newUsers(newUsers)
                .build();
        
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/user-growth")
    public ResponseEntity<Map<String, Long>> getUserGrowth(@RequestParam(defaultValue = "30") int days) {
        LocalDateTime startDate = LocalDateTime.now().minus(days, ChronoUnit.DAYS);
        Map<String, Long> userGrowth = userService.getUserGrowthByDay(startDate);
        return ResponseEntity.ok(userGrowth);
    }
    
    @GetMapping("/links-by-domain")
    public ResponseEntity<Map<String, Long>> getLinksByDomain() {
        Map<String, Long> linksByDomain = linkService.getLinkCountByDomain();
        return ResponseEntity.ok(linksByDomain);
    }
    
    @GetMapping("/rating-distribution")
    public ResponseEntity<List<RatingDistributionDto>> getRatingDistribution() {
        List<RatingDistributionDto> distribution = ratingService.getRatingDistribution();
        return ResponseEntity.ok(distribution);
    }
    
    @GetMapping("/role-distribution")
    public ResponseEntity<Map<String, Long>> getRoleDistribution() {
        Map<String, Long> distribution = userService.getRoleDistribution();
        return ResponseEntity.ok(distribution);
    }
    
    @GetMapping("/top-commented-links")
    public ResponseEntity<Map<String, Long>> getTopCommentedLinks(@RequestParam(defaultValue = "5") int limit) {
        Map<String, Long> topLinks = linkService.getTopCommentedLinks(limit);
        return ResponseEntity.ok(topLinks);
    }
} 