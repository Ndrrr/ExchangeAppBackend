package com.exchange.app;

import com.exchange.app.domain.Currency;
import com.exchange.app.repository.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

@SpringBootApplication
public class ExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CurrencyRepository currencyRepository) {
        return args -> {
            if (currencyRepository.findAll().isEmpty()) {
                String fileName = "src/currencies.txt";
                String filePath = Paths.get("").toAbsolutePath() + File.separator + fileName;
                System.out.println(filePath);
                File file = new File(filePath);
                Scanner scanner = new Scanner(file).useDelimiter(",");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");
                    String currencyCode = parts[0].replaceAll("[^A-Za-z\\d]", "").trim();
                    String currencyName = parts[1].replaceAll("[^A-Za-z\\d]", " ").trim();
                    Currency currency = Currency
                            .builder()
                            .code(currencyCode)
                            .name(currencyName)
                            .build();
                    currencyRepository.save(currency);
                }
            }
        };
    }

}
