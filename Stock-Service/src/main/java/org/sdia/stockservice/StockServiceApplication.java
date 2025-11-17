package org.sdia.stockservice;

import org.sdia.stockservice.entities.StockMarket;
import org.sdia.stockservice.repository.StockMarketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class StockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(StockMarketRepository stockRepo) {
		return args -> {
			stockRepo.save(StockMarket.builder()
					.date(LocalDateTime.now().minusDays(3))
					.openValue(120.5)
					.closeValue(125.7)
					.volume(5000L)
					.companyId(1L)
					.build());

			stockRepo.save(StockMarket.builder()
					.date(LocalDateTime.now().minusDays(2))
					.openValue(126.0)
					.closeValue(122.3)
					.volume(4300L)
					.companyId(1L)
					.build());

			stockRepo.save(StockMarket.builder()
					.date(LocalDateTime.now().minusDays(1))
					.openValue(122.3)
					.closeValue(130.4)
					.volume(6100L)
					.companyId(1L)
					.build());

			System.out.println("Sample StockMarket data inserted successfully!");
		};
	}
}
