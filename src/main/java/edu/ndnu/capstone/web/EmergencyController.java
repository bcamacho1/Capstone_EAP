package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.Emergency;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/emergencys")
@Controller
@RooWebScaffold(path = "emergencys", formBackingObject = Emergency.class)
public class EmergencyController {
}
