package com.demo.cymelle_backend.mappers;

import com.demo.cymelle_backend.dtos.ProductDto;
import com.demo.cymelle_backend.dtos.ProductRequest;
import com.demo.cymelle_backend.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductRequest productRequest);
}
