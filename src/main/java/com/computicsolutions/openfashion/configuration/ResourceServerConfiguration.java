package com.computicsolutions.openfashion.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Resource Server Configuration
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String PRODUCT_ENDPOINT = "/api/v1/products/**";
    private static final String FILE_STORAGE_ENDPOINT = "/api/v1/fileStorage/**";
    private final String RESOURCE_ID;

    @Autowired
    public ResourceServerConfiguration(@Value("${oauth.resource-id}") String resourceId) {
        this.RESOURCE_ID = resourceId;
    }

    /**
     * Configure a resource id for resource server APIs
     *
     * @param resources resource
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    /**
     * This method configure the web security of the resource server
     *
     * @param http http
     * @throws Exception exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, PRODUCT_ENDPOINT).access("hasAuthority('ADMIN')")
                .antMatchers(HttpMethod.PUT, PRODUCT_ENDPOINT).access("hasAuthority('ADMIN')")
                .antMatchers(HttpMethod.DELETE, PRODUCT_ENDPOINT).access("hasAuthority('ADMIN')")
                .antMatchers(HttpMethod.GET, PRODUCT_ENDPOINT).access("hasAnyAuthority('ADMIN', 'USER')")
                .antMatchers(HttpMethod.POST, FILE_STORAGE_ENDPOINT).access("hasAuthority('ADMIN')")
                .antMatchers(HttpMethod.PUT, FILE_STORAGE_ENDPOINT).access("hasAuthority('ADMIN')")
                .antMatchers(HttpMethod.DELETE, FILE_STORAGE_ENDPOINT).access("hasAuthority('ADMIN')")
                .antMatchers(HttpMethod.GET, FILE_STORAGE_ENDPOINT).access("hasAnyAuthority('ADMIN', 'USER')")
                .anyRequest().authenticated().and().cors().and()
                .csrf().disable();
    }
}
