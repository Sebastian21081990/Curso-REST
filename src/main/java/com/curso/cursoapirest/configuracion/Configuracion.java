package com.curso.cursoapirest.configuracion;

import com.curso.cursoapirest.upload.StorageService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Configuracion {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public WebMvcConfigurer corsConfiguration(){

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**");
                registry.addMapping("/producto/**")
                        .allowedOrigins("http://localhost:9001")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };

    }

    @Bean
    public CommandLineRunner init(StorageService storageService){
        return args -> {
          storageService.deleteAll();
          storageService.init();
        };
    }

}
