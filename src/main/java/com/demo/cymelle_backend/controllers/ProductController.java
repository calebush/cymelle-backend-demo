package com.demo.cymelle_backend.controllers;

import com.demo.cymelle_backend.dtos.ProductDto;
import com.demo.cymelle_backend.dtos.ProductRequest;
import com.demo.cymelle_backend.repositories.ProductRepository;
import com.demo.cymelle_backend.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Product related endpoints")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Add the new product")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest request) {
        // check if the product exists by name
      if( productRepository.findByName(request.getName()).isPresent()){
          return ResponseEntity.badRequest().body("Error: Product already exists!");
      }
        return ResponseEntity.ok (productService.createProduct(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        var allowedFields = java.util.Set.of("id", "name", "price", "stockQuantity");

        var validatedSort = Sort.by(pageable.getSort().stream()
                .filter(order -> allowedFields.contains(order.getProperty()))
                .map(order -> order.isAscending() ? Sort.Order.asc(order.getProperty()) : Sort.Order.desc(order.getProperty()))
                .toList());

        var finalPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                validatedSort.isSorted() ? validatedSort : Sort.by("id").descending()
        );

        Page<ProductDto> productPage = productService.getAllProducts(finalPageable);

        return productPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productPage);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a single product by ID")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


}
