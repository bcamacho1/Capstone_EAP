package edu.ndnu.capstone.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.ndnu.capstone.domain.PasswordChange;
import edu.ndnu.capstone.domain.UploadItem;
import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserActiveType;
import edu.ndnu.capstone.domain.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping(value = "/passwordChange")
public class PasswordChangeController
{
  @Autowired
  UserService userService;
    
  @RequestMapping(method = RequestMethod.GET)
  public String getChangePasswordForm(Model model)
  {
    model.addAttribute(new PasswordChange());
    return "passwordChange";
  }

  @RequestMapping(value = "/process", method = RequestMethod.POST)
  public String changePassword(@Valid PasswordChange passwordChange, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) 
  {
      if (bindingResult.hasErrors()) {
          //bindingResult.addError(new ObjectError("user", "Username is already in the database"));
          //bindingResult.addError(new FieldError("user", "username","Username is already in the database"));
          java.util.List<ObjectError> list=bindingResult.getAllErrors();
          for(ObjectError obj:list)
          {
              System.out.println("objname"+obj.getObjectName()+";"+obj.getCode()+";"+obj.getDefaultMessage());
              if(obj instanceof FieldError)
              {
                  System.out.println(((FieldError)obj).getField());
              }
          }

          return "passwordChange";
      }
      try
      {
          System.out.println("Made it to the passwordChange method");
          System.out.println("Old password: " + passwordChange.getOldPassword());
          System.out.println("New password: " + passwordChange.getNewPassword());
          System.out.println("New password confirmed: " + passwordChange.getNewPasswordConfirm());
          
          String login = SecurityContextHolder.getContext().getAuthentication().getName();
          User user = User.findUserByUsername(login);
          user.setPassword(passwordChange.getNewPassword());
          userService.updateUser(user);
          
          return "redirect:/users/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
      } catch (Exception e) {
          return "passwordChange";
      }
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
