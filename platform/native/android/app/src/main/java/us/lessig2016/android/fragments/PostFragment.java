package us.lessig2016.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import us.lessig2016.android.MainActivity;
import us.lessig2016.android.R;
import us.lessig2016.android.adapters.ActionArrayAdapter;
import us.lessig2016.android.api.Action;
import us.lessig2016.android.helpers.Constants;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PostFragment extends Fragment implements AbsListView.OnItemClickListener {
    private static final String TAG = "PostFragment";

    private SwipeRefreshLayout swipeContainer;
    private ParseQuery<Action> mFeedQuery;
    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mActionAdapter;
    private ArrayList<Action> mActions;

    // TODO: Rename and change types of parameters
    public static PostFragment newInstance() {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        mActions = new ArrayList<>();
        mActionAdapter = new ActionArrayAdapter<>(getActivity(),
                R.layout.list_item_post, mActions);
        requestFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                requestFeed();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mActionAdapter);
        mListView.setEmptyView(view.findViewById(android.R.id.empty));

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach");
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("Whoops! No posts to show.");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if(mFeedQuery != null) {
            mFeedQuery.cancel();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(mActions.get(position).getObjectId());
        }
    }

    private void requestFeed() {
        Log.d(TAG, "requestFeed");
        if(mFeedQuery == null) {
            mFeedQuery = ParseQuery.getQuery(Action.class);
            mFeedQuery.addDescendingOrder("priority");
            mFeedQuery.addDescendingOrder("createdAt");
        }
        mFeedQuery.findInBackground(new FindCallback<Action>() {
            public void done(final List<Action> actionList, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Error getting actions: " + e.getMessage());

                } else {
                    // Release any objects previously pinned for this query.
                    ParseObject.unpinAllInBackground(Constants.LOCAL_DATASTORE_LABEL_ACTIONS, actionList, new DeleteCallback() {
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.d(TAG, "Error deleting cached actions: " + e.getMessage());
                                return;
                            }

                            // Add the latest results for this query to the cache.
                            ParseObject.pinAllInBackground(Constants.LOCAL_DATASTORE_LABEL_ACTIONS, actionList);
                        }
                    });

                    Log.d(TAG, "Retrieved " + actionList.size() + " actions");
                    mActions.clear();
                    mActions.addAll(actionList);
                    ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }
            }
        });
    }

    /**
     * The default title for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
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
        public void onFragmentInteraction(String id);
    }

}
