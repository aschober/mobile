package us.lessig2016.android.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import us.lessig2016.android.R;
import us.lessig2016.android.adapters.dummy.DummyContent;

/**
 * Created by Allen on 10/14/15.
 */
public class PostArrayAdapter<T> extends ArrayAdapter<T> {

    LayoutInflater mInflater;
    int mResource;

    public PostArrayAdapter(Context context, int resource, List objects) {
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
        TextView text;
        TextView description;
        TextView footer;
        ImageView image;
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        try {
            text = (TextView) view.findViewById(R.id.list_item_title);
            image = (ImageView) view.findViewById(R.id.list_item_icon);
            description = (TextView) view.findViewById(R.id.list_item_description);
            footer = (TextView) view.findViewById(R.id.list_item_footer);
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        DummyContent.PostItem item = (DummyContent.PostItem) getItem(position);
        text.setText(item.getTitle());
        description.setText(item.getDescription());
        footer.setText(item.getFooter());

        // TODO: for testing, assuming image res id exits for image name
        int imageResId = getContext().getResources().getIdentifier(item.getImageResName(), "drawable", getContext().getPackageName());
        image.setImageResource(imageResId);

        return view;
    }
}
