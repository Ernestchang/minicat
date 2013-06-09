package com.mcxiaoke.fanfouapp.fragment;

import android.app.Activity;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.mcxiaoke.fanfouapp.adapter.StatusCursorAdapter;
import com.mcxiaoke.fanfouapp.api.Paging;
import com.mcxiaoke.fanfouapp.AppContext;
import com.mcxiaoke.fanfouapp.controller.DataController;
import com.mcxiaoke.fanfouapp.dao.model.StatusModel;
import com.mcxiaoke.fanfouapp.service.SyncService;
import com.mcxiaoke.fanfouapp.util.Utils;

/**
 * @author mcxiaoke
 * @version 4.0 2013.05.18
 */
public class UserTimelineFragment extends BaseTimlineFragment implements
        OnClickListener {
    private static final String TAG = UserTimelineFragment.class.getSimpleName();

    public static UserTimelineFragment newInstance(String userId) {
        return newInstance(userId, false);
    }

    public static UserTimelineFragment newInstance(String userId, boolean refresh) {
        Bundle args = new Bundle();
        args.putString("id", userId);
        args.putBoolean("refresh", refresh);
        UserTimelineFragment fragment = new UserTimelineFragment();
        fragment.setArguments(args);
        if (AppContext.DEBUG) {
            Log.d(TAG, "newInstance() " + fragment);
        }
        return fragment;
    }

    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((StatusCursorAdapter) getAdapter()).setColored(false);
    }

    @Override
    protected void doFetch(boolean doGetMore) {
        final ResultHandler handler = new ResultHandler(this);
        final Cursor cursor = getCursor();

        Paging p = new Paging();
        if (doGetMore) {
            p.maxId = Utils.getMaxId(cursor);
        } else {
            p.sinceId = Utils.getSinceId(cursor);
        }
        if (AppContext.DEBUG) {
            Log.d(TAG, "doFetch() userId=" + userId + " doGetMore=" + doGetMore
                    + " paging=" + p + " type=" + getType());
        }
        SyncService.getTimeline(getActivity(), getType(), handler, userId, p);
    }

    @Override
    protected int getType() {
        return StatusModel.TYPE_USER;
    }

    @Override
    protected void parseArguments(Bundle data) {
        userId = data.getString("id");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public String getTitle() {
        return "时间线";
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return DataController
                .getUserTimelineCursorLoader(getActivity(), userId);
    }

}
