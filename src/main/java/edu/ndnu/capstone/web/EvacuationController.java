package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.Evacuation;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/evacuations")
@Controller
@RooWebScaffold(path = "evacuations", formBackingObject = Evacuation.class)
@GvNIXWebJQuery
public class EvacuationController {
}
