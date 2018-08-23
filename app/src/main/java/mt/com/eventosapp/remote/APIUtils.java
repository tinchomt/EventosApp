package mt.com.eventosapp.remote;

/**
 * Created by martin on 10/08/18.
 */

public class APIUtils {

    private APIUtils(){
    };

    //public static final String API_URL = "http://18.219.113.167:8080/user-portal-1.0/api/";
    public static final String API_URL = "http://18.219.113.167:8080/eventos-1.0/api/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }
}
