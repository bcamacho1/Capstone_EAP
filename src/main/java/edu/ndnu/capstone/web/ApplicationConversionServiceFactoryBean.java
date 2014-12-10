package edu.ndnu.capstone.web;

import edu.ndnu.capstone.domain.Emergency;
import edu.ndnu.capstone.domain.EmergencyAlertLog;
import edu.ndnu.capstone.domain.EmergencyAlertLogService;
import edu.ndnu.capstone.domain.EmergencyMessage;
import edu.ndnu.capstone.domain.EmergencyMessageService;
import edu.ndnu.capstone.domain.EmergencyService;
import edu.ndnu.capstone.domain.EmergencyStatus;
import edu.ndnu.capstone.domain.EmergencyStatusService;
import edu.ndnu.capstone.domain.EmergencyType;
import edu.ndnu.capstone.domain.EmergencyTypeService;
import edu.ndnu.capstone.domain.Location;
import edu.ndnu.capstone.domain.LocationService;
import edu.ndnu.capstone.domain.User;
import edu.ndnu.capstone.domain.UserService;
import edu.ndnu.capstone.domain.AuthorizedUser;
import edu.ndnu.capstone.domain.AuthorizedUserService;
import edu.ndnu.capstone.domain.UserType;
import edu.ndnu.capstone.domain.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Override
    protected void installFormatters(FormatterRegistry registry) {
        super.installFormatters(registry);
        // Register application converters and formatters
    }

    @Autowired
    EmergencyService emergencyService;

    @Autowired
    EmergencyAlertLogService emergencyAlertLogService;

    @Autowired
    EmergencyMessageService emergencyMessageService;

    @Autowired
    EmergencyStatusService emergencyStatusService;

    @Autowired
    EmergencyTypeService emergencyTypeService;

    @Autowired
    LocationService locationService;

    @Autowired
    UserService userService;
    
    @Autowired
    AuthorizedUserService authorizedUserService;

    @Autowired
    UserTypeService userTypeService;

    public Converter<Emergency, String> getEmergencyToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.Emergency, java.lang.String>() {
            public String convert(Emergency emergency) {
                return new StringBuilder().append(emergency.getLocationId().getName()).append(", ").append(emergency.getTypeId().getName()).append(", ").append(emergency.getDescription()).toString();
            }
        };
    }

    public Converter<Integer, Emergency> getIdToEmergencyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.Emergency>() {
            public edu.ndnu.capstone.domain.Emergency convert(java.lang.Integer id) {
                return emergencyService.findEmergency(id);
            }
        };
    }

    public Converter<String, Emergency> getStringToEmergencyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.Emergency>() {
            public edu.ndnu.capstone.domain.Emergency convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Emergency.class);
            }
        };
    }

    public Converter<EmergencyAlertLog, String> getEmergencyAlertLogToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.EmergencyAlertLog, java.lang.String>() {
            public String convert(EmergencyAlertLog emergencyAlertLog) {
                return new StringBuilder().append(emergencyAlertLog.getTs()).append(' ').append(emergencyAlertLog.getSent()).toString();
            }
        };
    }

    public Converter<Integer, EmergencyAlertLog> getIdToEmergencyAlertLogConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.EmergencyAlertLog>() {
            public edu.ndnu.capstone.domain.EmergencyAlertLog convert(java.lang.Integer id) {
                return emergencyAlertLogService.findEmergencyAlertLog(id);
            }
        };
    }

    public Converter<String, EmergencyAlertLog> getStringToEmergencyAlertLogConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.EmergencyAlertLog>() {
            public edu.ndnu.capstone.domain.EmergencyAlertLog convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), EmergencyAlertLog.class);
            }
        };
    }

    public Converter<EmergencyMessage, String> getEmergencyMessageToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.EmergencyMessage, java.lang.String>() {
            public String convert(EmergencyMessage emergencyMessage) {
                return new StringBuilder().append(emergencyMessage.getMessage()).toString();
            }
        };
    }

    public Converter<Integer, EmergencyMessage> getIdToEmergencyMessageConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.EmergencyMessage>() {
            public edu.ndnu.capstone.domain.EmergencyMessage convert(java.lang.Integer id) {
                return emergencyMessageService.findEmergencyMessage(id);
            }
        };
    }

    public Converter<String, EmergencyMessage> getStringToEmergencyMessageConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.EmergencyMessage>() {
            public edu.ndnu.capstone.domain.EmergencyMessage convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), EmergencyMessage.class);
            }
        };
    }

    public Converter<EmergencyStatus, String> getEmergencyStatusToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.EmergencyStatus, java.lang.String>() {
            public String convert(EmergencyStatus emergencyStatus) {
                return new StringBuilder().append(emergencyStatus.getName()).toString();
            }
        };
    }

    public Converter<Integer, EmergencyStatus> getIdToEmergencyStatusConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.EmergencyStatus>() {
            public edu.ndnu.capstone.domain.EmergencyStatus convert(java.lang.Integer id) {
                return emergencyStatusService.findEmergencyStatus(id);
            }
        };
    }

    public Converter<String, EmergencyStatus> getStringToEmergencyStatusConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.EmergencyStatus>() {
            public edu.ndnu.capstone.domain.EmergencyStatus convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), EmergencyStatus.class);
            }
        };
    }

    public Converter<EmergencyType, String> getEmergencyTypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.EmergencyType, java.lang.String>() {
            public String convert(EmergencyType emergencyType) {
                return new StringBuilder().append(emergencyType.getName()).toString();
            }
        };
    }

    public Converter<Integer, EmergencyType> getIdToEmergencyTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.EmergencyType>() {
            public edu.ndnu.capstone.domain.EmergencyType convert(java.lang.Integer id) {
                return emergencyTypeService.findEmergencyType(id);
            }
        };
    }

    public Converter<String, EmergencyType> getStringToEmergencyTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.EmergencyType>() {
            public edu.ndnu.capstone.domain.EmergencyType convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), EmergencyType.class);
            }
        };
    }

    public Converter<Location, String> getLocationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.Location, java.lang.String>() {
            public String convert(Location location) {
                return new StringBuilder().append(location.getName()).append(", ").append(location.getAddress()).append(", ").append(location.getCity()).append(", ").append(location.getState()).toString();
            }
        };
    }

    public Converter<Integer, Location> getIdToLocationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.Location>() {
            public edu.ndnu.capstone.domain.Location convert(java.lang.Integer id) {
                return locationService.findLocation(id);
            }
        };
    }

    public Converter<String, Location> getStringToLocationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.Location>() {
            public edu.ndnu.capstone.domain.Location convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), Location.class);
            }
        };
    }

    public Converter<User, String> getUserToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.User, java.lang.String>() {
            public String convert(User user) {
                return new StringBuilder().append(user.getName()).append(" - ").append(user.getEmail()).toString();
            }
        };
    }

    public Converter<Integer, User> getIdToUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.User>() {
            public edu.ndnu.capstone.domain.User convert(java.lang.Integer id) {
                return userService.findUser(id);
            }
        };
    }

    public Converter<String, User> getStringToUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.User>() {
            public edu.ndnu.capstone.domain.User convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), User.class);
            }
        };
    }

    public Converter<UserType, String> getUserTypeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.UserType, java.lang.String>() {
            public String convert(UserType userType) {
                return new StringBuilder().append(userType.getName()).toString();
            }
        };
    }

    public Converter<Integer, UserType> getIdToUserTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.UserType>() {
            public edu.ndnu.capstone.domain.UserType convert(java.lang.Integer id) {
                return userTypeService.findUserType(id);
            }
        };
    }

    public Converter<String, UserType> getStringToUserTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.UserType>() {
            public edu.ndnu.capstone.domain.UserType convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), UserType.class);
            }
        };
    }
    
    public Converter<AuthorizedUser, String> getAuthorizedUserToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.ndnu.capstone.domain.AuthorizedUser, java.lang.String>() {
            public String convert(AuthorizedUser user) {
                return new StringBuilder().append(user.getName()).append(" - ").append(user.getEmail()).toString();
            }
        };
    }

    public Converter<Integer, AuthorizedUser> getIdToAuthorizedUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Integer, edu.ndnu.capstone.domain.AuthorizedUser>() {
            public edu.ndnu.capstone.domain.AuthorizedUser convert(java.lang.Integer id) {
                return authorizedUserService.findUser(id);
            }
        };
    }

    public Converter<String, AuthorizedUser> getStringToAuthorizedUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.ndnu.capstone.domain.AuthorizedUser>() {
            public edu.ndnu.capstone.domain.AuthorizedUser convert(String id) {
                return getObject().convert(getObject().convert(id, Integer.class), AuthorizedUser.class);
            }
        };
    }

    public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getEmergencyToStringConverter());
        registry.addConverter(getIdToEmergencyConverter());
        registry.addConverter(getStringToEmergencyConverter());
        registry.addConverter(getEmergencyAlertLogToStringConverter());
        registry.addConverter(getIdToEmergencyAlertLogConverter());
        registry.addConverter(getStringToEmergencyAlertLogConverter());
        registry.addConverter(getEmergencyMessageToStringConverter());
        registry.addConverter(getIdToEmergencyMessageConverter());
        registry.addConverter(getStringToEmergencyMessageConverter());
        registry.addConverter(getEmergencyStatusToStringConverter());
        registry.addConverter(getIdToEmergencyStatusConverter());
        registry.addConverter(getStringToEmergencyStatusConverter());
        registry.addConverter(getEmergencyTypeToStringConverter());
        registry.addConverter(getIdToEmergencyTypeConverter());
        registry.addConverter(getStringToEmergencyTypeConverter());
        registry.addConverter(getLocationToStringConverter());
        registry.addConverter(getIdToLocationConverter());
        registry.addConverter(getStringToLocationConverter());
        registry.addConverter(getUserToStringConverter());
        registry.addConverter(getIdToUserConverter());
        registry.addConverter(getStringToUserConverter());
        registry.addConverter(getAuthorizedUserToStringConverter());
        registry.addConverter(getIdToAuthorizedUserConverter());
        registry.addConverter(getStringToAuthorizedUserConverter());
        registry.addConverter(getUserTypeToStringConverter());
        registry.addConverter(getIdToUserTypeConverter());
        registry.addConverter(getStringToUserTypeConverter());
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
