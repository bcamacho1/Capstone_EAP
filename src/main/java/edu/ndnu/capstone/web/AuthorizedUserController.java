package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.AuthorizedUser;
import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserActiveType;
import edu.ndnu.capstone.domain.AuthorizedUserService;
import edu.ndnu.capstone.domain.UserService;
import edu.ndnu.capstone.domain.UserType;
import edu.ndnu.capstone.domain.UserTypeService;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;
import org.joda.time.format.DateTimeFormat;

@RequestMapping("/authorizedusers")
@Controller
@RooWebScaffold(path = "authorizedusers", formBackingObject = AuthorizedUser.class)
@GvNIXWebJQuery
public class AuthorizedUserController {

    @Autowired
    AuthorizedUserService authorizedUserService;
    
    @Autowired
    UserService userService;

    @Autowired
    EmergencyService emergencyService;

    @Autowired
    UserTypeService userTypeService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@ModelAttribute("authorizeduser") @Valid AuthorizedUser user, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            java.util.List<ObjectError> list=bindingResult.getAllErrors();
            for(ObjectError obj:list)
            {
                System.out.println("objname: "+obj.getObjectName()+";"+obj.getCode()+";"+obj.getDefaultMessage());
                
                if(obj instanceof FieldError)
                {
                    System.out.println(((FieldError)obj).getField());
                }
            }

            populateEditForm(uiModel, user);
            return "authorizedusers/create";
        }
        try
        {
            AuthorizedUser existingAuthUser = authorizedUserService.findUserByEmail(user.getEmail());
            if (existingAuthUser != null)
            {
                bindingResult.addError(new FieldError("authorizeduser", "email", "That email address already exists in the database."));
                populateEditForm(uiModel, user);
                return "authorizedusers/create";
            }
            
            User existingUser = userService.findUserByEmail(user.getEmail());
            if (existingUser != null)
            {
                bindingResult.addError(new FieldError("authorizeduser", "email", "That email address already exists in the database."));
                populateEditForm(uiModel, user);
                return "authorizedusers/create";
            }
            
            existingAuthUser = authorizedUserService.findUserByUsername(user.getUsername());
            if (existingAuthUser != null)
            {
                bindingResult.addError(new FieldError("authorizeduser", "username", "That username already exists in the database."));
                populateEditForm(uiModel, user);
                return "authorizedusers/create";
            }
            
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            authorizedUserService.saveUser(user);
            uiModel.asMap().clear();  
            redirectAttributes.addFlashAttribute("successMessage", "The user has been created successfully.");
            return "redirect:/authorizedusers/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            bindingResult.addError(new ObjectError("authorizeduser", "An error has occurred, please contact an Administrator."));
            populateEditForm(uiModel, user);
            return "authorizedusers/create";
        }
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AuthorizedUser());
        return "authorizedusers/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("authorizeduser", authorizedUserService.findUser(id));
        uiModel.addAttribute("itemId", id);
        return "authorizedusers/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("authorizedusers", AuthorizedUser.findUserEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) authorizedUserService.countAllUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("users", AuthorizedUser.findAllUsers(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "authorizedusers/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@ModelAttribute("authorizeduser") @Valid AuthorizedUser user, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            java.util.List<ObjectError> list=bindingResult.getAllErrors();
            int error_flag = 0;
            for(ObjectError obj:list)
            {
                System.out.println("objname: "+obj.getObjectName()+";"+obj.getCode()+";"+obj.getDefaultMessage());
                
                if(obj instanceof FieldError)
                {
                    System.out.println(((FieldError)obj).getField());
                    // if the only failure is the password validation, 
                    // skip it and process the edits
                    if ((((FieldError)obj).getField().compareTo("password") != 0))
                    {
                        error_flag = 1;
                    }
                }
            }
            
            AuthorizedUser existingAuthUser = authorizedUserService.findUserByEmail(user.getEmail());
            if (existingAuthUser != null && existingAuthUser.getId() != user.getId())
            {
                bindingResult.addError(new FieldError("authorizeduser", "email", "That email address already exists in the database."));
                populateEditForm(uiModel, user);
                return "authorizedusers/update";
            }
            
            User existingUser = userService.findUserByEmail(user.getEmail());
            if (existingUser != null && existingUser.getId() != user.getId())
            {
                bindingResult.addError(new FieldError("authorizeduser", "email", "That email address already exists in the database."));
                populateEditForm(uiModel, user);
                return "authorizedusers/update";
            }
            
            existingAuthUser = authorizedUserService.findUserByUsername(user.getUsername());
            if (existingAuthUser != null && existingAuthUser.getId() != user.getId())
            {
                bindingResult.addError(new FieldError("authorizeduser", "username", "That username already exists in the database."));
                populateEditForm(uiModel, user);
                return "authorizedusers/update";
            }
            
            if (error_flag == 0)
            {
                AuthorizedUser oldUser = authorizedUserService.findUser(user.getId());
                user.setPassword(oldUser.getPassword());
                uiModel.asMap().clear();
                authorizedUserService.updateUser(user);
                redirectAttributes.addFlashAttribute("successMessage", "The user has been updated successfully.");
                return "redirect:/authorizedusers/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
            }
            else
            {
                populateEditForm(uiModel, user);
                return "authorizedusers/update";
            }
        }
        
        uiModel.asMap().clear();
        authorizedUserService.updateUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "The user has been updated successfully.");
        return "redirect:/authorizedusers/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, authorizedUserService.findUser(id));
        return "authorizedusers/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        try {
        AuthorizedUser user = authorizedUserService.findUser(id);
        authorizedUserService.deleteUser(user);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
            return "redirect:/authorizedusers";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "This user cannot be deleted because it owns other objects.");
            return "redirect:/authorizedusers/" + encodeUrlPathSegment(id.toString(), httpServletRequest);
        }
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("user_created_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, AuthorizedUser user) {
        uiModel.addAttribute("authorizeduser", user);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("emergencies", emergencyService.findAllEmergencies());
        uiModel.addAttribute("useractivetypes", UserActiveType.findTypes());
        
        List<UserType> types = userTypeService.findAllUserTypes();
        
        for (int i = 0; i < types.size(); i++) {
            UserType element = types.get(i);
            if (element.getName().compareTo("Student") == 0 ||
                element.getName().compareTo("Faculty") == 0) {
                types.remove(i);
                i--;
            }
        }
        
        uiModel.addAttribute("usertypes", types);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
