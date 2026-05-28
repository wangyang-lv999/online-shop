package com.wy.shop.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wy.shop.common.service.ProductService;
import com.wy.shop.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    /** 商品列表（排除已逻辑删除） */
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("productList",
                productService.list(
                        new QueryWrapper<Product>().ne("status", -1).orderByDesc("product_id")));
        return "admin/product/list";
    }

    /** 新增页面 */
    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/form";
    }

    /** 保存新商品 */
    @PostMapping("/save")
    public String save(@ModelAttribute Product product,
                       @RequestParam(value = "coverFiles", required = false) MultipartFile[] coverFiles,
                       Model model) {
        try {
            productService.saveProduct(product, coverFiles);
            return "redirect:/admin/product/list";
        } catch (Exception e) {
            model.addAttribute("error", "保存失败：" + e.getMessage());
            model.addAttribute("product", product);
            return "admin/product/form";
        }
    }

    /** 编辑页面 */
    @GetMapping("/edit/{productId}")
    public String editPage(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.getById(productId));
        return "admin/product/form";
    }

    /** 更新商品 */
    @PostMapping("/update")
    public String update(@ModelAttribute Product product,
                         @RequestParam(value = "coverFiles", required = false) MultipartFile[] coverFiles,
                         Model model) {
        try {
            productService.updateProduct(product, coverFiles);
            return "redirect:/admin/product/list";
        } catch (Exception e) {
            model.addAttribute("error", "更新失败：" + e.getMessage());
            model.addAttribute("product", product);
            return "admin/product/form";
        }
    }

    /** 上架/下架 */
    @GetMapping("/status/{productId}/{status}")
    public String changeStatus(@PathVariable Long productId,
                               @PathVariable Integer status) {
        productService.changeStatus(productId, status);
        return "redirect:/admin/product/list";
    }

    /** 逻辑删除 */
    @GetMapping("/delete/{productId}")
    public String delete(@PathVariable Long productId) {
        productService.logicDelete(productId);
        return "redirect:/admin/product/list";
    }
}