package com.shuham.springrecipeapp.controller;

import com.shuham.springrecipeapp.commands.RecipeCommand;
import com.shuham.springrecipeapp.services.ImageService;
import com.shuham.springrecipeapp.services.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("recipe")
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "views/recipe/imageupload";
    }
    @PostMapping("/{id}/image")
    public String handleImagesPost(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {

        imageService.saveImageFile(Long.valueOf(id),file);
        return "redirect:/recipe/"+id+"/show";
    }

    @GetMapping("/{id}/recipeImage")
    public void renderingImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException{

        RecipeCommand recipeCommand=recipeService.findCommandById(Long.valueOf(id));

        if(recipeCommand.getImage()!=null){

         byte[] byteObj=new byte[recipeCommand.getImage().length];

         int i=0;

          for (Byte b:recipeCommand.getImage()) {

            byteObj[i++]=b;
          }

          response.setContentType("image/jpg");
            InputStream inp=new ByteArrayInputStream(byteObj);
            IOUtils.copy(inp,response.getOutputStream());
        }
    }
}
