package sauerapps.self_destructingapp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import sauerapps.self_destructingapp.R;

public class FriendsTab extends ListFragment {

    private static final String TAG = FriendsTab.class.getSimpleName();

    protected List<ParseUser> mFriends;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.friends_tab,container,false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
        query.addAscendingOrder(ParseConstants.KEY_USERNAME);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {

                if (e == null) {


                mFriends = friends;

                String[] usernames = new String[mFriends.size()];
                int i = 0;
                for (ParseUser user : mFriends) {
                    usernames[i] = user.getUsername();
                    i++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(),
                        android.R.layout.simple_list_item_1, usernames);
                setListAdapter(adapter);
                }
                else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getListView().getContext());
                    alertBuilder.setMessage(e.getMessage())
                            .setTitle(R.string.edit_friends_error_message)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = alertBuilder.create();
                    dialog.show();
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }



    }
