package com.luongtx.oes.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ResourcePathConfig {
    @Value("${upload.path}")
    String uploadPath;

    @Value("${upload.default-profile-image}")
    String defaultProfileImage;

    @Value("${upload.default-cert-image}")
    String defaultCertImage;
}
