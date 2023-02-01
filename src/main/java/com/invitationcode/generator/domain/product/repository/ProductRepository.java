package com.invitationcode.generator.domain.product.repository;

import com.invitationcode.generator.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
