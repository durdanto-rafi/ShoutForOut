package bd.com.tigercricket.extras;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import bd.com.tigercricket.json.Endpoints;
import bd.com.tigercricket.json.Parser;
import bd.com.tigercricket.json.Requestor;
import bd.com.tigercricket.pojo.Match;


/**
 * Created by Windows on 02-03-2015.
 */
public class MatchUtils {
    public static ArrayList<Match> loadMatch(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMatchJSON(requestQueue, Endpoints.getRequestUrlMatch(30));
        ArrayList<Match> matchArrayList = Parser.parseMatchJson(response);
        //MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return matchArrayList;
    }

}
