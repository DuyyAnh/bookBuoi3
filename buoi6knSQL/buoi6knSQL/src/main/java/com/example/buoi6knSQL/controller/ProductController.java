package com.example.buoi6knSQL.controller;


import com.example.buoi6knSQL.entity.Product;
import com.example.buoi6knSQL.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/")
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public String Index(Model model){
        List<Product> listproduct = productService.GetAll();
        model.addAttribute("listproduct", listproduct);
        return "products/index";
    }

    @GetMapping("products/create")
    public String createproductForm(Model model){
        model.addAttribute("product",new Product());
        return "products/create";
    }
//    @PostMapping("products/create")
//    public String createproduct(@ModelAttribute("product") Product product){
//        productService.add(product);
//        return "redirect:/products";
//    }
    @PostMapping("products/create")
    public String createproduct(@Valid Product newProduct, @RequestParam MultipartFile imageProduct, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("product", newProduct);
            return "products/create";
        }
        if(imageProduct != null && imageProduct.getSize() > 0) {
            try{
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path= Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                newProduct.setImage(newImageFile);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        productService.add(newProduct);
        return  "redirect:/products";
    }

    @GetMapping("products/edit/{id}")
    public String editproductForm(@PathVariable("id") Integer id, Model model) {
        Product editproduct = productService.getBookById(id);
        if (editproduct != null) {
            model.addAttribute("product", editproduct);
            return "products/edit";
        } else {
            return "redirect:/products";
        }
    }
    @PostMapping("products/edit/{id}")
    public String editproduct(@PathVariable("id") Integer id, @ModelAttribute("product") @Valid Product editProduct, BindingResult result, Model model) {
        Product existingProduct = productService.getBookById(id);
        if (existingProduct != null) {
            existingProduct.setName(editProduct.getName());
            existingProduct.setImage(editProduct.getImage());
            existingProduct.setPrice(editProduct.getPrice());
            productService.updateproduct(existingProduct); // Lưu thay đổi vào cơ sở dữ liệu
        }
        return "redirect:/products";
    }
    @GetMapping("products/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id) {
        Product book = productService.getBookById(id);
        if (book != null) {
            productService.deleteproduct(id);
        }
        return "redirect:/products";
    }
    @GetMapping("products/search")
    public String search(@RequestParam("searchText") String searchText,Model model) {
        List<Product> product = productService.GetAll();
        List<Product> filteredProducts = new ArrayList<>();

        if (searchText != null && !searchText.isEmpty()) {
            filteredProducts = product.stream()
                    .filter(book -> book.getName().contains(searchText))
                    .collect(Collectors.toList());
        }
        model.addAttribute("listproduct", filteredProducts);
        return "products/index";
    }
}
