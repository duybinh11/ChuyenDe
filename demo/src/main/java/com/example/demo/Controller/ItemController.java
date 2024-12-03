package com.example.demo.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.CartEntity;
import com.example.demo.Entity.CategoryEntity;
import com.example.demo.Entity.ItemEntity;
import com.example.demo.Request.OrderRequest;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ItemService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ItemController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;

    @GetMapping("index")
    public String index(Model model) {
        String username = getUsernameFromToken();
        model.addAttribute("username", username);

        List<CategoryEntity> categorys = categoryService.getAllCategory(); 
        model.addAttribute("categorys", categorys);

        List<ItemEntity> items = itemService.getAll();
        model.addAttribute("items", items);

        return "index";
    }

    @GetMapping("detail_item")
    public String detailItem(Model model, @RequestParam("idItem") int idItem) {
        ItemEntity itemEntity = itemService.getItemById(idItem);
        model.addAttribute("item", itemEntity);
        return "detail";
    }

    @GetMapping("category_item")
    public String category_item(Model model, @RequestParam("category") int categoryId) {

        String nameCategory = categoryService.getNameCategiry(categoryId);
        System.out.println(nameCategory);
        model.addAttribute("nameCategory", nameCategory);

        String username = getUsernameFromToken();
        model.addAttribute("username", username);

        List<CategoryEntity> categorys = categoryService.getAllCategory();
        model.addAttribute("categorys", categorys);

        List<ItemEntity> items = itemService.getAllByCategory(categoryId);
        model.addAttribute("items", items);
        return "category_item";
    }

    @GetMapping("cart")
    public String cart(Model model) {
        String username = getUsernameFromToken();
        List<CartEntity> carts = userService.getCartByUsername(username);
        model.addAttribute("carts", carts);
        return "cart";
    }

    @PostMapping("addCart")
    public String handleAddCart(@RequestParam("itemId") int itemId) {
        String username = getUsernameFromToken();
        userService.addCart(username, itemId, 1);
        return "redirect:/index";
    }

    @PostMapping("buyItem")
    public String handleBuyItem(@RequestBody OrderRequest request) {
        String username = getUsernameFromToken();
        orderService.addOrder(username, request);
        return "redirect:/cart";
    }

    public String getUsernameFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal(); // Ép kiểu thành Jwt
            String username = jwt.getClaim("sub"); // Lấy tên người dùng từ claim 'sub' (subject)

            return username;
        } else {
            return null;
        }
    }

}
