package com.example.nghiahaui.service;

import java.util.List;

import com.example.nghiahaui.entity.Tag;
import com.example.nghiahaui.model.request.CreateTagRequest;

public interface TagService {
    
    List<Tag> getListTag();

    Tag createTag(CreateTagRequest request);

    Tag updateTag(long id,CreateTagRequest request);

    void enableTag(long id);

    void deleleTag(long id);

}
