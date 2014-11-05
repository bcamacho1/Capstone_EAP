package edu.ndnu.capstone.web;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import edu.ndnu.capstone.domain.Emergency;
import edu.ndnu.capstone.domain.EmergencyAlertLogService;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.EmergencyStatusService;
import edu.ndnu.capstone.domain.EmergencyTypeService;
import edu.ndnu.capstone.domain.LocationService;
import edu.ndnu.capstone.domain.UserService;
import edu.ndnu.capstone.domain.User;
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
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;
import org.joda.time.format.DateTimeFormat;

@RequestMapping("/emergencys")
@Controller
@RooWebScaffold(path = "emergencys", formBackingObject = Emergency.class)
@GvNIXWebJQuery
public class EmergencyController {

    @Autowired
    EmergencyService emergencyService;

    @Autowired
    EmergencyAlertLogService emergencyAlertLogService;

    @Autowired
    EmergencyStatusService emergencyStatusService;

    @Autowired
    EmergencyTypeService emergencyTypeService;

    @Autowired
    LocationService locationService;

    @Autowired
    UserService userService;
    
    // The url to get to this method is /emergencys/alert
    // This gets appended to the RequestMapping annotation above
    //@RequestMapping("/alert")
    //@RequestMapping(value="/alert/{param}", method=RequestMethod.GET)
    @RequestMapping(value="/alert/{param}")
    public String sendEmailAlert(@PathVariable("param") int param) 
    {
        System.out.printf("%d\n", param);
        System.out.println("You sent an email alert!");
        
        final String username = "capstone.eap.ndnu@gmail.com";
        final String password = "capstone_eap";
        final String from = "capstone.eap.ndnu@gmail.com";
        String to = "smantegani@student.ndnu.edu";
 
        new User();
        EntityManager em = User.entityManager();
        // User is the class name in the query, not lower case "user" which is the actual name of the table
        @SuppressWarnings("unchecked")
        List<String> rs = em.createQuery("SELECT u.email FROM User u where u.active = 1").getResultList();
        
        Iterator<String> iterator = rs.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
 
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
        
        try
        {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Emergency Alert");
            message.setText("New Emergency: \nid=" + param);
            //message.setContent(text, "text/html");
    
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }
        catch (MessagingException mex) 
        {
            mex.printStackTrace();
        }
        
        // return the name of one of the mappings in the view.xml file
        // we will navigate to this page
        // examples like campusMap, index, resourceNotFound
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Emergency emergency, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergency);
            return "emergencys/create";
        }
        uiModel.asMap().clear();
        emergencyService.saveEmergency(emergency);
        return "redirect:/emergencys/" + encodeUrlPathSegment(emergency.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Emergency());
        return "emergencys/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("emergency", emergencyService.findEmergency(id));
        uiModel.addAttribute("itemId", id);
        return "emergencys/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("emergencys", emergencyService.findEmergencyEntries(firstResult, sizeNo));
            float nrOfPages = (float) emergencyService.countAllEmergencys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("emergencys", emergencyService.findAllEmergencys());
        }
        addDateTimeFormatPatterns(uiModel);
        return "emergencys/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Emergency emergency, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, emergency);
            return "emergencys/update";
        }
        uiModel.asMap().clear();
        emergencyService.updateEmergency(emergency);
        return "redirect:/emergencys/" + encodeUrlPathSegment(emergency.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, emergencyService.findEmergency(id));
        return "emergencys/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Emergency emergency = emergencyService.findEmergency(id);
        emergencyService.deleteEmergency(emergency);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/emergencys";
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("emergency_created_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }

    void populateEditForm(Model uiModel, Emergency emergency) {
        uiModel.addAttribute("emergency", emergency);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("emergencyalertlogs", emergencyAlertLogService.findAllEmergencyAlertLogs());
        uiModel.addAttribute("emergencystatuses", emergencyStatusService.findAllEmergencyStatuses());
        uiModel.addAttribute("emergencytypes", emergencyTypeService.findAllEmergencyTypes());
        uiModel.addAttribute("locations", locationService.findAllLocations());
        uiModel.addAttribute("users", userService.findAllUsers());
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
