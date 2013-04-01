package com.cta.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;


public class CrudControllerTest extends BaseSpringWebTest {

    @Test
    public void crudSerie() throws Exception {
    	
    	//CREATE
        this.mockMvc.perform(post("/crud/serie").accept(MediaType.APPLICATION_JSON)
          .content(toJson("{'name':'ma serie', 'synopsis': 'syno'}")))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.data.id").value(1))
          .andExpect(jsonPath("$.operation").value("CREATE"))
          .andExpect(jsonPath("$.ok").value(true))
          ;
        
        this.mockMvc.perform(post("/crud/serie").accept(MediaType.APPLICATION_JSON)
        		.content(toJson("{'name':'ma serie 2'}")))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.data.id").value(2));
        
        //UPDATE
        this.mockMvc.perform(put("/crud/serie/1").accept(MediaType.APPLICATION_JSON)
        		.content(toJson("{'name':'ma serie avec un nouveau nom', 'id': 1}")))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.data.id").value(1))
        		.andExpect(jsonPath("$.operation").value("UPDATE"))
        		.andExpect(jsonPath("$.ok").value(true))
        		;
        
        this.mockMvc.perform(put("/crud/serie/3").accept(MediaType.APPLICATION_JSON)
        		.content(toJson("{'name':'ma serie avec un nouveau nom', 'id': 1}")))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.code").value("crud.update.id.mismatch"))
        		;
        
        //READ
        this.mockMvc.perform(get("/crud/serie").accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$[1]").exists());

        this.mockMvc.perform(get("/crud/serie/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
        
        this.mockMvc.perform(get("/crud/bd/-1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(-1));
        
        //DELETE
        this.mockMvc.perform(delete("/crud/serie/2").accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.data.id").value(2))
        		.andExpect(jsonPath("$.operation").value("DELETE"))
    			.andExpect(jsonPath("$.ok").value(true));
    }
}
