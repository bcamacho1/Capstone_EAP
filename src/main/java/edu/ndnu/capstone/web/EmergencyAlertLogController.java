package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.AuthorizedUserService;
import edu.ndnu.capstone.domain.EmergencyAlertLog;
import edu.ndnu.capstone.domain.EmergencyAlertLogService;
import edu.ndnu.capstone.domain.EmergencyMessageService;
import edu.ndnu.capstone.domain.EmergencyService;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.joda.time.format.DateTimeFormat;

@RequestMapping("/emergencyalertlogs")
@Controller
@RooWebScaffold(path = "emergencyalertlogs", formBackingObject = EmergencyAlertLog.class)
@GvNIXWebJQuery
public class EmergencyAlertLogController {

    @Autowired
    EmergencyAlertLogService emergencyAlertLogService;

    @Autowired
    EmergencyService emergencyService;

    @Autowired
    EmergencyMessageService emergencyMessageService;

    @Autowired
    AuthorizedUserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid EmergencyAlertLog emergencyAlertLog, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyAlertLog);
            return "emergencyalertlogs/create";
        }
        uiModel.asMap().clear();
        emergencyAlertLogService.saveEmergencyAlertLog(emergencyAlertLog);
        redirectAttributes.addFlashAttribute("successMessage", "The emergency log has been created successfully.");
        return "redirect:/emergencyalertlogs/" + encodeUrlPathSegment(emergencyAlertLog.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new EmergencyAlertLog());
        return "emergencyalertlogs/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("emergencyalertlog", emergencyAlertLogService.findEmergencyAlertLog(id));
        uiModel.addAttribute("itemId", id);
        return "emergencyalertlogs/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("emergencyalertlogs", emergencyAlertLogService.findEmergencyAlertLogEntries(firstResult, sizeNo));
            float nrOfPages = (float) emergencyAlertLogService.countAllEmergencyAlertLogs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("emergencyalertlogs", emergencyAlertLogService.findAllEmergencyAlertLogs());
        }
        addDateTimeFormatPatterns(uiModel);
        return "emergencyalertlogs/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid EmergencyAlertLog emergencyAlertLog, BindingResult bindingResult, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergencyAlertLog);
            return "emergencyalertlogs/update";
        }
        uiModel.asMap().clear();
        emergencyAlertLogService.updateEmergencyAlertLog(emergencyAlertLog);
        redirectAttributes.addFlashAttribute("successMessage", "The emergency log has been updated successfully.");
        return "redirect:/emergencyalertlogs/" + encodeUrlPathSegment(emergencyAlertLog.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, emergencyAlertLogService.findEmergencyAlertLog(id));
        return "emergencyalertlogs/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        try {
            EmergencyAlertLog emergencyAlertLog = emergencyAlertLogService.findEmergencyAlertLog(id);
            emergencyAlertLogService.deleteEmergencyAlertLog(emergencyAlertLog);
            uiModel.asMap().clear();
            uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
            uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
            redirectAttributes.addFlashAttribute("successMessage", "The alert log was deleted successfully.");
            return "redirect:/emergencyalertlogs";
        }
        catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred. The alert log cannot be deleted.");
            return "redirect:/emergencyalertlogs/" + encodeUrlPathSegment(id.toString(), httpServletRequest);
        }
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("emergencyAlertLog_ts_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, EmergencyAlertLog emergencyAlertLog) {
        uiModel.addAttribute("emergencyAlertLog", emergencyAlertLog);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("emergencies", emergencyService.findAllEmergencies());
        uiModel.addAttribute("emergencymessages", emergencyMessageService.findAllEmergencyMessages());
        uiModel.addAttribute("authorizedusers", userService.findAllUsers());
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
