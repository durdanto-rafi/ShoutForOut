package bd.com.tigercricket.json;


import bd.com.tigercricket.extras.MyApplication;
import static bd.com.tigercricket.extras.UrlEndpoints.URL_MATCH;

/**
 * Created by Windows on 02-03-2015.
 */
public class Endpoints {

    public static String getRequestUrlMatch(int limit) {

        return URL_MATCH;
               /* + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;*/
    }
}
