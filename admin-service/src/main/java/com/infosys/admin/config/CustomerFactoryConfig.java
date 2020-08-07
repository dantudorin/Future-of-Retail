package com.infosys.admin.config;

import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.springframework.context.annotation.Bean;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerFactoryConfig {

    @Bean
    public AmazonRekognition getAmazonClient() {
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setConnectionTimeout(30000);
        clientConfig.setRequestTimeout(60000);
        clientConfig.setProtocol(Protocol.HTTPS);

        AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();

        return AmazonRekognitionClientBuilder
                .standard()
                .withClientConfiguration(clientConfig)
                .withCredentials(credentialsProvider)
                .withRegion("us-west-2")
                .build();
    }
}