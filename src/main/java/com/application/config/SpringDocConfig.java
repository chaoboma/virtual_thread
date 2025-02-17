//package com.application.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * spring doc配置
// */
//@Configuration
//public class SpringDocConfig {
//
//    @Value("${framework.swagger.title}")
//    private String swaggerTitle;
//
//    @Value("${framework.swagger.description}")
//    private String swaggerDescription;
//
//    @Value("${framework.swagger.contact.name}")
//    private String swaggerContactName;
//
//    @Value("${framework.swagger.contact.url}")
//    private String swaggerContactUrl;
//
//    @Value("${framework.swagger.contact.email}")
//    private String swaggerContactEmail;
//
//    @Value("${framework.swagger.version}")
//    private String swaggerVersion;
//
//    @Bean
//    public OpenAPI apiInfo() {
//        return new OpenAPI()
//                .info(new Info().title(swaggerTitle)
//                        .description(swaggerDescription)
//                        .version(swaggerVersion)
//                        .contact(new Contact()
//                                .name(swaggerContactName).email(swaggerContactEmail).url(swaggerContactUrl)));
//    }
//
//}
