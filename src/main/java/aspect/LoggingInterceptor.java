package aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static constants.SecurityConstants.AUTHORIZATION_HEADER;
import static constants.LoggingConstants.*;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(ANSI_GREEN + "New request occurrence");
        Object authenticationPrincipal = request.getAttribute("authenticationPrincipal");

        logger.info("REQUEST IP ADDRESS: " + getClientIpAddress(request) );

        logger.info((String) request.getAttribute(TOKEN_STATUS));

        try {
            logger.info("TOKEN: "+ request.getHeader(AUTHORIZATION_HEADER));
        } catch (NullPointerException ignored) {}

        if (authenticationPrincipal != null) {
            logger.info("Authentication principal: " + authenticationPrincipal);
        }


        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Class<?> controllerClass = handlerMethod.getBeanType();

            logger.info("Controller: " + controllerClass.getName());
            logger.info("Method: " + method.getName());


            logger.info("Request URL: " + request.getRequestURI());
            logger.info("Request Method: " + request.getMethod());
            logger.info("Request Parameters: " + getRequestParametersAsString(request.getParameterMap()) );
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Map<String, Object> modelMap = modelAndView.getModel();
            String modelMapString = modelMap.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining(", "));
            logger.info("ModelAndView: " + modelMapString);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("Response Status: " + response.getStatus());

        if (ex != null) {
            logger.warning("Exception occurred: " + ex.getMessage());
        }
    }


    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        // In case of multiple IP addresses, get the first one
        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0];
        }
        return ipAddress;
    }

    private String getRequestParametersAsString(Map<String, String[]> parameterMap) {
        return parameterMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
                .collect(Collectors.joining(", "));
    }
}
