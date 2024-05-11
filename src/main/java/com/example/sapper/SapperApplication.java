package com.example.sapper;

import com.example.sapper.entity.GameBoard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableWebSecurity
public class SapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SapperApplication.class, args);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    public static void show(GameBoard gameBoard){
        for(var row : gameBoard.getVisibleGameBoard()){
            for (var col : row){
                if(col.equals(" ")){
                    System.out.print("*" + '\t');
                }
                else {
                    System.out.print(col + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
