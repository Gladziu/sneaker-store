package com.rafal.IStore.admin;

import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private AdminService adminService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addItemPage_ReturnsAddItemView() throws Exception {
        mockMvc.perform(get("/admin/add-item"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminview/add-item"));
    }

    @Test
    void addItem_ReturnsRedirectionToHome() throws Exception {
        mockMvc.perform(post("/admin/add-item"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sneaker-store/home"));
    }

    @Test
    void usersListPage_ReturnsUsersListView() throws Exception {
        UserDto userDto = UserDto.builder()
                .firstName("bob")
                .build();
        List<UserDto> users = List.of(userDto);

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminview/users-list"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    void editItemPage_ReturnsEditItemView() throws Exception {
        Long itemId = 1L;
        Item item = new Item();

        when(adminService.findItemById(itemId)).thenReturn(item);

        mockMvc.perform(get("/admin/edit-item/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(view().name("adminview/edit-item"))
                .andExpect(model().attribute("item", item));
    }

    @Test
    void deleteItemReturnsRedirectionToHome() throws Exception {
        Long itemId = 1L;

        mockMvc.perform(delete("/admin/delete/{itemId}", itemId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sneaker-store/home"));

    }

    @Test
    void editItem_ReturnsRedirectionToHome() throws Exception {
        Item item = new Item();  // You may need to set properties of the item

        mockMvc.perform(put("/admin/edit-item")
                        .flashAttr("item", item))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sneaker-store/home"));
    }
}
