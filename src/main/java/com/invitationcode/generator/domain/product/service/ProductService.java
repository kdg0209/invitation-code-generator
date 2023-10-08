package com.invitationcode.generator.domain.product.service;

import com.invitationcode.generator.domain.product.dao.ProductDao;
import com.invitationcode.generator.domain.product.domain.Money;
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

    public Long create(ProductCreateRequestDto request) {
        Product product = Product.builder()
                .name(request.getName())
                .stock(request.getStock())
                .money(new Money(request.getPrice()))
                .build();

        productRepository.save(product);
        return product.getIdx();
    }

    public Long update(Long productIdx, ProductUpdateRequestDto requestDto) {
        Product product = productDao.findByIdx(productIdx);
        product.updateName(requestDto.getName());
        product.updateStock(requestDto.getStock());
        product.updateMoney(new Money(requestDto.getPrice()));

        return product.getIdx();
    }

    public void delete(Long productIdx) {
        Product product = productDao.findByIdx(productIdx);
        product.delete();
    }

    public void decreaseStock(int stock) {
        Product product = productDao.findByIdx(1L);
        product.decreaseStock(stock);
    }
}
