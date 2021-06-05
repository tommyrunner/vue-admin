package com.tommy.vueadmin.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select() // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                .apis(RequestHandlerSelectors.basePackage("com.tommy.vueadmin"))
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("vue-admin-接口文档") //设置文档的标题
                .description("文档描述") // 设置文档的描述
                .version("1.0.0") // 设置文档的版本信息-> 1.0.0 Version information
                .termsOfServiceUrl("http://www.tmm-tommy.cn:8888/tommy") // 设置文档的License信息->1.3 License information
                .build();
    }
}
