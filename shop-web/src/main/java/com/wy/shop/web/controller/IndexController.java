package com.wy.shop.web.controller;

import com.wy.shop.common.entity.Product;
import com.wy.shop.common.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class IndexController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        // 查询首页商品
        model.addAttribute("productList", productService.getHomeTop10Products());
        return "index";
    }

    /**
     * 商品详情页
     */
    @GetMapping("/product/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductDetail(id);

        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "商品不存在或已下架");
            return "redirect:/";
        }

        model.addAttribute("product", product);
        return "detail";
    }
}
