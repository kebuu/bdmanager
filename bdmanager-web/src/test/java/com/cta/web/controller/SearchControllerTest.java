package com.cta.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;


public class SearchControllerTest extends BaseSpringWebTest {

    @Test
    public void searchSerie() throws Exception {
    	this.mockMvc.perform(get("/search/serie?name=Largo Winch,La trilogie Nikopol&name_op_=in").accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$[1]").exists());
    	
    	this.mockMvc.perform(get("/search/serie?id=-1&id_op_=gte").accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$[1]").doesNotExist());
    	
    	this.mockMvc.perform(get("/search/serie?bds.title=Hestia").accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$[0].id").value(-2));
    }
    
    @Test
    public void searchBd() throws Exception {
    	this.mockMvc.perform(get("/search/bd?serie.bds.title=Hestia").accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$[3]").exists());
    	
    	this.mockMvc.perform(get("/search/bd?title=Hestia").accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$[0].id").value(-6));
    	
    	this.mockMvc.perform(get("/search/bd?date=2020-01-01").accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$[0].id").exists());
    }
}
