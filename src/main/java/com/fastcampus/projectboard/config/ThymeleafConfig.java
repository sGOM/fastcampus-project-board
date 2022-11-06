package com.fastcampus.projectboard.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

/**
 * Java Spring boot에서
 * Thymeleaf의 decoupling logic을 지원해 주지 않기때문에 직접 설정 추가
 * 원래 있던 defaultTemplateResolver에 setting만 추가
 * https://gist.github.com/djkeh/6e1d557ce8c466135b1541d342b1c25c
 * 해당 링크에 reference 또한 포함 됨
 *
 * decoupling이란?
 * html 파일에 thymeleaf 문법이 랜더링하기 전 상태인 filesystem에서는
 * 올바른 문법으로 받아 들여지기 않기 때문에 원하는 형태로 html을 확인할 수 없어
 * 개발에 불편함을 줄 수 있음 (디자이너, 개발자의 오프라인 확인 등에서)
 * 따라서 filesystem에서도 보여질 수 있도록 thymeleaf의 문법이 적용된 부분은
 * 따로 th.xml 파일로 빼낼 수 있게 하는 방법
 */
@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());

        return defaultTemplateResolver;
    }

    @RequiredArgsConstructor
    @Getter
    @ConstructorBinding
    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        /**
         * Use Thymeleaf 3 Decoupled Logic
         */
        private final boolean decoupledLogic;
    }
}

