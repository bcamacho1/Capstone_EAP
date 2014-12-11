package edu.ndnu.capstone.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.ndnu.capstone.domain.PasswordChange;
import edu.ndnu.capstone.domain.AuthorizedUser;
import edu.ndnu.capstone.domain.AuthorizedUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping(value = "/passwordChange")
public class PasswordChangeController
{
  @Autowired
  AuthorizedUserService userService;
    
  @RequestMapping(method = RequestMethod.GET)
  public String getChangePasswordForm(Model model)
  {
    model.addAttribute(new PasswordChange());
    return "passwordChange";
  }

  @RequestMapping(value = "/process", method = RequestMethod.POST)
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
          String hashedOldPassword = user.encryptPassword(old_password_confirm);
          
          String old_password = user.getPassword();
          
          if (old_password.compareTo(hashedOldPassword) != 0)
          {
              bindingResult.addError(new ObjectError("passwordChange", "The old password you entered does not match our records."));
              return "passwordChange";
          }
          
          if (passwordChange.getNewPassword().compareTo(passwordChange.getNewPasswordConfirm()) != 0)
          {
              bindingResult.addError(new ObjectError("passwordChange", "The new password you entered does not match on both input fields."));
              return "passwordChange";
          }
          String hashedPassword = user.encryptPassword(passwordChange.getNewPassword());
          user.setPassword(hashedPassword);
          userService.updateUser(user);
          redirectAttributes.addFlashAttribute("successMessage", "Your password has been updated successfully.");
          return "redirect:/authorizedusers/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
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
