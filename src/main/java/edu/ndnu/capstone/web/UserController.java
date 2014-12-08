package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserActiveType;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;
import org.joda.time.format.DateTimeFormat;

@RequestMapping("/users")
@Controller
@RooWebScaffold(path = "users", formBackingObject = User.class)
@GvNIXWebJQuery
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmergencyService emergencyService;

    @Autowired
    UserTypeService userTypeService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
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
            return "users/create";
        }
        try {
            userService.saveUser(user);
            uiModel.asMap().clear();  
            return "redirect:/users/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
        } catch (Exception e) {
            bindingResult.addError(new FieldError("user", "username","Username is already in the database"));
            populateEditForm(uiModel, user);
            return "users/create";
        }


    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new User());
        return "users/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("user", userService.findUser(id));
        uiModel.addAttribute("itemId", id);
        return "users/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("users", User.findUserEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) userService.countAllUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("users", User.findAllUsers(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "users/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "users/update";
        }
        uiModel.asMap().clear();
        userService.updateUser(user);
        return "redirect:/users/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, userService.findUser(id));
        return "users/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        User user = userService.findUser(id);
        userService.deleteUser(user);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/users";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("user_created_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, User user) {
        uiModel.addAttribute("user", user);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("emergencys", emergencyService.findAllEmergencys());
        uiModel.addAttribute("useractivetypes", UserActiveType.findTypes());
        
        List<UserType> types = userTypeService.findAllUserTypes();
        
        for (int i = 0; i < types.size(); i++) {
            UserType element = types.get(i);
            if (element.getName().compareTo("Admin") == 0 ||
                element.getName().compareTo("First Responder") == 0) {
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
