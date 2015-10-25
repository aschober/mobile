package us.lessig2016.android.adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import us.lessig2016.android.R;
import us.lessig2016.android.helpers.LessigHelpers;

/**
 * Created by Allen on 10/14/15.
 */
public class ActionArrayAdapter<T> extends ArrayAdapter<T> {
    private static final String TAG = "ActionArrayAdapter";

    LayoutInflater mInflater;
    int mResource;

    public ActionArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, position, convertView, parent, mResource);
    }

    private View createViewFromResource(LayoutInflater inflater, int position, View convertView,
                                        ViewGroup parent, int resource) {
        View view;
        TextView titleView;
        TextView descriptionView;
        TextView footerView;
        ImageView image;
        ImageButton actionButton;

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        try {
            titleView = (TextView) view.findViewById(R.id.list_item_title);
            image = (ImageView) view.findViewById(R.id.list_item_icon);
            descriptionView = (TextView) view.findViewById(R.id.list_item_description);
            footerView = (TextView) view.findViewById(R.id.list_item_footer);
            actionButton = (ImageButton) view.findViewById(R.id.list_item_action);
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        final ParseObject action = (ParseObject) getItem(position);
        String title = "";
        String description = "";
        String footer = "";

        title = action.getString("title");
        description = action.getString("message");
        footer = LessigHelpers.getRelativeTimeString(action.getUpdatedAt());

        titleView.setText(title);
        descriptionView.setText(description);
        footerView.setText(footer);
        Picasso.with(getContext())
                .load(action.getString("thumbnailUrl"))
                .placeholder(R.drawable.lessig_avatar)
                .into(image);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "action button clicked for: " + action.getObjectId());
            }
        });

        return view;
    }


}
