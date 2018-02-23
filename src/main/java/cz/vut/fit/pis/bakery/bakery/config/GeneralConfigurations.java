package cz.vut.fit.pis.bakery.bakery.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;

@Configuration
public class GeneralConfigurations {


//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }
}
