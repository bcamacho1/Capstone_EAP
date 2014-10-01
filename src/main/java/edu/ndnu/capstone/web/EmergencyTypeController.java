package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyType;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/emergencytypes")
@Controller
@RooWebScaffold(path = "emergencytypes", formBackingObject = EmergencyType.class)
@GvNIXWebJQuery
public class EmergencyTypeController {
}
