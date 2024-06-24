package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.mapper.ProductMapper;
import com.adkp.fuexchange.mapper.VariationMapper;
import com.adkp.fuexchange.pojo.*;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.*;
import com.adkp.fuexchange.response.*;
import jakarta.transaction.Transactional;
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
    private final CategoryRepository categoryRepository;
    private final ProductDetailRepository productDetailRepository;
    private final VariationDetailRepository variationDetailRepository;
    private final VariationRepository variationRepository;
    private final SellerRepository sellerRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductDetailRepository productDetailRepository, VariationDetailRepository variationDetailRepository, VariationRepository variationRepository, SellerRepository sellerRepository, ProductImageRepository productImageRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productDetailRepository = productDetailRepository;
        this.variationDetailRepository = variationDetailRepository;
        this.variationRepository = variationRepository;
        this.sellerRepository = sellerRepository;
        this.productImageRepository = productImageRepository;
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
    public ResponseObject<Object> topProductByUserIdAndName(String studentId, String productName, int current) {
        Pageable currentProduct = PageRequest.of(0, current);
        String newProductName = Optional.ofNullable(productName).map(String::valueOf).orElse("");
        Seller seller = sellerRepository.getInformationSellerByStudentId(studentId);

        List<ProductDTO> productDTO = productMapper.toProductDTOList(productRepository.filterSellerProduct(seller.getSellerId(), newProductName, currentProduct));
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
        ProductDetail productDetail = new ProductDetail(registerProductRequest.getProductName(), registerProductRequest.getProductDescription());
        productDetailRepository.save(productDetail);

        for (ProductImageRequest productImageRequest : registerProductRequest.getProductImageRequestsList()) {
            productImageRepository.save(new ProductImage(productDetail, productImageRequest.getImageUrl()));
        }

        Seller seller = sellerRepository.getInformationSellerByStudentId(registerProductRequest.getStudentId());

        Product product = new Product(productDetail, sellerRepository.getReferenceById(seller.getSellerId())
                , categoryRepository.getReferenceById(registerProductRequest.getCategoryId()), registerProductRequest.getPrice()
                , true);
        productRepository.save(product);

        List<RegisterVariationResponse> variationList = new ArrayList<>();
        List<RegisterVariationDetailResponse> variationDetailResponseList = new ArrayList<>();
        for (VariationRequest variationRequest : registerProductRequest.getVariationList()) {
            Variation variation = new Variation(variationRequest.getVariationName(), product);
            variationRepository.save(variation);


            for (VariationDetailRequest variationDetailRequest : variationRequest.getVariationDetailRequestList()) {
                variationDetailRepository.save(new VariationDetail(variation, variationDetailRequest.getDescription()));
                variationDetailResponseList.add(new RegisterVariationDetailResponse(variationDetailRequest.getDescription()));
                variationList.add(new RegisterVariationResponse(variation.getVariationId(), variation.getVariationName(), variationDetailResponseList));
            }


        }


        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Tạo sản phẩm thành công!").data(new RegisterProductRespone(product.getProductId()
                        , product.getSellerId().getSellerId(), product.getCategoryId(), product.getPrice(), product.isProductStatus()
                        , variationList, productDetail))
                .build();
    }


    @Override
    @Transactional
    public ResponseObject<Object> updateProductInformation(UpdateInformationProductRequest updateInformationProductRequest) {
        if (productRepository.existsById(updateInformationProductRequest.getProductID())) {
            Product updatedProduct = productRepository.getReferenceById(updateInformationProductRequest.getProductID());
            updatedProduct.setCategoryId(categoryRepository.getReferenceById(updateInformationProductRequest.getCategoryId()));
            updatedProduct.setPrice(updateInformationProductRequest.getPrice());
            productRepository.save(updatedProduct);
            // product detail
            ProductDetail productDetail = productDetailRepository.getReferenceById(updatedProduct.getProductDetailId().getProductDetailId());
            productDetail.setProductName(updateInformationProductRequest.getProductDetailIdProductName());
            productDetail.setDescription(updateInformationProductRequest.getProductDetailIdDescription());
            productDetailRepository.save(productDetail);
            // variation
            if(updateInformationProductRequest.getVariationId()!=null){
                Variation variation = variationRepository.getReferenceById(updateInformationProductRequest.getVariationId());
                variation.setVariationName(updateInformationProductRequest.getVariationName());
                variationRepository.save(variation);
            }
            if(updateInformationProductRequest.getVariationDetailId()!=null){
                VariationDetail variationDetail = variationDetailRepository.getReferenceById(updateInformationProductRequest.getVariationDetailId());
                variationDetail.setDescription(updateInformationProductRequest.getVariationDescription());
                variationDetailRepository.save(variationDetail);

            }
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

    @Override
    public ProductResponse getProductByVariationDetailId(List<Integer> variationDetailId) {

        List<VariationDetail> variationDetails = variationDetailRepository.findAllById(variationDetailId);

        List<Integer> variationIds = new ArrayList<>();

        List<VariationResponse> variations = getVariation(variationDetails, variationIds);

        ProductDTO product = productMapper.toProductDTO(productRepository.getProductByVariationId(variationIds));

        product.setVariation(null);
        product.setSeller(null);

        return ProductResponse.builder()
                .variation(variations)
                .product(product)
                .build();
    }

    private List<VariationResponse> getVariation(List<VariationDetail> variationDetails, List<Integer> variationIds) {

        List<VariationResponse> variations = new ArrayList<>();

        for (VariationDetail detail : variationDetails) {
            variationIds.add(detail.getVariationId().getVariationId());

            VariationResponse variation = VariationResponse.builder()
                    .variationId(detail.getVariationId().getVariationId())
                    .variationName(detail.getVariationId().getVariationName())
                    .variationDetail(detail)
                    .build();

            detail.getVariationId().setProductId(null);
            detail.setVariationId(null);

            variations.add(variation);
        }
        return variations;
    }
}
