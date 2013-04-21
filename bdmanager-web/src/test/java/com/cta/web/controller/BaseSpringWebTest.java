package com.cta.web.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cta.test.BaseSpringTest;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:spring/web-test-context.xml")
public abstract class BaseSpringWebTest extends BaseSpringTest {

	@Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setUpBaseSpringWebTest() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

	protected String toJson(String fakeJson) {
		return fakeJson.replaceAll("'", "\"");
	}
}
