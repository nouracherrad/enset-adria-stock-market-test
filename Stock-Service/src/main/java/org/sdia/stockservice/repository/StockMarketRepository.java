package org.sdia.stockservice.repository;


import org.sdia.stockservice.entities.StockMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockMarketRepository extends JpaRepository<StockMarket, Long> {
    List<StockMarket> findByCompanyIdOrderByDateDesc(Long companyId);

    @Query("select s from StockMarket s where s.companyId = :companyId order by s.date desc")
    List<StockMarket> findLatestByCompanyId(Long companyId);
}
