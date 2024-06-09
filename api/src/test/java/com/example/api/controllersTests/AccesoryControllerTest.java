package com.example.api.controllersTests;

import com.example.api.config.CustomUserDetailsService;
import com.example.api.config.JWTAuthFilter;
import com.example.api.config.JWTUtils;
import com.example.api.controller.AccessoryController;
import com.example.api.model.Accessory;
import com.example.api.repository.AccessoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccessoryController.class)
@ContextConfiguration(classes = TestConfig.class)
public class AccesoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccessoryRepository accesoryRepository;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JWTAuthFilter jwtAuthFilter;

    @Autowired
    private JWTUtils jwtUtils;

    @Test
    public void testGetAllAccesories_Unauthorized() throws Exception {
        Accessory accesory1 = new Accessory();
        accesory1.setName("Zawieszka");
        List<Accessory> accesories = Arrays.asList(accesory1);
        when(accesoryRepository.findAll()).thenReturn(accesories);

        mockMvc.perform(get("/api/accesories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
