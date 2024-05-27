package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CampusDTO;
import com.adkp.fuexchange.dto.CategoryDTO;
import com.adkp.fuexchange.dto.PostTypeDTO;
import com.adkp.fuexchange.mapper.CampusMapper;
import com.adkp.fuexchange.mapper.CategoryMapper;
import com.adkp.fuexchange.mapper.PostTypeMapper;
import com.adkp.fuexchange.repository.CampusRepository;
import com.adkp.fuexchange.repository.CategoryRepository;
import com.adkp.fuexchange.repository.PostTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ViewRenderServiceImpl implements ViewRenderService {
    private final CampusRepository campusRepository;
    private final PostTypeRepository postTypeRepository;
    private final CategoryRepository categoryTypeRepository;


    private final CampusMapper campusMapper;
    private final PostTypeMapper postTypeMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ViewRenderServiceImpl(CampusRepository campusRepository, PostTypeRepository postTypeRepository, CategoryRepository categoryTypeRepository, CampusMapper campusMapper, PostTypeMapper postTypeMapper, CategoryMapper categoryMapper) {
        this.campusRepository = campusRepository;
        this.postTypeRepository = postTypeRepository;
        this.categoryTypeRepository = categoryTypeRepository;
        this.campusMapper = campusMapper;
        this.postTypeMapper = postTypeMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CampusDTO> viewAllCampus() {
        return campusMapper.toCampusDTOList(campusRepository.findAllCampus());
    }

    @Override
    public List<PostTypeDTO> viewAllPostType() {
//        return postTypeMapper;
        return postTypeMapper.toPostTypeDTOList(postTypeRepository.findAllPostType());
    }

    @Override
    public List<CategoryDTO> viewAllCategoryType() {
        return categoryMapper. toCategoryDTOList(categoryTypeRepository.findAllCategoryProductType()) ;
    }
}
