package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.mapper.ProductMapper;
import com.adkp.fuexchange.pojo.Product;
import com.adkp.fuexchange.pojo.ProductImage;
import com.adkp.fuexchange.pojo.Variation;
import com.adkp.fuexchange.repository.ProductRepository;
import com.adkp.fuexchange.request.UpdateInformationProductRequest;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ResponseObject<Object> viewMoreProduct(int current) {
        Pageable currentProduct = PageRequest.of(0, current);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thêm thành công!")
                .data(
                        productMapper.toProductDTOList(productRepository.topProduct(currentProduct))
                )
                .build();
    }

    @Override
    public ResponseObject<Object> topProductByUserIdAndName(int sellerID, String productName, int current) {
        Pageable currentProduct = PageRequest.of(0, current);
        String newProductName = Optional.ofNullable(productName).map(String::valueOf).orElse("");
        List<ProductDTO> productDTO = productMapper.toProductDTOList(productRepository.filterSellerProduct(sellerID, newProductName, currentProduct));
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem sản phẩm thành công!")
                .data(productDTO)
                .build();
    }

    @Override
    public ResponseObject<Object> getProductByProductID(int productID) {
        return ResponseObject.builder().status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Đây là thông tin sản phẩm")
                .data(
                        productMapper.toProductDTOList(productRepository.getProductByProductID(productID))
                )
                .build();
    }

    @Override
    @Transactional
    public ResponseObject<Object> updateProductInformation(UpdateInformationProductRequest updateInformationProductRequest) {
        if (productRepository.existsById(updateInformationProductRequest.getProductID())) {

            Product updatedProduct = productRepository.getReferenceById(updateInformationProductRequest.getProductID());
//           if(updateInformationProductRequest.getVariationId().size()>0){
//
//               for (int i = 0; i < updateInformationProductRequest.getVariationId().size(); i++) {
//                   updatedProduct.getVariationId().add(updateInformationProductRequest.getVariationId().get(i));
//               }
//           }


            updatedProduct.setPrice(updateInformationProductRequest.getPrice());
            updatedProduct.getProductDetailId().setProductName(updateInformationProductRequest.getProductDetailId().getProductName());
            updatedProduct.getProductDetailId().setDescription(updateInformationProductRequest.getProductDetailId().getDescription());
            productRepository.save(updatedProduct);
            return ResponseObject.builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.name())
                    .content("Đã cập nhật sản phẩm thành công").build();
        }

        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Thông tin sản phẩm không chính xác!")
                .build();
    }


    @Override
    public long countTotalPostProduct() {
        return productRepository.count();
    }

    @Override
    public long countProduct(String name, List<ProductDTO> productDTOList) {
        if (name == null || name.isEmpty()) {
            return productRepository.count();
        }
        return productDTOList.size();
    }
}
