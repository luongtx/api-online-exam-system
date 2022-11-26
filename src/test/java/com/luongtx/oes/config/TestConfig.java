package com.luongtx.oes.config;

import com.luongtx.oes.repository.CatalogRepo;
import com.luongtx.oes.repository.ProfileRepo;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.security.JwtTokenUtil;
import com.luongtx.oes.service.CatalogService;
import com.luongtx.oes.service.ProfileService;
import com.luongtx.oes.service.impl.CatalogServiceImpl;
import com.luongtx.oes.service.impl.ProfileServiceImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy
public class TestConfig extends AspectJConfig {

    @MockBean
    CatalogRepo catalogRepo;

    @MockBean
    QuestionRepo questionRepo;

    @MockBean
    ProfileRepo profileRepo;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    ResourcePathConfig resourcePathConfig;

    @Bean
    CatalogService catalogService(final CatalogRepo catalogRepo, final QuestionRepo questionRepo) {
        CatalogService catalogService = CatalogServiceImpl.builder()
                .catalogRepo(catalogRepo)
                .questionRepo(questionRepo).build();
        return proxyObject(catalogService);
    }

    @Bean
    ProfileService profileService() {
        ProfileService profileService = new ProfileServiceImpl();
        return proxyObject(profileService);
    }

}