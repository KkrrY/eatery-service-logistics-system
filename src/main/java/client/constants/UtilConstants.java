package client.constants;

import org.springframework.beans.factory.annotation.Value;

public class UtilConstants {
    public static final String RESOURCE_SERVER_URL = "http://localhost:8080/" ;

//    @Value("${jwt.header}")
    public static String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHENTICATION_RESPONSE = "authenticationResponse";

    public static final String TOKEN_ATTRIBUTE = "token";
    public static final String USER_ATTRIBUTE = "user";
    public static final String ORDER_ATTRIBUTE = "order";

}
