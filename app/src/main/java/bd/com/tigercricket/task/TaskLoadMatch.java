package bd.com.tigercricket.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import bd.com.tigercricket.callbacks.MatchLoadedListener;
import bd.com.tigercricket.extras.MatchUtils;
import bd.com.tigercricket.network.VolleySingleton;
import bd.com.tigercricket.pojo.Match;

/**
 * Created by Windows on 02-03-2015.
 */
public class TaskLoadMatch extends AsyncTask<Void, Void, ArrayList<Match>> {
    private MatchLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;



    public TaskLoadMatch(MatchLoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Match> doInBackground(Void... params) {

        ArrayList<Match> matchArrayList = MatchUtils.loadMatch(requestQueue);
        return matchArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Match> matchArrayList) {
        if (myComponent != null) {
            myComponent.onMatchLoaded(matchArrayList);
        }
    }


}
