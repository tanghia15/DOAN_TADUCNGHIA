package com.example.nghiahaui.service;

import java.util.List;

import com.example.nghiahaui.entity.Image;

public interface ImageService {
    List<Image> getListImage();

    Image getImageById(long id);

    Image save(Image image);

    List<Image> getListByUser(long userId);

    void deleteImage(long id);
}
