package ru.imakhno.productsWebApp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.imakhno.productsWebApp.controller.pyaload.NewProductPayLoad;
import ru.imakhno.productsWebApp.entity.Product;
import ru.imakhno.productsWebApp.service.ProductService;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

    private final ProductService productService;


    @GetMapping("list")
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productService.findAllProducts());
        return "catalogue/products/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("create")
    public String createProduct(@Valid NewProductPayLoad payLoad,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payLoad);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList()));
            return "catalogue/products/new_product";
        } else {
            Product product = this.productService.createProduct(payLoad.title(), payLoad.details());
            return "redirect:/catalogue/products/%d".formatted(product.getId());
        }
    }

}

















