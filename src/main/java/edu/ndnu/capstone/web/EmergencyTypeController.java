package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.EmergencyType;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/emergencytypes")
@Controller
@RooWebScaffold(path = "emergencytypes", formBackingObject = EmergencyType.class)
public class EmergencyTypeController {
}
