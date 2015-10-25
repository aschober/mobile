package us.lessig2016.android;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

import us.lessig2016.android.api.Action;

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
        ParseObject.registerSubclass(Action.class);
        Parse.initialize(this, "bKgF1QzqZL6qVUgj8FnVDDX3p9LcaDYwFvCU0pLn", "lC9k1yR9SqbOP1IzgrGBm2RCNRgeoEhi5wo5P7F1");

        ParseUser.enableAutomaticUser();
        ParseUser.getCurrentUser().increment("runCount");
        ParseUser.getCurrentUser().saveInBackground();

        // Associate the device with a user
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("user", ParseUser.getCurrentUser());
        installation.saveInBackground();
    }
}
