package com.cta.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;


public class CrudControllerTest extends BaseSpringWebTest{

    @Test
    public void createSerie() throws Exception {
        this.mockMvc.perform(post("/crud/serie").accept(MediaType.APPLICATION_JSON)
          .content(toJson("{'name':'ma serie'}")))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(1));
    }
}
