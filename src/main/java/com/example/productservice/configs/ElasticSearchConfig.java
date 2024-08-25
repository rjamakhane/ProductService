package com.example.productservice.configs;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.productservice.repositories.elasticsearch")
@ComponentScan(basePackages = { "com.example.productservice.services" })
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http") // Adjust host and port as needed
        );
        return new RestHighLevelClient(builder);
    }
}
