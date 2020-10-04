package edu.tec.ic6821.app.greetings;

import edu.tec.ic6821.app.greetings.controller.GreetingController;
import edu.tec.ic6821.app.security.config.JwtAuthEntryPoint;
import edu.tec.ic6821.app.security.config.JwtProvider;
import edu.tec.ic6821.app.security.service.CustomUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
public class GreetingControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    public void whenGreet_thenSendGreeting() throws Exception {
        // given
        String username = "someuser";

        when(jwtProvider.validateJwtToken(anyString())).thenReturn(true);
        when(jwtProvider.getUsernameFromJwtToken(anyString())).thenReturn(username);
        when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(
                org.springframework.security.core.userdetails.User
                        .withUsername(username)
                        .password("somepassword")
                        .authorities(Collections.emptyList())
                        .accountExpired(false)
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .build()
        );

        // when ... then
        mvc.perform(get("/api/greeting")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer sometoken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Hello, someuser!")));
    }

}
