package edu.ndnu.capstone.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.ndnu.capstone.domain.AuthorizedUser;
import edu.ndnu.capstone.domain.AuthorizedUserService;
import edu.ndnu.capstone.domain.Search;
import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserActiveType;
import edu.ndnu.capstone.domain.UserType;
import edu.ndnu.capstone.domain.UserTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/search")
@Controller
public class SearchController
{
  @Autowired
  AuthorizedUserService userService;
  
  @Autowired
  UserTypeService userTypeService;
    
  @RequestMapping(value="/authorizedUsers", params = "form", produces = "text/html")
  public String searchFormAuthorizedUser(Model uiModel) {
      populateEditForm(uiModel, new Search());
      return "search/authorizedUsers";
  }
  
  @RequestMapping(value = "/searchAuthorizedUser", method = RequestMethod.POST, produces = "text/html")
  public String searchAuthorizedUser(@ModelAttribute("search") @Valid Search search, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes) {
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

          populateEditForm(uiModel, search);
          return "search/authorizedUsers";
      }
      List<AuthorizedUser> list = AuthorizedUser.searchUsers(search.getName());
      redirectAttributes.addFlashAttribute("authorizedusers", list);
      redirectAttributes.addFlashAttribute("successMessage", "Found the following users:");
      uiModel.asMap().clear();  
      return "redirect:/authorizedusers/result";
  }
  
  @RequestMapping(value="/users", params = "form", produces = "text/html")
  public String searchFormUser(Model uiModel) {
      populateEditForm(uiModel, new Search());
      return "search/users";
  }
  
  @RequestMapping(value = "/searchUser", method = RequestMethod.POST, produces = "text/html")
  public String searchUser(@ModelAttribute("search") @Valid Search search, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes) {
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

          populateEditForm(uiModel, search);
          return "search/users";
      }
      List<User> list = User.searchUsers(search.getName());
      redirectAttributes.addFlashAttribute("users", list);
      redirectAttributes.addFlashAttribute("successMessage", "Found the following users:");
      uiModel.asMap().clear();  
      return "redirect:/users/result";
  }
  
  void populateEditForm(Model uiModel, Search search) {
      uiModel.addAttribute("search", search);
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
