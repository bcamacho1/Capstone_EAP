package edu.ndnu.capstone.web;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import edu.ndnu.capstone.domain.UploadItem;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/dataImport")
public class UploadItemController
{
  @RequestMapping(method = RequestMethod.GET)
  public String getUploadForm(Model model)
  {
    model.addAttribute(new UploadItem());
    return "dataImport";
  }

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public String create(UploadItem uploadItem, BindingResult result)
  {   
    if (result.hasErrors()) 
    {
        for (ObjectError error : result.getAllErrors())
        {
            System.out.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
        }
        return "/uncaughtException";
    }
    
    // Some type of file processing...
    System.out.println("-------------------------------------------");
    //System.out.println("Test upload: " + uploadItem.getName());
    System.out.println("Test upload: " + uploadItem.getFileData().getOriginalFilename());
    System.out.println("-------------------------------------------");
    
    try
    {
        MultipartFile file = uploadItem.getFileData();
        String fileName = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        if (file.getSize() > 0)
        {
            inputStream = file.getInputStream();
            if (file.getSize() > 10000000)
            {
                System.out.println("File Size exceeded:::" + file.getSize());
                return "/uncaughtException";
            }
            System.out.println("size::" + file.getSize());
            
            fileName = file.getOriginalFilename();
            outputStream = new FileOutputStream(fileName);
            System.out.println("fileName:" + file.getOriginalFilename());
            int readBytes = 0;
            byte[] buffer = new byte[10000];
            while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1)
            {
                outputStream.write(buffer, 0, readBytes);
            }
            outputStream.close();
            inputStream.close();
            // ..........................................
        }
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    }
    return "index";
  }
}
