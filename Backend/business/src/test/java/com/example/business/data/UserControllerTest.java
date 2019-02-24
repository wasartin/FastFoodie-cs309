package com.example.business.data;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.business.data.controllers.UserController;
import com.example.business.data.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO, figure out how to best test with a DB
 * @author watis
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	private static final String BASE_URL = "/users/";
	
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @MockBean
    UserController userControllerMock;
    
    /**
     * Used for converting users to and from JSON
     */
    @Autowired
    ObjectMapper objectMapper;
    
    @Before
    public void init() {
    	
    }
    
    @Test
    public void createUserTest_Success() throws Exception {
    }
    
	
	
	
	
	
	
	
	
	
}
