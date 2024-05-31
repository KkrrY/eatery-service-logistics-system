package client.constants;

public class PathConstants {
    public static final String HOME = "/";
    public static final String BURGER_BUILDER_MAPPING = "/design";
    public static final String DISHES_CATALOGUE_MAPPING = "/dishes";
    public static final String CLIENT_LOGIN = "/authorization";
    public static final String CLIENT_REGISTRATION = "/register";
    public static final String ORDER_PROCESSING = "/orders";
    public static final String CURRENT_ORDER = "/current";
    public static final String ORDERS_HISTORY = "/orders";
    public static final String USER_PROFILE = "/profile";
    public static final String ORDER_CANCELLING = "/cancel-order/{orderId}";
    public static final String RECENT_ORDERS = "/recent-orders";
    public static final String LOGOUT = "/logouts";
    public static final String LOGIN_LOGOUT = "/login?logout";
    public static final String OAUTH2_REDIRECT = "/oauth2/redirect";

    public static final String[] CLIENT_ALLOWED_GUEST_USERS_RESOURCES = {
        HOME,
        CLIENT_LOGIN,
        CLIENT_REGISTRATION,
        BURGER_BUILDER_MAPPING,
        DISHES_CATALOGUE_MAPPING,
        ORDER_PROCESSING + "/**" ,
        LOGOUT
    };
    public static final String[] CLIENT_ALLOWED_AUTHORIZED_RESOURCES = {
        RECENT_ORDERS + "/**",
        USER_PROFILE,
        LOGIN_LOGOUT
    };
}
