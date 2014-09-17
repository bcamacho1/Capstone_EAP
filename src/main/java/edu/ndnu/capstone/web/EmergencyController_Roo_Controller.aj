// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.web;

import edu.ndnu.capstone.domain.Emergency;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.web.EmergencyController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect EmergencyController_Roo_Controller {
    
    @Autowired
    EmergencyService EmergencyController.emergencyService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String EmergencyController.create(@Valid Emergency emergency, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergency);
            return "emergencys/create";
        }
        uiModel.asMap().clear();
        emergencyService.saveEmergency(emergency);
        return "redirect:/emergencys/" + encodeUrlPathSegment(emergency.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String EmergencyController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Emergency());
        return "emergencys/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String EmergencyController.show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("emergency", emergencyService.findEmergency(id));
        uiModel.addAttribute("itemId", id);
        return "emergencys/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String EmergencyController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("emergencys", Emergency.findEmergencyEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) emergencyService.countAllEmergencys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("emergencys", Emergency.findAllEmergencys(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "emergencys/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String EmergencyController.update(@Valid Emergency emergency, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergency);
            return "emergencys/update";
        }
        uiModel.asMap().clear();
        emergencyService.updateEmergency(emergency);
        return "redirect:/emergencys/" + encodeUrlPathSegment(emergency.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String EmergencyController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, emergencyService.findEmergency(id));
        return "emergencys/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String EmergencyController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Emergency emergency = emergencyService.findEmergency(id);
        emergencyService.deleteEmergency(emergency);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/emergencys";
    }
    
    void EmergencyController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("emergency_created_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
    
    void EmergencyController.populateEditForm(Model uiModel, Emergency emergency) {
        uiModel.addAttribute("emergency", emergency);
        addDateTimeFormatPatterns(uiModel);
    }
    
    String EmergencyController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
