package us.lessig2016.android.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import us.lessig2016.android.MainActivity;
import us.lessig2016.android.R;
import us.lessig2016.android.api.Action;
import us.lessig2016.android.helpers.LessigHelpers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostDetailFragment extends Fragment {
    private static final String TAG = "PostFragment";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_OBJECT_ID = "objectId";

    private String mObjectId;

    private TextView mTitleView;
    private TextView mMessageView;
    private TextView mFooterView;
    private ImageView mThumbnailView;
    private ImageView mImageView;
    private FloatingActionButton mFloatingActionButton;

    private OnFragmentInteractionListener mListener;
    private ParseQuery<Action> mActionQuery;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param objectID Parameter 1.
     * @return A new instance of fragment PostDetail.
     */
    public static PostDetailFragment newInstance(String objectID) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OBJECT_ID, objectID);
        fragment.setArguments(args);
        return fragment;
    }

    public PostDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mObjectId = getArguments().getString(ARG_OBJECT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        mTitleView = (TextView) view.findViewById(R.id.details_title);
        mMessageView = (TextView) view.findViewById(R.id.details_message);
        mFooterView = (TextView) view.findViewById(R.id.details_footer);
        mThumbnailView = (ImageView) view.findViewById(R.id.details_thumbnail);
        mImageView = (ImageView) view.findViewById(R.id.details_image);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestAction(mObjectId);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFloatingActionButton.hide();
        mListener = null;
        if(mActionQuery != null) {
            mActionQuery.cancel();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    private void requestAction(String objectId) {
        if(mActionQuery == null) {
            mActionQuery = ParseQuery.getQuery(Action.class);
            mActionQuery.fromLocalDatastore();
        }
        mActionQuery.getInBackground(objectId, new GetCallback<Action>() {
            @Override
            public void done(Action object, ParseException e) {

                mTitleView.setText(object.getTitle());
                mMessageView.setText(object.getMessage());
                mFooterView.setText(LessigHelpers.getRelativeTimeString(object.getUpdatedAt()));

                Picasso.with(getContext())
                        .load(object.getThumbnailUrl())
                        .placeholder(R.drawable.lessig_avatar_grayscale)
                        .into(mThumbnailView);
                Picasso.with(getContext())
                        .load(object.getImageUrl())
                        .resize(mImageView.getWidth(), 0)
                        .placeholder(R.drawable.lessig_avatar)
                        .into(mImageView);

                setupFloatingActionBar(object);
            }
        });
    }

    public void setupFloatingActionBar(final Action action) {
        Log.d(TAG, "setupFloatingActionBar: " + action.getActionType());

        if(action == null) {
            mFloatingActionButton.hide();
        } else {
            LessigHelpers.setupActionView(action, mFloatingActionButton);
            mFloatingActionButton.show();
        }
    }
}
