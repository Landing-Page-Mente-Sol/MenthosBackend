package com.api.mentosbackend.cucumber;

import com.api.mentosbackend.MentosBackendApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = MentosBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MentosBackendApplication.class, loader = SpringBootContextLoader.class)
public class CucumberSpringConfiguration {
}

