package com.obvious.nasagallery.util;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.obvious.nasagallery.R;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

public class FragmentUtils implements NasaGalleryConstants {

    /*
     * Add the initial fragment, in most cases the first tab in BottomNavigationView
     */
    public static void addInitialTabFragment(FragmentManager fragmentManager,
                                             Map<String, Stack<Fragment>> stacks,
                                             String tag,
                                             Fragment fragment,
                                             int layoutId,
                                             boolean shouldAddToStack) {
        if (shouldAddToStack) stacks.get(tag).push(fragment);
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .add(layoutId, fragment)
                .commit();
    }

    /*
     * Add additional tab in BottomNavigationView on click, apart from the initial one and for the first time
     */
    public static void addAdditionalTabFragment(FragmentManager fragmentManager,
                                                Map<String, Stack<Fragment>> stacks,
                                                String tag,
                                                Fragment show,
                                                Fragment hide,
                                                int layoutId,
                                                boolean shouldAddToStack) {

        if (shouldAddToStack) stacks.get(tag).push(show);
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .add(layoutId, show)
                .show(show)
                .hide(hide)
                .commit();
    }

    /*
     * Hide previous and show current tab fragment if it has already been added
     * In most cases, when tab is clicked again, not for the first time
     */
    public static void showHideTabFragment(FragmentManager fragmentManager,
                                           Fragment show,
                                           Fragment hide) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .hide(hide)
                .show(show)
                .commit();
    }

    /*
     * Add fragment in the particular tab stack and show it, while hiding the one that was before
     */
    public static void addShowHideFragment(FragmentManager fragmentManager,
                                           Map<String, Stack<Fragment>> stacks,
                                           String tag,
                                           Fragment show,
                                           Fragment hide,
                                           int layoutId,
                                           boolean shouldAddToStack) {
        if (shouldAddToStack)
            stacks.get(tag).push(show);

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .add(layoutId, show)
                .show(show)
                .hide(hide)
                .commitAllowingStateLoss();


    }


    public static void removeFragment(FragmentManager fragmentManager, Fragment show, Fragment remove) {
        fragmentManager
                .beginTransaction()
                .remove(remove)
                .show(show)
                .commit();
    }

    /*
     * Send action from fragment to activity
     */
    public static void sendActionToActivity(String action, String tab, String mCategoryId, String mCategoryTitle, boolean shouldAdd, BaseFragment.FragmentInteractionCallback fragmentInteractionCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTION, action);
        bundle.putString(DATA_KEY_1, tab);
        bundle.putBoolean(DATA_KEY_2, shouldAdd);
        bundle.putString(KEY_CATEGORY_CODE, mCategoryId);
        bundle.putString(KEY_SELECTED_ITEM, mCategoryTitle);
        fragmentInteractionCallback.onFragmentInteractionCallback(bundle);
    }

    /*
     * Send action from fragment to activity
     */
    public static void sendActionToActivity(String action, String tab, String mCategoryId, String mCategoryTitle, String mode, String type, boolean shouldAdd, BaseFragment.FragmentInteractionCallback fragmentInteractionCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTION, action);
        bundle.putString(DATA_KEY_1, tab);
        bundle.putBoolean(DATA_KEY_2, shouldAdd);
        bundle.putString(KEY_CATEGORY_CODE, mCategoryId);
        bundle.putString(KEY_SELECTED_ITEM, mCategoryTitle);
        bundle.putString(ACTION_MODE, mode);
        bundle.putString(TEMPLATE_TYPE, type);
        fragmentInteractionCallback.onFragmentInteractionCallback(bundle);
    }

    //used for simple navigation
    public static void sendActionToActivity(String action, String tab, boolean shouldAdd, BaseFragment.FragmentInteractionCallback fragmentInteractionCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTION, action);
        bundle.putString(DATA_KEY_1, tab);
        bundle.putBoolean(DATA_KEY_2, shouldAdd);
        fragmentInteractionCallback.onFragmentInteractionCallback(bundle);
    }

    //used for simple navigation with data object
    public static void sendActionToActivity(String action, String tab, Object data, boolean shouldAdd, BaseFragment.FragmentInteractionCallback fragmentInteractionCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTION, action);
        bundle.putString(DATA_KEY_1, tab);
        bundle.putBoolean(DATA_KEY_2, shouldAdd);
        bundle.putSerializable(KEY_OBJ_NASA_DATA, (Serializable) data);
        fragmentInteractionCallback.onFragmentInteractionCallback(bundle);
    }

    //used for detail page
    public static void sendActionToDetail(String action, String tab, int index, boolean shouldAdd, BaseFragment.FragmentInteractionCallback fragmentInteractionCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(ACTION, action);
        bundle.putString(DATA_KEY_1, tab);
        bundle.putBoolean(DATA_KEY_2, shouldAdd);
        bundle.putInt(KEY_CURRENT_INDEX, index);
        fragmentInteractionCallback.onFragmentInteractionCallback(bundle);
    }



}
