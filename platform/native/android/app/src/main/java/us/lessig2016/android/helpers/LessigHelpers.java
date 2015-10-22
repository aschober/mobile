package us.lessig2016.android.helpers;


import android.text.format.DateUtils;
import java.util.Date;

/**
 * Created by Allen on 10/21/15.
 */
public class LessigHelpers {

    public static String getRelativeTimeString(Date timestamp){
        if(timestamp != null) {
            return DateUtils.getRelativeTimeSpanString(timestamp.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        }
        return null;
    }
}
