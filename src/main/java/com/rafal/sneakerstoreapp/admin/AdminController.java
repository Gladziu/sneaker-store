package com.rafal.sneakerstoreapp.admin;

import com.rafal.sneakerstoreapp.item.model.Item;
import com.rafal.sneakerstoreapp.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final String REDIRECT_TO_HOME_PAGE = "redirect:/sneaker-store/home";
    private final UserService userService;
    private final AdminService adminService;

    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/add-item")
    public String addItemPage() {
        return "adminview/add-item";
    }

    @PostMapping("/add-item")
    public String addItem(Item item) {
        adminService.addItem(item);
        return REDIRECT_TO_HOME_PAGE;
    }

    @GetMapping("/users")
    public String usersListPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "adminview/users-list";
    }

    @GetMapping("/edit-item/{itemId}")
    public String editItemPage(@PathVariable("itemId") Long itemId, Model model) {
        model.addAttribute("item", adminService.findItemById(itemId));
        return "adminview/edit-item";
    }

   @DeleteMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        adminService.deleteItem(itemId);
        return REDIRECT_TO_HOME_PAGE;
    }

    @PutMapping("/edit-item")
    public String editItem(Item item) {
        adminService.editItem(item);
        return REDIRECT_TO_HOME_PAGE;
    }
}
