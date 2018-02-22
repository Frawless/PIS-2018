package cz.vut.fit.pis.bakery.bakery.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class GeneralConfigurations {



    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }
}
