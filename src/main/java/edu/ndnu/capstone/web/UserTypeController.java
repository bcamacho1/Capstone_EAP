package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.UserService;
import edu.ndnu.capstone.domain.UserType;
import edu.ndnu.capstone.domain.UserTypeService;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/usertypes")
@Controller
@RooWebScaffold(path = "usertypes", formBackingObject = UserType.class)
@GvNIXWebJQuery
public class UserTypeController {

    @Autowired
    UserTypeService userTypeService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid UserType userType, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userType);
            return "usertypes/create";
        }
        uiModel.asMap().clear();
        userTypeService.saveUserType(userType);
        redirectAttributes.addFlashAttribute("successMessage", "The user type has been created successfully.");
        return "redirect:/usertypes/" + encodeUrlPathSegment(userType.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new UserType());
        return "usertypes/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("usertype", userTypeService.findUserType(id));
        uiModel.addAttribute("itemId", id);
        return "usertypes/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("usertypes", UserType.findUserTypeEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) userTypeService.countAllUserTypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("usertypes", UserType.findAllUserTypes(sortFieldName, sortOrder));
        }
        return "usertypes/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UserType userType, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, userType);
            return "usertypes/update";
        }
        uiModel.asMap().clear();
        userTypeService.updateUserType(userType);
        redirectAttributes.addFlashAttribute("successMessage", "The user type has been updated successfully.");
        return "redirect:/usertypes/" + encodeUrlPathSegment(userType.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, userTypeService.findUserType(id));
        return "usertypes/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        try {
            UserType userType = userTypeService.findUserType(id);
            userTypeService.deleteUserType(userType);
            uiModel.asMap().clear();
            uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
            uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
            redirectAttributes.addFlashAttribute("successMessage", "The type was deleted successfully.");
            return "redirect:/usertypes";
        }
        catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "The type cannot be deleted because it is used in a user record.");
            return "redirect:/usertypes/" + encodeUrlPathSegment(id.toString(), httpServletRequest);
        }
    }

    void populateEditForm(Model uiModel, UserType userType) {
        uiModel.addAttribute("userType", userType);
        uiModel.addAttribute("users", userService.findAllUsers());
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
