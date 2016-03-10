package bd.com.tigercricket.json;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bd.com.tigercricket.extras.Constants;
import bd.com.tigercricket.logging.L;
import bd.com.tigercricket.pojo.Match;


import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_DATA;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_ID;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_TEAM_1;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_TEAM_2;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_TEAM_1_FLAG;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_TEAM_2_FLAG;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_SITUATION;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_SHOUTS;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_OUTS;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_NOT_OUTS;

/**
 * Created by Windows on 02-03-2015.
 */
public class Parser {

    public static ArrayList<Match> parseMatchJson(JSONObject response) {
        ArrayList<Match> matchArrayList = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                JSONObject currentMatch = response.getJSONObject(KEY_DATA);

                int id = -1;
                String team_1 = Constants.NA;
                String team_2 = Constants.NA;
                String team_1_flag = Constants.NA;
                String team_2_flag = Constants.NA;
                String situation = Constants.NA;
                int shouts = -1;
                int outs = -1;
                int not_outs = -1;

                //get the id of the current latest news
                if (Utils.contains(currentMatch, KEY_ID)) {
                    id = currentMatch.getInt(KEY_ID);
                }

                if (Utils.contains(currentMatch, KEY_TEAM_1)) {
                    team_1 = currentMatch.getString(KEY_TEAM_1);
                }
                //get the image of the current latest news
                if (Utils.contains(currentMatch, KEY_TEAM_2)) {
                    team_2 = currentMatch.getString(KEY_TEAM_2);
                }
                //get the abstract of the current latest news
                if (Utils.contains(currentMatch, KEY_TEAM_1_FLAG)) {
                    team_1_flag = currentMatch.getString(KEY_TEAM_1_FLAG);
                }
                //get the author of the current latest news
                if (Utils.contains(currentMatch, KEY_TEAM_2_FLAG)) {
                    team_2_flag = currentMatch.getString(KEY_TEAM_2_FLAG);
                }
                //get the category of the current latest news
                if (Utils.contains(currentMatch, KEY_SITUATION)) {
                    situation = currentMatch.getString(KEY_SITUATION);
                }
                //get the category of the current latest news
                if (Utils.contains(currentMatch, KEY_SHOUTS)) {
                    shouts= currentMatch.getInt(KEY_SHOUTS);
                }
                //get the category of the current latest news
                if (Utils.contains(currentMatch, KEY_OUTS)) {
                    outs = currentMatch.getInt(KEY_OUTS);
                }

                if (Utils.contains(currentMatch, KEY_NOT_OUTS)) {
                    not_outs = currentMatch.getInt(KEY_NOT_OUTS);
                }

                Match match = new Match();
                match.setId(id);
                match.setTeam_1(team_1);
                match.setTeam_2(team_2);
                match.setTeam_1_flag(team_1_flag);
                match.setTeam_2_flag(team_2_flag);
                match.setSituation(situation);
                match.setShouts(shouts);
                match.setOuts(outs);
                match.setNot_outs(not_outs);

                if (id != -1) {
                    matchArrayList.add(match);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return matchArrayList;
    }


}
