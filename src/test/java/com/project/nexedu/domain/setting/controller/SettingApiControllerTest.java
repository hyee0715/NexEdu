package com.project.nexedu.domain.setting.controller;

import com.project.nexedu.domain.user.dto.UserResponseDto;
import com.project.nexedu.domain.user.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SettingApiControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SettingApiController settingApiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(settingApiController).build();
    }

    @DisplayName("회원 정보를 조회한다")
    @Test
    void detail() throws Exception {
        //given
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setUsername("testUser");

        when(userService.getCurrentUserResponseDto()).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(get("/api/user/detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testUser"))
                .andReturn();
    }

    @DisplayName("회원을 삭제한다")
    @Test
    void deleteTest() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/api/user/detail/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}