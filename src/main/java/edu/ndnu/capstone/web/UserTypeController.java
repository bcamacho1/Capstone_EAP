package edu.ndnu.capstone.web;
import edu.ndnu.capstone.domain.UserType;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/usertypes")
@Controller
@RooWebScaffold(path = "usertypes", formBackingObject = UserType.class)
@GvNIXWebJQuery
public class UserTypeController {
}
