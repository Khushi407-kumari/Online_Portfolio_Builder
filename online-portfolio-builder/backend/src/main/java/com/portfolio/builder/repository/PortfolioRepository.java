package com.portfolio.builder.repository;

import com.portfolio.builder.model.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<UserPortfolio, Long> {
}
