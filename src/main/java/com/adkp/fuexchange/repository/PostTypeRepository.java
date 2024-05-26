package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
    @Query("SELECT pt FROM PostType pt")
    List<PostType> findAllPostType();
}
