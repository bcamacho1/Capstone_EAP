package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.EmergencyStatus;
import edu.ndnu.capstone.domain.EmergencyStatusService;

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

@RequestMapping("/emergencystatuses")
@Controller
@RooWebScaffold(path = "emergencystatuses", formBackingObject = EmergencyStatus.class)
@GvNIXWebJQuery
public class EmergencyStatusController {

    @Autowired
    EmergencyStatusService emergencyStatusService;

    @Autowired
    EmergencyService emergencyService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid EmergencyStatus emergencyStatus, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyStatus);
            return "emergencystatuses/create";
        }
        uiModel.asMap().clear();
        emergencyStatusService.saveEmergencyStatus(emergencyStatus);
        redirectAttributes.addFlashAttribute("successMessage", "The emergency status has been created successfully.");
        return "redirect:/emergencystatuses/" + encodeUrlPathSegment(emergencyStatus.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new EmergencyStatus());
        return "emergencystatuses/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("emergencystatus", emergencyStatusService.findEmergencyStatus(id));
        uiModel.addAttribute("itemId", id);
        return "emergencystatuses/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("emergencystatuses", EmergencyStatus.findEmergencyStatusEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) emergencyStatusService.countAllEmergencyStatuses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("emergencystatuses", EmergencyStatus.findAllEmergencyStatuses(sortFieldName, sortOrder));
        }
        return "emergencystatuses/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid EmergencyStatus emergencyStatus, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyStatus);
            return "emergencystatuses/update";
        }
        uiModel.asMap().clear();
        emergencyStatusService.updateEmergencyStatus(emergencyStatus);
        redirectAttributes.addFlashAttribute("successMessage", "The emergency status has been updated successfully.");
        return "redirect:/emergencystatuses/" + encodeUrlPathSegment(emergencyStatus.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, emergencyStatusService.findEmergencyStatus(id));
        return "emergencystatuses/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        EmergencyStatus emergencyStatus = emergencyStatusService.findEmergencyStatus(id);
        emergencyStatusService.deleteEmergencyStatus(emergencyStatus);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/emergencystatuses";
    }

    void populateEditForm(Model uiModel, EmergencyStatus emergencyStatus) {
        uiModel.addAttribute("emergencyStatus", emergencyStatus);
        uiModel.addAttribute("emergencies", emergencyService.findAllEmergencies());
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
