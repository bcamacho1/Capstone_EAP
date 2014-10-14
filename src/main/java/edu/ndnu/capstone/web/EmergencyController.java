package edu.ndnu.capstone.web;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import edu.ndnu.capstone.domain.Emergency;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.gvnix.addon.web.mvc.jquery.GvNIXWebJQuery;

@RequestMapping("/emergencys")
@Controller
@RooWebScaffold(path = "emergencys", formBackingObject = Emergency.class)
@GvNIXWebJQuery
public class EmergencyController 
{
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
}
