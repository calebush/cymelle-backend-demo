package com.demo.cymelle_backend.services;

import com.demo.cymelle_backend.dtos.ProductDto;
import com.demo.cymelle_backend.dtos.ProductRequest;
import com.demo.cymelle_backend.entities.Product;
import com.demo.cymelle_backend.mappers.ProductMapper;
import com.demo.cymelle_backend.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto createProduct(ProductRequest request) {
        var product = productMapper.toEntity(request);
        var savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }



    @Transactional
    public ProductDto updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStockQuantity(request.getStockQuantity());


        Product savedProduct = productRepository.save(existingProduct);
        return productMapper.toDto(savedProduct);
    }

    // Fetch all products
    @Transactional(readOnly = true)
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    // Get Product by Id
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    // Delete Product
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete: Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }


}