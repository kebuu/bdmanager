package com.cta.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import com.cta.BaseSpringTest;
import com.cta.exception.AppException;
import com.cta.model.Serie;
import com.cta.service.impl.DefaultModelService;

public class ModelServiceTest extends BaseSpringTest {

    @Autowired
    protected ModelService modelService;
    
    @Autowired
    protected CacheManager cacheManager;
    
    @Test
    public void testGetQualifiedResourceClassName() {
        try {
            modelService.getQualifiedResourceClassName("unknown");
        } catch (AppException e) {
            assertEquals("unknown.resource.name", e.getCode());
        }
        
        // Test cache management
        Cache cache = cacheManager.getCache(DefaultModelService.CACHE_NAME);
        
        String resourceName = "serie";
        assertNull(cache.get(resourceName)) ;
        assertEquals(Serie.class, modelService.getQualifiedResourceClassName(resourceName));
        assertEquals(Serie.class, ((SimpleValueWrapper)cache.get(resourceName)).get()) ;
    }
}
