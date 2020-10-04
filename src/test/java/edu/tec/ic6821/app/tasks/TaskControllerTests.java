package edu.tec.ic6821.app.tasks;

import edu.tec.ic6821.app.security.config.JwtAuthEntryPoint;
import edu.tec.ic6821.app.security.config.JwtProvider;
import edu.tec.ic6821.app.security.service.CustomUserDetailsService;
import edu.tec.ic6821.app.tasks.controller.TaskController;
import edu.tec.ic6821.app.tasks.dao.TaskDao;
import edu.tec.ic6821.app.tasks.model.Task;
import edu.tec.ic6821.app.tasks.service.TaskService;
import edu.tec.ic6821.app.users.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableWebMvc
@WebMvcTest(TaskController.class)

public class TaskControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskDao taskDao;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @MockBean
    private JwtProvider jwtProvider;
    private Object Task;

    

    @Test
    public void givenInvalidTaskListIdReturnBadRequest() throws Exception {
        // given
        String body = String.format("{\"name\": \"prueba\"}");
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
        mvc.perform(put("/api/taskList/task_list_id/task/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .header("Authorization", "Bearer sometoken"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenInvalidIdReturnBadRequest() throws Exception {
        // given
        String body = String.format("{\"name\": \"prueba\"}");
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
        mvc.perform(put("/api/taskList/2/task/id")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)
                .header("Authorization", "Bearer sometoken"))
                .andExpect(status().isBadRequest());
    }
}