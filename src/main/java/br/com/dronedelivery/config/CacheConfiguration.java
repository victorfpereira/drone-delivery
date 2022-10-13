package br.com.dronedelivery.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, br.com.dronedelivery.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, br.com.dronedelivery.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, br.com.dronedelivery.domain.User.class.getName());
            createCache(cm, br.com.dronedelivery.domain.Authority.class.getName());
            createCache(cm, br.com.dronedelivery.domain.User.class.getName() + ".authorities");
            createCache(cm, br.com.dronedelivery.domain.Empresa.class.getName());
            createCache(cm, br.com.dronedelivery.domain.Empresa.class.getName() + ".enderecos");
            createCache(cm, br.com.dronedelivery.domain.Empresa.class.getName() + ".telefones");
            createCache(cm, br.com.dronedelivery.domain.Empresa.class.getName() + ".pedidos");
            createCache(cm, br.com.dronedelivery.domain.Cliente.class.getName());
            createCache(cm, br.com.dronedelivery.domain.Cliente.class.getName() + ".enderecos");
            createCache(cm, br.com.dronedelivery.domain.Cliente.class.getName() + ".telefones");
            createCache(cm, br.com.dronedelivery.domain.Cliente.class.getName() + ".pedidos");
            createCache(cm, br.com.dronedelivery.domain.Drone.class.getName());
            createCache(cm, br.com.dronedelivery.domain.Drone.class.getName() + ".agendamentos");
            createCache(cm, br.com.dronedelivery.domain.Endereco.class.getName());
            createCache(cm, br.com.dronedelivery.domain.Endereco.class.getName() + ".pedidos");
            createCache(cm, br.com.dronedelivery.domain.Telefone.class.getName());
            createCache(cm, br.com.dronedelivery.domain.Pedido.class.getName());
            createCache(cm, br.com.dronedelivery.domain.Pedido.class.getName() + ".agendamentos");
            createCache(cm, br.com.dronedelivery.domain.Agendamento.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
