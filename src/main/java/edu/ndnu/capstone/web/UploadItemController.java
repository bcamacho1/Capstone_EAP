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

import edu.ndnu.capstone.domain.AuthorizedUser;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  public String create(UploadItem uploadItem, BindingResult result, final RedirectAttributes redirectAttributes)
  {
    if (result.hasErrors()) 
    {
        for (ObjectError error : result.getAllErrors())
        {
            System.out.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
        }
        return "/uncaughtException";
    }
    
    System.out.println("-------------------------------------------");
    System.out.println("Upload: " + uploadItem.getFileData().getOriginalFilename());
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
                redirectAttributes.addFlashAttribute("errorMessage", "The file size cannot exceed 10MB.");
                return "redirect:/dataImport/";
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
            
            String errors = "";
            
            while ((line = reader.readLine()) != null) {
                out.append(line);
                
                // skip the header line
                if (line.indexOf("Emergency Contact Name") != -1)
                {
                    continue;
                }
                
                // Split the line, then strip any quotes
                String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int i=0; i<parts.length; i++)
                {
                    parts[i] = parts[i].replaceAll("\"", "");
                }
                
                Calendar created = java.util.Calendar.getInstance();
                User newUser = new User();

                User returnedUser = User.findUserByEmail(parts[2]);
                if (returnedUser instanceof User)
                {
                    errors = errors + returnedUser.getEmail() + ", already exists<br />";
                    continue;
                }
                
                AuthorizedUser returnedAuthorizedUser = AuthorizedUser.findUserByEmail(parts[2]);
                if (returnedAuthorizedUser instanceof AuthorizedUser)
                {
                    errors = errors + returnedAuthorizedUser.getEmail() + ", already exists<br />";
                    continue;
                }
                
                try {
                    newUser.setName(parts[0]);
                    
                    if (parts[1].length() > 0)
                    {
                        newUser.setIdNumber(Integer.parseInt(parts[1]));
                    }

                    newUser.setEmail(parts[2]);
                    newUser.setPhone(parts[3]);
                    newUser.setTypeId(type_cache.get(parts[4]));
                    newUser.setActive(Integer.parseInt(parts[5]));
                    newUser.setEmergencyContactName(parts[6]);
                    newUser.setEmergencyContactPhone(parts[7]);
                    newUser.setCreated(created);
                    if (parts.length >= 9)
                    {
                        newUser.setDescription(parts[8]);
                    }
                    
                    userService.updateUser(newUser);
                } catch (Exception e) {
                    errors = errors + newUser.getEmail() + ", did not pass validation<br />";
                    e.printStackTrace();
                }

            }
            //System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close();
            
            //outputStream.close();
            inputStream.close();
            
            if (errors.length() > 0)
            {
                redirectAttributes.addFlashAttribute("errorMessage", "The following users could not be imported:<br />" + errors);
            }
        }
        
        redirectAttributes.addFlashAttribute("successMessage", "The data import completed successfully.");
        return "redirect:/users";
    }
    catch (Exception e) 
    {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "An error occurred. Check your input file format.");
        return "redirect:/dataImport/";
    }
  }
}
