package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.Location;
import edu.ndnu.capstone.domain.LocationService;
import edu.ndnu.capstone.domain.LocationState;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/locations")
@Controller
@RooWebScaffold(path = "locations", formBackingObject = Location.class)
@GvNIXWebJQuery
public class LocationController {

    @Autowired
    LocationService locationService;

    @Autowired
    EmergencyService emergencyService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Location location, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
        	java.util.List<ObjectError> list=bindingResult.getAllErrors();
        	for(ObjectError obj:list)
        	{
        		System.out.println("objname"+obj.getObjectName()+";"+obj.getCode()+";"+obj.getDefaultMessage());
        		if(obj instanceof FieldError)
        		{
        			System.out.println(((FieldError)obj).getField());
        		}
        	}
            populateEditForm(uiModel, location);
            return "locations/create";
        }
        
        try {
        	locationService.saveLocation(location);
        	uiModel.asMap().clear();  
	        return "redirect:/locations/" + encodeUrlPathSegment(location.getId().toString(), httpServletRequest);
        } catch (Exception e) {
        	bindingResult.addError(new FieldError("location", "state", "Location is already in the database"));
        	populateEditForm(uiModel, location);
            return "locations/create";
        }
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Location());
        return "locations/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("location", locationService.findLocation(id));
        uiModel.addAttribute("itemId", id);
        return "locations/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("locations", Location.findLocationEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) locationService.countAllLocations() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("locations", Location.findAllLocations(sortFieldName, sortOrder));
        }
        return "locations/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Location location, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, location);
            return "locations/update";
        }
        uiModel.asMap().clear();
        locationService.updateLocation(location);
        return "redirect:/locations/" + encodeUrlPathSegment(location.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, locationService.findLocation(id));
        return "locations/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Location location = locationService.findLocation(id);
        locationService.deleteLocation(location);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/locations";
    }

    void populateEditForm(Model uiModel, Location location) {
        uiModel.addAttribute("location", location);
        uiModel.addAttribute("emergencies", emergencyService.findAllEmergencies());
        uiModel.addAttribute("locationstates", LocationState.findState());
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
