package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.Emergency;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/emergencys")
@Controller
@RooWebScaffold(path = "emergencys", formBackingObject = Emergency.class)
@GvNIXWebJQuery
public class EmergencyController {
}
