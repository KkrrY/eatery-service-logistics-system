package constants;

public class PathConstants {
    public static final String API_V1 = "/api/v1";
    public static final String ORDER = "/order";
    public static final String ORDER_ID = "/{orderId}";
    public static final String ORDERS = "/orders";
    public static final String ORDERS_CURRENT = ORDERS + "/current";
    public static final String RECENT_ORDERS = "/recent-orders";
    public static final String CANCEL_ORDER = "/cancel-order" + ORDER_ID;
    public static final String INGREDIENT = "/ingredient";
    public static final String INGREDIENTS = "/ingredients";
    public static final String DISH = "/dish";
    public static final String DISHES = "/dishes";
    public static final String DISH_ID = "/{dishId}";
    public static final String USER = "/user";
    public static final String GRAPHQL = "/graphql";

    public static final String API_V1_ADMIN = API_V1 + "/admin";
    public static final String API_V1_AUTH = API_V1 + "/auth";
    public static final String API_V1_ORDER = API_V1 + ORDER;
    public static final String API_V1_INGREDIENT = API_V1 + INGREDIENT;
    public static final String API_V1_DISH = API_V1 + DISH;
    public static final String API_V1_REGISTRATION = API_V1 + "/registration";

    public static final String REDIRECT = "redirect:";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String REDIRECT_LOGOUT = REDIRECT + "/login?logout" ;
    public static final String FORGOT_EMAIL = "/forgot/{email}";
    public static final String RESET = "/reset";
    public static final String CODE = "/{code}";
    public static final String RESET_CODE = RESET + CODE;
    public static final String ACTIVATE_CODE = "/activate" + CODE;
    public static final String EDIT_PASSWORD = "/edit/password";

    public static final String REDIRECT_RECENT_ORDERS = REDIRECT + RECENT_ORDERS + ORDERS;
}
