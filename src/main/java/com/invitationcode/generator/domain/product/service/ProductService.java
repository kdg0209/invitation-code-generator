package com.invitationcode.generator.domain.product.service;

import com.invitationcode.generator.domain.product.dao.ProductDao;
import com.invitationcode.generator.domain.product.domain.Product;
import com.invitationcode.generator.domain.product.dto.ProductCreateRequestDto;
import com.invitationcode.generator.domain.product.dto.ProductUpdateRequestDto;
import com.invitationcode.generator.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final ProductRepository productRepository;

    public Long create(ProductCreateRequestDto requestDto) {
        Product product = Product.builder()
                .name(requestDto.getName())
                .stock(requestDto.getStock())
                .money(requestDto.getPrice())
                .build();

        productRepository.save(product);
        return product.getIdx();
    }

    public Long update(Long productIdx, ProductUpdateRequestDto requestDto) {
        Product product = productDao.findByIdx(productIdx);
        product.updateName(requestDto.getName());
        product.updateStock(requestDto.getStock());
        product.updateMoney(requestDto.getPrice());

        return product.getIdx();
    }

    public void delete(Long productIdx) {
        Product product = productDao.findByIdx(productIdx);
        product.delete();
    }
}
