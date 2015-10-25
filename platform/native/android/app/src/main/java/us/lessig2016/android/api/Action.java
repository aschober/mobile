package us.lessig2016.android.api;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Allen on 10/24/15.
 */
@ParseClassName("Action")
public class Action extends ParseObject {

    public enum ActionType {
        ACTION_GENERAL("general"),
        ACTION_URL("url"),
        ACTION_CALL("call"),
        ACTION_EMAIL("email"),
        ACTION_TWEET("tweet"),
        ACTION_RETWEET("retweet"),
        ACTION_TWEET_REPLY("tweet_reply"),
        ACTION_FB_POST("fb_post"),
        ACTION_FB_SHARE("fb_share"),
        ACTION_ATTEND("attend"),
        ACTION_AVATAR("avatar"),
        ACTION_INSTAGRAM("instagram");

        private final String typeString;

        ActionType(String typeString) {
            this.typeString = typeString;
        }

        public String toString() {
            return this.typeString;
        }

        public static ActionType parse(String typeString) {
            if (typeString != null) {
                for (ActionType actionType : ActionType.values()) {
                    if (typeString.equalsIgnoreCase(actionType.typeString)) {
                        return actionType;
                    }
                }
            }
            return null;
        }
    }
    public Action() {
        // A default constructor is required.
    }

    public ActionType getActionType() {
        return ActionType.parse(getString("actionType"));
    }

    public String getTitle() {
        return getString("title");
    }

    public String getMessage() {
        return getString("message");
    }

    public String getThumbnailUrl() {
        return getString("thumbnailUrl");
    }

    public String getImageUrl() {
        return getString("imageUrl");
    }

    public int getPriority() {
        return getInt("priority");
    }

    public String getRef() {
        return getString("ref");
    }

    public String[] getRecipients() {
        List<String> al = getList("recipients");
        Object[] objectArray = al.toArray();
        return Arrays.copyOf(objectArray, objectArray.length, String[].class);
    }

    public String getSubject() {
        return getString("subject");
    }

    public String getBody() {
        return getString("body");
    }
}
