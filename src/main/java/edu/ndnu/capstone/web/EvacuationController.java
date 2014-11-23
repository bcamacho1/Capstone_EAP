package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.Evacuation;
import edu.ndnu.capstone.domain.EvacuationService;
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
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/evacuations")
@Controller
@RooWebScaffold(path = "evacuations", formBackingObject = Evacuation.class)
@GvNIXWebJQuery
public class EvacuationController {

    @Autowired
    EvacuationService evacuationService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Evacuation evacuation, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, evacuation);
            return "evacuations/create";
        }
        uiModel.asMap().clear();
        evacuationService.saveEvacuation(evacuation);
        return "redirect:/evacuations/" + encodeUrlPathSegment(evacuation.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Evacuation());
        return "evacuations/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        uiModel.addAttribute("evacuation", evacuationService.findEvacuation(id));
        uiModel.addAttribute("itemId", id);
        return "evacuations/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("evacuations", Evacuation.findEvacuationEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) evacuationService.countAllEvacuations() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("evacuations", Evacuation.findAllEvacuations(sortFieldName, sortOrder));
        }
        return "evacuations/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Evacuation evacuation, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, evacuation);
            return "evacuations/update";
        }
        uiModel.asMap().clear();
        evacuationService.updateEvacuation(evacuation);
        return "redirect:/evacuations/" + encodeUrlPathSegment(evacuation.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, evacuationService.findEvacuation(id));
        return "evacuations/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Evacuation evacuation = evacuationService.findEvacuation(id);
        evacuationService.deleteEvacuation(evacuation);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/evacuations";
    }

    void populateEditForm(Model uiModel, Evacuation evacuation) {
        uiModel.addAttribute("evacuation", evacuation);
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
