package org.smurve.hsr2014.inheritance;

import configuration.SpringJpaConfiguration;
import net.sf.ehcache.config.*;
import org.smurve.hsr2014.utils.db.DatabaseConnector;
import org.smurve.hsr2014.utils.db.hsql.HSqlConnector;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(SpringJpaConfiguration.class)
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = {"org.smurve.hsr2014.repo"})
@ComponentScan(basePackages = {
        "org.smurve.hsr2014.repo",
        "org.smurve.hsr2014.service"})
@EnableCaching
public class DatabaseTestContext {

    @Bean
    public DatabaseConnector databaseConnector() {
        return new HSqlConnector(); //MySqlConnector();
    }

    @Bean
    public CacheManager cacheManager () {
        net.sf.ehcache.config.Configuration ehConfig = new net.sf.ehcache.config.Configuration();

        CacheConfiguration primary = createCacheConfiguration("primary");
        ehConfig.addCache(primary);
        CacheConfiguration projects = createCacheConfiguration("projects");
        ehConfig.addCache(projects);

        net.sf.ehcache.CacheManager cacheManager = new net.sf.ehcache.CacheManager(ehConfig);
        return new EhCacheCacheManager(cacheManager);
    }


    private CacheConfiguration createCacheConfiguration( String name ) {
        CacheConfiguration config = new CacheConfiguration();
        config.setName(name);
        config.setMemoryStoreEvictionPolicy("LRU");
        config.setMaxEntriesLocalHeap(1000);
        config.setTimeToLiveSeconds(100);
        return config;
    }

}
