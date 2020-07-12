package br.com.codenation.configurations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String TITLE = "Central de Erros - Codenation";
	public static final String DESCRIPTION = "Serviços para cadastrar logs de erro.";
	public static final String VERSION = "1.0";
	public static final String LICENSE = "Apache 2.0";
	public static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";
	public static final String URL = "https://github.com/Squad2-codenation/CentralDeErros";
	public static final String CONTACT_NAME = "Squad 2 - Codenation";

	public static final String BASE_PACKAGE = "br.com.codenation.controllers";

	@Value("${security.oauth2.client.client-id}")
	private String clientId;

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	@Value("${security.oauth2.client.auth-link}")
	private String authLink;

	@Value("${security.oauth2.client.scope}")
	private String scope;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
				.paths(PathSelectors.any())
				.build().apiInfo(metaInfo())
				.securitySchemes(Collections.singletonList(securitySchema()))
				.securityContexts(Arrays.asList(securityContext()));
	}

	private OAuth securitySchema() {
		List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
		authorizationScopeList.add(new AuthorizationScope(scope, "all"));

		List<GrantType> grantTypes = new ArrayList<>();
		grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(authLink));

		return new OAuth("Oauth2 Password", authorizationScopeList, grantTypes);
	}

	@Bean
	public SecurityConfiguration securityInfo() {
		return SecurityConfigurationBuilder.builder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.appName(TITLE)
				.build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.any())
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = new AuthorizationScope(scope, "all");
		return Collections.singletonList(new SecurityReference("Oauth2 Password", authorizationScopes));
	}

	@SuppressWarnings("rawtypes")
	private ApiInfo metaInfo() {
		return new ApiInfoBuilder().title(TITLE)
				.description(DESCRIPTION)
				.version(VERSION)
				.contact(new Contact(CONTACT_NAME, URL, ""))
				.license(LICENSE)
				.licenseUrl(LICENSE_URL)
				.build();
	}

}
