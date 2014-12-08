package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.AuthorizedUser;
import edu.ndnu.capstone.domain.UserActiveType;
import edu.ndnu.capstone.domain.AuthorizedUserService;
import edu.ndnu.capstone.domain.UserTypeService;

import java.io.UnsupportedEncodingException;

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

@RequestMapping("/authorizedusers")
@Controller
@RooWebScaffold(path = "authorizedusers", formBackingObject = AuthorizedUser.class)
@GvNIXWebJQuery
public class AuthorizedUserController {

    @Autowired
    AuthorizedUserService userService;

    @Autowired
    EmergencyService emergencyService;

    @Autowired
    UserTypeService userTypeService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AuthorizedUser user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
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
        try {
            userService.saveUser(user);
            uiModel.asMap().clear();  
            return "redirect:/authorizedusers/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
        } catch (Exception e) {
            bindingResult.addError(new FieldError("user", "username","Username is already in the database"));
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
        uiModel.addAttribute("user", userService.findUser(id));
        uiModel.addAttribute("itemId", id);
        return "authorizedusers/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("users", AuthorizedUser.findUserEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) userService.countAllUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("users", AuthorizedUser.findAllUsers(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "authorizedusers/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AuthorizedUser user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "authorizedusers/update";
        }
        uiModel.asMap().clear();
        userService.updateUser(user);
        return "redirect:/authorizedusers/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, userService.findUser(id));
        return "authorizedusers/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AuthorizedUser user = userService.findUser(id);
        userService.deleteUser(user);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/authorizedusers";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("user_created_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, AuthorizedUser user) {
        uiModel.addAttribute("user", user);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("emergencys", emergencyService.findAllEmergencys());
        uiModel.addAttribute("usertypes", userTypeService.findAllUserTypes());
        uiModel.addAttribute("useractivetypes", UserActiveType.findTypes());
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
