package edu.ndnu.capstone.web;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import edu.ndnu.capstone.domain.UploadItem;
import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserService;
import edu.ndnu.capstone.domain.UserType;

import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  UserService userService;
    
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
        //String fileName = null;
        InputStream inputStream = null;
        //OutputStream outputStream = null;
        if (file.getSize() > 0)
        {
            inputStream = file.getInputStream();
            if (file.getSize() > 10000000)
            {
                System.out.println("File Size exceeded:::" + file.getSize());
                return "/uncaughtException";
            }
            System.out.println("size::" + file.getSize());
            
            //fileName = file.getOriginalFilename();
            //outputStream = new FileOutputStream(fileName);
            System.out.println("fileName:" + file.getOriginalFilename());
            //int readBytes = 0;
            //byte[] buffer = new byte[10000];
            //while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1)
            //{
                //outputStream.write(buffer, 0, readBytes);
            //}
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder out = new StringBuilder();
            String line;
            List<UserType> types = UserType.findAllUserTypes();
            
            Hashtable<String, UserType> type_cache = new Hashtable<String, UserType>();
            
            for (UserType type : types)
            {
                type_cache.put(type.getName(), type);
            }
            
            while ((line = reader.readLine()) != null) {
                out.append(line);
                System.out.println(line);
                
                // This regex needs to handle quotes better, or readLine needs to be better
                // Because when specifying quotes around elements in the csv file
                // The User validations fail because it literally sees the quotes
                String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                System.out.println(parts[0]);
                System.out.println(parts[1]);
                System.out.println(parts[2]);
                System.out.println(parts[3]);
                System.out.println(parts[4]);
                System.out.println(parts[5]);
                Calendar created = java.util.Calendar.getInstance();
                User newUser = new User();
                
                User returnedUser = User.findUserByEmail(parts[1]);
                
                if (returnedUser instanceof User)
                {
                    continue;
                }
                
                try {
                    newUser.setName(parts[0]);
                    newUser.setEmail(parts[1]);
                    newUser.setPhone(parts[4]);
                    newUser.setTypeId(type_cache.get(parts[5]));
                    newUser.setActive(1);
                    newUser.setCreated(created);
                    
                    if (parts.length >= 7)
                    {
                        newUser.setDescription(parts[6]);
                    }
                    
                    userService.updateUser(newUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            //System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close();
            
            //outputStream.close();
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
