package com.rafal.sneakerstoreapp.registrationandlogin;

import com.rafal.sneakerstoreapp.user.UserReceiverDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationAndLoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RegistrationAndLoginControllerTest {

    @MockBean
    private RegistrationService registrationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registrationPage_ReturnsRegistrationView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("user"));

    }

    @Test
    void registration_ValidUser_ReturnsRedirectionToLogin() throws Exception {
        UserReceiverDto user = new UserReceiverDto();
        when(registrationService.processRegistration(eq(user), any(BindingResult.class), any(Model.class)))
                .thenReturn("redirect:/login");

        mockMvc.perform(post("/register/save")
                        .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }


    @Test
    void registration_InvalidUser_ReturnsRegistrationView() throws Exception {
        UserReceiverDto invalidUserDto = new UserReceiverDto();
        when(registrationService.processRegistration(eq(invalidUserDto), any(BindingResult.class), any(Model.class)))
                .thenReturn("user/register");

        mockMvc.perform(post("/register/save")
                        .flashAttr("user", invalidUserDto))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void loginPage_ReturnsLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));
    }
}