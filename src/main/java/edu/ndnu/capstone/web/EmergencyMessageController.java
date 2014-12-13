package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyAlertLogService;
import edu.ndnu.capstone.domain.EmergencyMessage;
import edu.ndnu.capstone.domain.EmergencyMessageService;
import edu.ndnu.capstone.domain.EmergencyType;
import edu.ndnu.capstone.domain.EmergencyTypeService;
import edu.ndnu.capstone.domain.AuthorizedUser;
import edu.ndnu.capstone.domain.AuthorizedUserService;
import edu.ndnu.capstone.domain.UserType;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/emergencymessages")
@Controller
@RooWebScaffold(path = "emergencymessages", formBackingObject = EmergencyMessage.class)
@GvNIXWebJQuery
public class EmergencyMessageController {

    @Autowired
    EmergencyMessageService emergencyMessageService;

    @Autowired
    EmergencyAlertLogService emergencyAlertLogService;

    @Autowired
    EmergencyTypeService emergencyTypeService;

    @Autowired
    AuthorizedUserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid EmergencyMessage emergencyMessage, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateCreateForm(uiModel, emergencyMessage);
            return "emergencymessages/create";
        }

        Integer type_id = emergencyMessage.getEmergencyTypeId().getId();
        // if they selected Null make sure we're not duplicating a message
        if (emergencyMessage.getUserId() == null)
        {
            EmergencyMessage existingMessage = emergencyMessageService.findDefaultEmergencyMessageByType(type_id);
            if (existingMessage != null)
            {
                bindingResult.addError(new ObjectError("emergencyMessage", "An existing default emergency message was found for this emergency type."));
                populateCreateForm(uiModel, emergencyMessage);
                return "emergencymessages/create";
            }
        }
        else
        {
            Integer user_id = emergencyMessage.getUserId().getId();
            EmergencyMessage existingMessage = emergencyMessageService.findEmergencyMessageByUserAndType(user_id, type_id);
            if (existingMessage != null)
            {
                bindingResult.addError(new ObjectError("emergencyMessage", "An existing emergency message was found for this user and emergency type."));
                populateCreateForm(uiModel, emergencyMessage);
                return "emergencymessages/create";
            }
        }
        
        uiModel.asMap().clear();
        emergencyMessageService.saveEmergencyMessage(emergencyMessage);
        redirectAttributes.addFlashAttribute("successMessage", "The emergency message has been created successfully.");
        return "redirect:/emergencymessages/" + encodeUrlPathSegment(emergencyMessage.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateCreateForm(uiModel, new EmergencyMessage());
        return "emergencymessages/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("emergencymessage", emergencyMessageService.findEmergencyMessage(id));
        uiModel.addAttribute("itemId", id);
        return "emergencymessages/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        AuthorizedUser user = AuthorizedUser.findUserByUsername(login);
        UserType type = user.getTypeId();

        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            if (type.getName().compareTo("Admin")==0)
            {
                uiModel.addAttribute("emergencymessages", emergencyMessageService.findEmergencyMessageEntries(firstResult, sizeNo));
                float nrOfPages = (float) emergencyMessageService.countAllEmergencyMessages() / sizeNo;
                uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            }
            else
            {
                uiModel.addAttribute("emergencymessages", emergencyMessageService.findEmergencyMessageEntriesByUser(user.getId(), firstResult, sizeNo));
                float nrOfPages = (float) emergencyMessageService.countAllEmergencyMessagesByUser(user.getId()) / sizeNo;
                uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
            }

        } else {
            if (type.getName().compareTo("Admin")==0)
            {
                uiModel.addAttribute("emergencymessages", emergencyMessageService.findAllEmergencyMessages());
            }
            else
            {
                uiModel.addAttribute("emergencymessages", emergencyMessageService.findEmergencyMessageByUser(user.getId()));
            }
        }
        return "emergencymessages/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid EmergencyMessage emergencyMessage, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyMessage);
            return "emergencymessages/update";
        }
        
        uiModel.asMap().clear();
        emergencyMessageService.updateEmergencyMessage(emergencyMessage);
        redirectAttributes.addFlashAttribute("successMessage", "The emergency message has been updated successfully.");
        return "redirect:/emergencymessages/" + encodeUrlPathSegment(emergencyMessage.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, emergencyMessageService.findEmergencyMessage(id));
        return "emergencymessages/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        EmergencyMessage emergencyMessage = emergencyMessageService.findEmergencyMessage(id);
        emergencyMessageService.deleteEmergencyMessage(emergencyMessage);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/emergencymessages";
    }

    void populateEditForm(Model uiModel, EmergencyMessage emergencyMessage) {
        AuthorizedUser user = new AuthorizedUser();
        if (emergencyMessage.getUserId() == null)
            user.setId(0);
        else 
            user = AuthorizedUser.findUser(emergencyMessage.getUserId().getId());
        
        List<AuthorizedUser> currentUserList = new ArrayList<AuthorizedUser>();
        currentUserList.add(user);
        uiModel.addAttribute("authorizedusers", currentUserList);
            
        EmergencyType type = emergencyTypeService.findEmergencyType(emergencyMessage.getId());
        List<EmergencyType> currentTypeList = new ArrayList<EmergencyType>();
        currentTypeList.add(type);
        uiModel.addAttribute("emergencytypes", currentTypeList);
        uiModel.addAttribute("emergencyMessage", emergencyMessage);
    }
    
    void populateCreateForm(Model uiModel, EmergencyMessage emergencyMessage) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        AuthorizedUser user = AuthorizedUser.findUserByUsername(login);
        UserType type = user.getTypeId();

        if (type.getName().compareTo("Admin")==0)
        {
            List<AuthorizedUser> currentUserList = userService.findAllUsers();
            AuthorizedUser emptyUser = new AuthorizedUser();
            emptyUser.setId(0);
            currentUserList.add(0, emptyUser);
            uiModel.addAttribute("authorizedusers", currentUserList);
        }
        else
        {
            List<AuthorizedUser> currentUserList = new ArrayList<AuthorizedUser>();
            currentUserList.add(user);
            uiModel.addAttribute("authorizedusers", currentUserList);
        }

        uiModel.addAttribute("emergencyMessage", emergencyMessage);
        uiModel.addAttribute("emergencyalertlogs", emergencyAlertLogService.findAllEmergencyAlertLogs());
        uiModel.addAttribute("emergencytypes", emergencyTypeService.findAllEmergencyTypes());
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
