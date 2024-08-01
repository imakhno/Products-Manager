package ru.imakhno.productsWebApp.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.imakhno.productsWebApp.controller.pyaload.UpdateProductPayLoad;
import ru.imakhno.productsWebApp.entity.Product;
import ru.imakhno.productsWebApp.service.ProductService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

// Контроллер для работы с одним конкретным товаром по id
@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products/{productId:\\d+}")
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    // Получение модели объекта, чтобы не дублировать код в каждом методе
    // Выбрасываем здесь эе исключение, если товара нет в БД
    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productService.findProduct(productId).orElseThrow(() ->
                new NoSuchElementException("catalogue.errors.product.not_found"));
    }

    @GetMapping
    public String getProduct() {
        return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage() {
        return "catalogue/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute(value = "product", binding = false) Product product,
                                @Valid UpdateProductPayLoad payLoad,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("payload", payLoad);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList()));
            return "catalogue/products/edit";
        } else {
            this.productService.updateProduct(product.getId(), payLoad.title(), payLoad.details());
            return "redirect:/catalogue/products/%d".formatted(product.getId());
        }

    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/catalogue/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(
            NoSuchElementException exception,
            Model model,
            HttpServletResponse response, Locale locale) {

        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(),new Object[0],
                        exception.getMessage(),
                        locale));
        return "catalogue/errors/404";
    }
}
