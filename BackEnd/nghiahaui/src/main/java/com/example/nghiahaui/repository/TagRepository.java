package com.example.nghiahaui.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nghiahaui.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    
}
