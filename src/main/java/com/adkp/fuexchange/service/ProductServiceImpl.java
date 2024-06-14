package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.dto.VariationDTO;
import com.adkp.fuexchange.mapper.ProductMapper;
import com.adkp.fuexchange.mapper.VariationMapper;
import com.adkp.fuexchange.pojo.*;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.*;
import com.adkp.fuexchange.response.RegisterProductRespone;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.response.VariationResponse;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;
    private  final ProductDetailRepository productDetailRepository;
    private  final VariationDetailRepository variationDetailRepository;
    private  final  VariationRepository variationRepository;
    private final SellerRepository sellerRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductMapper productMapper;
    private final VariationMapper variationMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductDetailRepository productDetailRepository, VariationDetailRepository variationDetailRepository, VariationRepository variationRepository, SellerRepository sellerRepository, ProductImageRepository productImageRepository, ProductMapper productMapper, VariationMapper variationMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productDetailRepository = productDetailRepository;
        this.variationDetailRepository = variationDetailRepository;
        this.variationRepository = variationRepository;
        this.sellerRepository = sellerRepository;
        this.productImageRepository = productImageRepository;
        this.productMapper = productMapper;
        this.variationMapper = variationMapper;
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
    public ResponseObject<Object> createProduct(RegisterProductRequest registerProductRequest) {
        ProductDetail productDetail = new ProductDetail(registerProductRequest.getProductName(),registerProductRequest.getProductDescription());
        productDetailRepository.save(productDetail);

        for (ProductImageRequest productImageRequest : registerProductRequest.getProductImageRequestsList()) {
            productImageRepository.save(new ProductImage(productDetail,productImageRequest.getImageUrl()));
        }

        Product product = new Product(productDetail,sellerRepository.getReferenceById(registerProductRequest.getSellerId())
                ,categoryRepository.getReferenceById(registerProductRequest.getCategoryId()),registerProductRequest.getPrice()
                ,true);
        productRepository.save(product);

        List<VariationResponse> variationList = new ArrayList<>();
        for(VariationRequest variationRequest : registerProductRequest.getVariationList()){
            Variation variation = new Variation(variationRequest.getVariationName(),product);
            variationRepository.save(variation);
            variationList.add(new VariationResponse(variation.getVariationId(),variation.getVariationName()));
        }




        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Tạo sản phẩm thành công!").data(new RegisterProductRespone(product.getProductId()
                        ,product.getSellerId().getSellerId(),product.getCategoryId(),product.getPrice(),product.isProductStatus()
                        ,variationList,productDetail))
                .build();
    }

    @Override
    @Transactional
    public ResponseObject<Object> updateProductInformation(UpdateInformationProductRequest updateInformationProductRequest) {
        Product product = productRepository.getReferenceById(updateInformationProductRequest.getProductID());


          product.setCategoryId(categoryRepository.getReferenceById(updateInformationProductRequest.getCategoryId()));

      if(updateInformationProductRequest.getPrice()>0){
          product.setPrice(updateInformationProductRequest.getPrice());
      }
        // product detail
       ProductDetail productDetail = productDetailRepository.getReferenceById(product.getProductDetailId().getProductDetailId());
      productDetail.setProductName(updateInformationProductRequest.getProductDetailIdProductName());
      productDetail.setDescription(updateInformationProductRequest.getProductDetailIdDescription());

        // product image (where tu image co nhung casi = prdDetailID)
       if(!updateInformationProductRequest.getImageUrl().isEmpty()){
          ProductImage getProductImg = productImageRepository.getReferenceById(updateInformationProductRequest.getProductImageId());
        getProductImg.setImageUrl(updateInformationProductRequest.getImageUrl());
       }
        //Variation
      if(!updateInformationProductRequest.getVariationName().isEmpty()){
         Variation variation = variationRepository.getReferenceById(updateInformationProductRequest.getVariationId());
          variation.setVariationName(updateInformationProductRequest.getVariationName());
      }
       // variation detail
       if(!updateInformationProductRequest.getDescription().isEmpty()){
          VariationDetail variationDetail = variationDetailRepository.getReferenceById(updateInformationProductRequest.getVariationDetailId());
            variationDetail.setDescription(updateInformationProductRequest.getDescription());
       }
        productRepository.save(product);
        return ResponseObject.builder().status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Cập nhật sản phẩm thành công").
                data(productMapper.toProductDTO(product))
                .build();
    }

    @Override
    public ResponseObject<Object> updateStatus(UpdateProductStatusRequest updateProductStatusRequest) {
      Product product = productRepository.getReferenceById(updateProductStatusRequest.getProductId());
        product.setProductStatus(false);
        productRepository.save(product);
        return ResponseObject.builder().status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Cập nhật sản phẩm thành công").
                data(productMapper.toProductDTO(product))
                .build();
    }

    @Override
    public void deleteProductByID(int productID) {
       productRepository.deleteById(productID);
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
