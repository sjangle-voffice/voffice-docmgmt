package com.voffice.rearch.ms.docmgmt.controller.uploaddocument;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * This MultipartResolverConfiguration is used to upload the documents.
 * @author Sagar Jangle.
 * @version 1.0
 * @since 1st Feb 2022
 **/
@Configuration
public class MultipartResolverConfiguration {

    @Bean
    public CommonsMultipartResolver multiPartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        return resolver;
    }
}