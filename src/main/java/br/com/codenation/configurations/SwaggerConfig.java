package br.com.codenation.configurations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.models.auth.In;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String TITLE = "Central de Erros - Codenation";
	public static final String DESCRIPTION = "Serviços para cadastrar logs de erro.";
	public static final String VERSION = "1.0";
	public static final String TERMS = "Termos e Serviços 2020";
	public static final String URL = "COLOCAR O LINK DA APLICAÇÃO EM PRODUÇÃO";
	public static final String LICENSE = "DigiCert SHA2 Extended Validation Server CA";
	public static final String EMAIL = "gusttavo.funchal@gmail.com";
	public static final String LICENSE_URL = "https://www.digicert.com/";
	public static final String CONTACT_NAME = "Gusttavo H. Funchal Pimenta";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.codenation.controllers")).paths(PathSelectors.any())
				.build().apiInfo(metaInfo())
				.securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
				.securityContexts(Arrays.asList(securityContext()));
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("ADMIN", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Token Access", authorizationScopes));
	}

	@SuppressWarnings("rawtypes")
	private ApiInfo metaInfo() {
		return new ApiInfo(TITLE, DESCRIPTION, VERSION, TERMS, new Contact(CONTACT_NAME, URL, EMAIL), LICENSE,
				LICENSE_URL, new ArrayList<VendorExtension>());
	}

}
