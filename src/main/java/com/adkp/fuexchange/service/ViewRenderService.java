package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CampusDTO;
import com.adkp.fuexchange.dto.CategoryDTO;
import com.adkp.fuexchange.dto.PostTypeDTO;

import java.util.List;

public interface ViewRenderService {
    List<CampusDTO> viewAllCampus();

    List<PostTypeDTO> viewAllPostType();

    List<CategoryDTO> viewAllCategoryType();
}
