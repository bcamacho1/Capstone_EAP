package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyStatus;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/emergencystatuses")
@Controller
@RooWebScaffold(path = "emergencystatuses", formBackingObject = EmergencyStatus.class)
@GvNIXWebJQuery
public class EmergencyStatusController {
}
