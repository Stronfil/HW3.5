package com.komissarov.springstore.controller;

import com.komissarov.springstore.entity.Product;
import com.komissarov.springstore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private ProductService productService;
    private CartService cartService;
    private OrderService orderService;
    private CartMapperService cartMapperService;
    private RabbitSenderService senderService;

    @Autowired
    public void setCartMapperService(CartMapperService cartMapperService) {
        this.cartMapperService = cartMapperService;
    }

    @Autowired
    public void setSenderService(RabbitSenderService senderService) {
        this.senderService = senderService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/viewProducts", method = RequestMethod.GET)
    public String viewProducts(HttpServletRequest request, Model uiModel,
                               @RequestParam(required = false) Integer currentPage,
                               @RequestParam(required = false) Double min,
                               @RequestParam(required = false) Double max) {
        cartService.addCartIfAbsent(request.getSession());
        if (currentPage == null) {
            currentPage = 0;
        }
        uiModel.addAttribute("products", productService.getProducts(min, max, currentPage));
        uiModel.addAttribute("currentPage", currentPage);
        return "view-products";
    }

    @RequestMapping("/addProduct")
    public String addProduct(Model uiModel) {
        uiModel.addAttribute("product", new Product());
        return "add-product";
    }

    @RequestMapping("/cart")
    public String cart(HttpServletRequest request, Model uiModel) {
        uiModel.addAttribute("totalCost", cartService.getTotalCost(request.getSession()));
        return "cart";
    }

    @RequestMapping("/order/save")
    public String saveOrder(HttpServletRequest request) {
        String serializedCart = cartMapperService.serialize(cartService.getCart(request.getSession()));
        senderService.sendMessage(serializedCart);
        return "redirect:view";
    }

    @RequestMapping("/order/view")
    public String viewOrders(Model uiModel) {
        uiModel.addAttribute("orders", orderService.getUserOrders());
        return "order";
    }

    @RequestMapping("/processForm")
    public String processForm(@ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return "redirect:viewProducts";
    }

    @RequestMapping("/addToCart")
    public String addToCart(@ModelAttribute("product") Product product, HttpServletRequest request) {
        cartService.addProduct(request.getSession(), product);
        return "redirect:viewProducts";
    }

    @RequestMapping("/removeFromCart")
    public String removeFromCart(@ModelAttribute("product") Product product, HttpServletRequest request) {
        cartService.deleteProduct(request.getSession(), product);
        return "redirect:cart";
    }
}
