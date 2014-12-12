package edu.ndnu.capstone.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.ndnu.capstone.domain.PasswordChange;
import edu.ndnu.capstone.domain.AuthorizedUser;
import edu.ndnu.capstone.domain.AuthorizedUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@Controller
public class PasswordChangeController
{
  @Autowired
  AuthorizedUserService userService;
    
  @RequestMapping(value = "/passwordChange", method = RequestMethod.GET)
  public String getChangePasswordForm(Model model)
  {
    model.addAttribute(new PasswordChange());
    return "passwordChange";
  }
  
  @RequestMapping(value = "/resetUserPassword", method = RequestMethod.GET)
  public String getResetUserPasswordForm(Model model)
  {
    model.addAttribute(new PasswordChange());
    model.addAttribute("authorizedusers", userService.findAllUsers());
    return "resetUserPassword";
  }
  
  @RequestMapping(value = "/resetUserPassword/reset", method = RequestMethod.POST)
  public String resetUserPassword(@Valid PasswordChange passwordChange, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) 
  {
      if (bindingResult.hasErrors()) {
          java.util.List<ObjectError> list=bindingResult.getAllErrors();
          int error_flag = 0;
          for(ObjectError obj:list)
          {
              System.out.println("objname: "+obj.getObjectName()+";"+obj.getCode()+";"+obj.getDefaultMessage());
              if(obj instanceof FieldError)
              {
                  System.out.println(((FieldError)obj).getField());
                  // if the only failure is the old password validation, 
                  // skip it and process the reset
                  if ((((FieldError)obj).getField().compareTo("oldPassword") != 0))
                  {
                      error_flag = 1;
                  }
              }
          }
          
          if (error_flag != 0)
          {
              uiModel.addAttribute("authorizedusers", userService.findAllUsers());
              return "resetUserPassword";
          }
      }
      try
      {
          PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    
          if (passwordChange.getNewPassword().compareTo(passwordChange.getNewPasswordConfirm()) != 0)
          {
              bindingResult.addError(new ObjectError("passwordChange", "The new password you entered does not match on both input fields."));
              uiModel.addAttribute("authorizedusers", userService.findAllUsers());
              return "resetUserPassword";
          }
          else
          {
              AuthorizedUser user = passwordChange.getUserId();
              String hashedPassword = passwordEncoder.encode(passwordChange.getNewPassword());
              user.setPassword(hashedPassword);
              userService.updateUser(user);
              redirectAttributes.addFlashAttribute("successMessage", "Your password has been reset successfully.");
              return "redirect:/authorizedusers/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
          }
      } catch (Exception e) {
          e.printStackTrace();
          bindingResult.addError(new ObjectError("passwordChange", "An error has occurred, please contact an Administrator."));
          uiModel.addAttribute("authorizedusers", userService.findAllUsers());
          return "resetUserPassword";
      }
  }

  @RequestMapping(value = "/passwordChange/process", method = RequestMethod.POST)
  public String changePassword(@Valid PasswordChange passwordChange, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) 
  {
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

          return "passwordChange";
      }
      try
      {
          String login = SecurityContextHolder.getContext().getAuthentication().getName();
          AuthorizedUser user = AuthorizedUser.findUserByUsername(login);
          String old_password_confirm = passwordChange.getOldPassword();
          PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
          
          if (passwordEncoder.matches(old_password_confirm, user.getPassword()))
          {              
              if (passwordChange.getNewPassword().compareTo(passwordChange.getNewPasswordConfirm()) != 0)
              {
                  bindingResult.addError(new ObjectError("passwordChange", "The new password you entered does not match on both input fields."));
                  return "passwordChange";
              }
              else
              {
                  String hashedPassword = passwordEncoder.encode(passwordChange.getNewPassword());
                  user.setPassword(hashedPassword);
                  userService.updateUser(user);
                  redirectAttributes.addFlashAttribute("successMessage", "Your password has been updated successfully.");
                  return "redirect:/authorizedusers/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
              }
          } 
          else 
          {
              bindingResult.addError(new ObjectError("passwordChange", "The old password you entered does not match our records."));
              return "passwordChange";
          }
      } catch (Exception e) {
          e.printStackTrace();
          bindingResult.addError(new ObjectError("passwordChange", "An error has occurred, please contact an Administrator."));
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
