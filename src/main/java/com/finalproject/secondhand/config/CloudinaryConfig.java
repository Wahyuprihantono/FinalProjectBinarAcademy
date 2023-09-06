package com.finalproject.secondhand.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.finalproject.secondhand.service.image.CloudinaryStorageService;
import com.finalproject.secondhand.service.image.CloudinaryStorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinaryAccount() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dseuf434q",
                "api_key", "649664353883695",
                "api_secret", "FNkhl4FUComE8ZpW1dWW7fyGRqw"
        ));
    }

    @Bean
    public CloudinaryStorageService cloudinaryStorageService() {
        return new CloudinaryStorageServiceImpl(cloudinaryAccount());
    }

}
