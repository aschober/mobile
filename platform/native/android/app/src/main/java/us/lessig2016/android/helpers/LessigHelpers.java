package us.lessig2016.android.helpers;


import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import us.lessig2016.android.R;
import us.lessig2016.android.api.Action;

/**
 * Created by Allen on 10/21/15.
 */
public class LessigHelpers {
    private static final String TAG = "LessigHelpers";

    public static String getRelativeTimeString(Date timestamp){
        if(timestamp != null) {
            return DateUtils.getRelativeTimeSpanString(timestamp.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        }
        return null;
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

    public static void setupActionView(final Action action, ImageView view) {
        switch (action.getActionType()) {
            case ACTION_CALL:
                view.setImageResource(R.drawable.ic_call_white_24dp);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + action.getRef()));
                        view.getContext().startActivity(callIntent);
                    }
                });
                break;
            case ACTION_EMAIL:
                view.setImageResource(R.drawable.ic_email_white_24dp);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", action.getRecipients()[0], null));
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, action.getRecipients()); // String[] addresses
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, action.getSubject());
                        emailIntent.putExtra(Intent.EXTRA_TEXT, action.getBody());
                        view.getContext().startActivity(Intent.createChooser(emailIntent, action.getTitle()));
                    }
                });
                break;
            case ACTION_URL:
                view.setImageResource(R.drawable.ic_open_in_browser_white_24dp);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(action.getRef()));
                        view.getContext().startActivity(browserIntent);
                    }
                });
                break;
            case ACTION_TWEET:
                view.setImageResource(R.drawable.ic_twitter_white_24dp);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Create intent using ACTION_VIEW and a normal Twitter url:
                        String tweetUrl = "https://twitter.com/intent/tweet?";
                        if (action.getBody() != null) {
                            tweetUrl = tweetUrl + "&text=" + urlEncode(action.getBody());
                        }
                        if (action.getRef() != null) {
                            tweetUrl = tweetUrl + "&url=" + urlEncode(action.getRef());
                        }
                        Intent tweetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

                        // Narrow down to official Twitter app, if available:
                        List<ResolveInfo> matches = view.getContext().getPackageManager().queryIntentActivities(tweetIntent, 0);
                        for (ResolveInfo info : matches) {
                            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                                tweetIntent.setPackage(info.activityInfo.packageName);
                            }
                        }
                        view.getContext().startActivity(tweetIntent);
                    }
                });
                break;
            case ACTION_RETWEET:
                view.setImageResource(R.drawable.ic_twitter_white_24dp);
                break;
            case ACTION_FB_POST:
                view.setImageResource(R.drawable.ic_facebook_white_24dp);
                break;
            case ACTION_FB_SHARE:
                view.setImageResource(R.drawable.ic_facebook_white_24dp);
                break;
            case ACTION_ATTEND:
                //TODO: Define calendar invite spec
                view.setImageResource(R.drawable.ic_insert_invitation_white_24dp);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent calendarIntent = new Intent(Intent.ACTION_EDIT);
                        calendarIntent.setType("vnd.android.cursor.item/event");
                        calendarIntent.putExtra(CalendarContract.Events.TITLE, action.getTitle());
                        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Washington, DC");
                        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                Calendar.getInstance().getTimeInMillis());
                        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                Calendar.getInstance().getTimeInMillis() + 1800000);
                        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, action.getMessage());
                        view.getContext().startActivity(calendarIntent);
                    }
                });
                break;

            default:
                view.setImageResource(R.drawable.ic_open_in_browser_white_24dp);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
                break;
        }
    }
}
