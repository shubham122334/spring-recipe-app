package com.shuham.springrecipeapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void saveImageFile(Long recipeId, MultipartFile file) throws IOException;
}
