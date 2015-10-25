package us.lessig2016.android;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Allen on 10/22/15.
 */
public class Lessig2016App extends Application {
    private static final String TAG = "Lessig2016App";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "PARSE_APP_ID", "PARSE_CLIENT_KEY");

        ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().increment("runCount");
        ParseUser.getCurrentUser().saveInBackground();

        // Associate the device with a user
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("user", ParseUser.getCurrentUser());
        installation.saveInBackground();
    }
}
