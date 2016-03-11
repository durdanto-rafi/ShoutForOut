package bd.com.tigercricket.shoutforout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bd.com.tigercricket.callbacks.MatchLoadedListener;
import bd.com.tigercricket.extras.Constants;
import bd.com.tigercricket.logging.L;
import bd.com.tigercricket.network.VolleySingleton;
import bd.com.tigercricket.pojo.Match;
import bd.com.tigercricket.pojo.User;
import bd.com.tigercricket.task.TaskLoadMatch;

import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_EMAIL;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_MATCH_ID;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_NAME;
import static bd.com.tigercricket.extras.Keys.EndpointMatch.KEY_SHOUT;
import static bd.com.tigercricket.extras.UrlEndpoints.URL_SHOUT;


public class LogoutActivity extends Activity implements MatchLoadedListener {

    private TextView btnLogout, tvTeam2, tvTeam1, tvSituation, tvTotalNotOut, tvTotalOut, tvTotalShout;
    private User user;
    private ImageView profileImage, ivTeam1, ivTeam2;
    Bitmap bitmap;
    private TextView tvSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQUEST_CODE = 1234;
    Dialog match_text_dialog;
    ArrayList<String> matches_text;
    ArrayList<Match> matchArrayList;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    String shout;
    Match match;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        user = PrefUtils.getCurrentUser(LogoutActivity.this);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        ivTeam1 = (ImageView) findViewById(R.id.ivTeam1);
        ivTeam2 = (ImageView) findViewById(R.id.ivTeam2);
        tvSpeechInput = (TextView) findViewById(R.id.tvSpeechInput);
        tvTeam2 = (TextView) findViewById(R.id.tvTeam2);
        tvTeam1 = (TextView) findViewById(R.id.tvTeam1);
        tvSituation = (TextView) findViewById(R.id.tvSituation);

        tvTotalShout = (TextView) findViewById(R.id.tvTotalShout);
        tvTotalOut = (TextView) findViewById(R.id.tvTotalOut);
        tvTotalNotOut = (TextView) findViewById(R.id.tvTotalNotOut);


        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();

        // fetching facebook's profile picture
        if (isConnected()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    URL imageURL = null;
                    try {
                        imageURL = new URL("https://graph.facebook.com/" + user.getFacebookID() + "/picture?type=large");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    profileImage.setImageBitmap(bitmap);
                }
            }.execute();
        }


        btnLogout = (TextView) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearCurrentUser(LogoutActivity.this);
                // We can logout from facebook by calling following method
                LoginManager.getInstance().logOut();
                Intent i = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        FloatingActionButton fbtnRecord = (FloatingActionButton) findViewById(R.id.fbtnRecord);
        fbtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if (isConnected()) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE);
                    tvSpeechInput.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }
            }
        });

        FloatingActionButton fbLogout = (FloatingActionButton) findViewById(R.id.fbLogout);
        fbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtils.clearCurrentUser(LogoutActivity.this);
                // We can logout from facebook by calling following method
                LoginManager.getInstance().logOut();
                Intent i = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        new TaskLoadMatch(this).execute();

    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net != null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            for (String text : matches_text) {
                if (text.matches("(?i).*no.*")) {
                    tvSpeechInput.setText("Not Out");
                    shout = "2";
                    shoutToServer();
                    return;
                } else if (text.matches("(?i).*out.*")) {
                    tvSpeechInput.setText("Out");
                    shout = "1";
                    shoutToServer();
                    return;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onMatchLoaded(ArrayList<Match> matchArrayList) {
        if(matchArrayList.size()==0)
        {
            showChangeLangDialog("No current Match !", "Please try later");
            return;
        }
        match = new Match();
        match = matchArrayList.get(0);
        tvTeam1.setText(match.getTeam_1());
        tvTeam2.setText(match.getTeam_2());
        tvSituation.setText(match.getSituation());

        tvTotalShout.setText("Total Shout : " + match.getShouts());
        tvTotalOut.setText("Total Out : " + match.getOuts());
        tvTotalNotOut.setText("Total Not Out : " + match.getNot_outs());

        loadImages(match.getTeam_1_flag(), ivTeam1);
        loadImages(match.getTeam_2_flag(), ivTeam2);
    }

    private void loadImages(String urlThumbnail, final ImageView ivImage) {
        if (!urlThumbnail.equals(Constants.NA)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    ivImage.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    private void shoutToServer() {
        if (isConnected()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SHOUT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                message = jsonObject.getString("message");
                                String match = jsonObject.getString("match");
                                //L.T(LogoutActivity.this, message);
                                showChangeLangDialog(match, message);
                                new TaskLoadMatch(LogoutActivity.this).execute();
                            } catch (JSONException e) {

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String message = error.getMessage();
                            L.T(LogoutActivity.this, error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_NAME, user.getName());
                    params.put(KEY_EMAIL, user.getEmail());
                    params.put(KEY_MATCH_ID, String.valueOf(match.getId()));
                    params.put(KEY_SHOUT, shout);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public void showChangeLangDialog(String shout, String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.display_result, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(shout);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
            }
        });
        /*dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });*/
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

}