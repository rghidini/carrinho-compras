package br.com.sompo.config;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Configuration;

/**
 * This allows to deploy upgraded JPA and Hibernate versions to Websphere 8.5.5.x servers.
 * Has been used successfully with JPA 2.1 and Hibernate 4.3.11
 *
 * Mostly a copy of <a href="https://hibernate.atlassian.net/browse/JPA-4">https://hibernate.atlassian.net/browse/JPA-4</a>
 * Changes made to deploy in a Spring environment.
 *
 * To use: add a @DependsOn("hibernatePersistenceProviderResolver") annotation on a DatabaseConfig class
 */
@Configuration
public class HibernatePersistenceProviderResolver implements PersistenceProviderResolver {
	
	//TODO Classe de configuracao do WebSphere para conexao com o banco de dados
	
    private volatile PersistenceProvider persistenceProvider = new HibernatePersistenceProvider();
 
    @Override
    public List<PersistenceProvider> getPersistenceProviders() {
        return Collections.singletonList(persistenceProvider);
    }
 
    @Override
    public void clearCachedProviders() {
        persistenceProvider = new HibernatePersistenceProvider();
    }
 
    @PostConstruct
    public void register() {
        PersistenceProviderResolverHolder.setPersistenceProviderResolver(new HibernatePersistenceProviderResolver());
    }
}