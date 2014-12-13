package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.EmergencyType;
import edu.ndnu.capstone.domain.EmergencyTypeService;

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

@RequestMapping("/emergencytypes")
@Controller
@RooWebScaffold(path = "emergencytypes", formBackingObject = EmergencyType.class)
@GvNIXWebJQuery
public class EmergencyTypeController {

    @Autowired
    EmergencyTypeService emergencyTypeService;

    @Autowired
    EmergencyService emergencyService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid EmergencyType emergencyType, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyType);
            return "emergencytypes/create";
        }
        uiModel.asMap().clear();
        emergencyTypeService.saveEmergencyType(emergencyType);
        redirectAttributes.addFlashAttribute("successMessage", "The emergency type has been created successfully.");
        return "redirect:/emergencytypes/" + encodeUrlPathSegment(emergencyType.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new EmergencyType());
        return "emergencytypes/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("emergencytype", emergencyTypeService.findEmergencyType(id));
        uiModel.addAttribute("itemId", id);
        return "emergencytypes/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("emergencytypes", EmergencyType.findEmergencyTypeEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) emergencyTypeService.countAllEmergencyTypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("emergencytypes", EmergencyType.findAllEmergencyTypes(sortFieldName, sortOrder));
        }
        return "emergencytypes/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid EmergencyType emergencyType, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyType);
            return "emergencytypes/update";
        }
        uiModel.asMap().clear();
        emergencyTypeService.updateEmergencyType(emergencyType);
        redirectAttributes.addFlashAttribute("successMessage", "The emergency type has been updated successfully.");
        return "redirect:/emergencytypes/" + encodeUrlPathSegment(emergencyType.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, emergencyTypeService.findEmergencyType(id));
        return "emergencytypes/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        EmergencyType emergencyType = emergencyTypeService.findEmergencyType(id);
        emergencyTypeService.deleteEmergencyType(emergencyType);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/emergencytypes";
    }

    void populateEditForm(Model uiModel, EmergencyType emergencyType) {
        uiModel.addAttribute("emergencyType", emergencyType);
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
