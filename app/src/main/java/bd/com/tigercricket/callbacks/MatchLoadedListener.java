package bd.com.tigercricket.callbacks;


import java.util.ArrayList;

import bd.com.tigercricket.pojo.Match;


/**
 * Created by Windows on 02-03-2015.
 */
public interface MatchLoadedListener {
    public void onMatchLoaded(ArrayList<Match> matchArrayList);
}
