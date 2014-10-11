// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.web;

import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.EmergencyStatus;
import edu.ndnu.capstone.domain.EmergencyStatusService;
import edu.ndnu.capstone.web.EmergencyStatusController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect EmergencyStatusController_Roo_Controller {
    
    @Autowired
    EmergencyStatusService EmergencyStatusController.emergencyStatusService;
    
    @Autowired
    EmergencyService EmergencyStatusController.emergencyService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String EmergencyStatusController.create(@Valid EmergencyStatus emergencyStatus, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyStatus);
            return "emergencystatuses/create";
        }
        uiModel.asMap().clear();
        emergencyStatusService.saveEmergencyStatus(emergencyStatus);
        return "redirect:/emergencystatuses/" + encodeUrlPathSegment(emergencyStatus.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String EmergencyStatusController.createForm(Model uiModel) {
        populateEditForm(uiModel, new EmergencyStatus());
        return "emergencystatuses/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String EmergencyStatusController.show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("emergencystatus", emergencyStatusService.findEmergencyStatus(id));
        uiModel.addAttribute("itemId", id);
        return "emergencystatuses/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String EmergencyStatusController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("emergencystatuses", emergencyStatusService.findEmergencyStatusEntries(firstResult, sizeNo));
            float nrOfPages = (float) emergencyStatusService.countAllEmergencyStatuses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("emergencystatuses", emergencyStatusService.findAllEmergencyStatuses());
        }
        return "emergencystatuses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String EmergencyStatusController.update(@Valid EmergencyStatus emergencyStatus, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyStatus);
            return "emergencystatuses/update";
        }
        uiModel.asMap().clear();
        emergencyStatusService.updateEmergencyStatus(emergencyStatus);
        return "redirect:/emergencystatuses/" + encodeUrlPathSegment(emergencyStatus.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String EmergencyStatusController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, emergencyStatusService.findEmergencyStatus(id));
        return "emergencystatuses/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String EmergencyStatusController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        EmergencyStatus emergencyStatus = emergencyStatusService.findEmergencyStatus(id);
        emergencyStatusService.deleteEmergencyStatus(emergencyStatus);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/emergencystatuses";
    }
    
    void EmergencyStatusController.populateEditForm(Model uiModel, EmergencyStatus emergencyStatus) {
        uiModel.addAttribute("emergencyStatus", emergencyStatus);
        uiModel.addAttribute("emergencys", emergencyService.findAllEmergencys());
    }
    
    String EmergencyStatusController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
