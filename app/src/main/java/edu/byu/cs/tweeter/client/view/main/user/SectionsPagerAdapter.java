package edu.byu.cs.tweeter.client.view.main.user;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.client.view.main.follow.FollowerFragment;
import edu.byu.cs.tweeter.client.view.main.follow.FollowingFragment;
import edu.byu.cs.tweeter.client.view.main.statuses.StoryFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int STORY_FRAGMENT_POSITION = 0;
    private static final int FOLLOWING_FRAGMENT_POSITION = 1;
    private static final int FOLLOWER_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;
    private final User rootUser;
    private final User currentUser;
    private final AuthToken authToken;

    public SectionsPagerAdapter(Context context, FragmentManager fm, User rootUser, User currentUser, AuthToken authToken) {
        super(fm);
        mContext = context;
        this.rootUser = rootUser;
        this.currentUser = currentUser;
        this.authToken = authToken;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case  STORY_FRAGMENT_POSITION:
                return StoryFragment.newInstance(rootUser, currentUser, authToken);
            case FOLLOWING_FRAGMENT_POSITION:
                return FollowingFragment.newInstance(currentUser, authToken);
            case FOLLOWER_FRAGMENT_POSITION:
                return FollowerFragment.newInstance(currentUser, authToken);
            default:
                return null; // If this ever happens, we have bigger problems.
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}