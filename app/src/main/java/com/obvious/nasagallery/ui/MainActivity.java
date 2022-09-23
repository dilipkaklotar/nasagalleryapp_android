package com.obvious.nasagallery.ui;

import static com.obvious.nasagallery.util.FragmentUtils.addInitialTabFragment;
import static com.obvious.nasagallery.util.FragmentUtils.addShowHideFragment;
import static com.obvious.nasagallery.util.FragmentUtils.removeFragment;
import static com.obvious.nasagallery.util.FragmentUtils.showHideTabFragment;
import static com.obvious.nasagallery.util.StackListManager.updateStackIndex;
import static com.obvious.nasagallery.util.StackListManager.updateTabStackIndex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;
import com.obvious.nasagallery.R;
import com.obvious.nasagallery.databinding.ActivityMainBinding;
import com.obvious.nasagallery.ui.dashboard.DashboardFragment;
import com.obvious.nasagallery.ui.description.DescriptionFragment;
import com.obvious.nasagallery.ui.detail.DetailFragment;
import com.obvious.nasagallery.ui.zoom.ZoomFragment;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.ConnectivityReceiver;
import com.obvious.nasagallery.util.NasaGalleryConstants;
import com.obvious.nasagallery.util.NetworkSchedulerService;
import com.obvious.nasagallery.util.ToastManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener,
        BaseFragment.FragmentInteractionCallback, NasaGalleryConstants, View.OnClickListener {

    ActivityMainBinding binding;

    private boolean isNetAvailable = false;

    private Map<String, Stack<Fragment>> stacks;
    private List<String> menuStacks;
    private List<String> stackList;
    private Fragment currentFragment;

    private Fragment dashboardFragment;

    private String currentTab = "";
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        createStacks();

        currentTab = TAB_HOME;
        BaseFragment.setCurrentTab(currentTab);

        addInitialTabFragment(getSupportFragmentManager(), stacks, TAB_HOME, dashboardFragment, R.id.container, true);
        resolveStackLists(TAB_HOME);
        assignCurrentFragment(dashboardFragment);
    }


    private void createStacks() {

        dashboardFragment = new DashboardFragment();

        stacks = new LinkedHashMap<>();
        stacks.put(TAB_HOME, new Stack<>());

        menuStacks = new ArrayList<>();
        menuStacks.add(TAB_HOME);

        stackList = new ArrayList<>();
        stackList.add(TAB_HOME);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        isNetAvailable = isConnected;
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent != null && intent.getAction().equals(NetworkSchedulerService.RECEIVER_NETWORK_CHANGED)) {
                    isNetAvailable = intent.getBooleanExtra(NetworkSchedulerService.NETWORK_AVAILABLE, false);
                    if (!isNetAvailable) {
                        ToastManager.toast(MainActivity.this, getString(R.string.toast_error_no_internet));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
    }

    private void initConnectionReceiver() {
        IntentFilter intentFilter = new IntentFilter(NetworkSchedulerService.RECEIVER_NETWORK_CHANGED);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
        isNetAvailable = ConnectivityReceiver.isConnectedBoolean(this);
    }

    @Override
    public void onClick(View view) {

    }

    private void resolveStackLists(String tabId) {
        updateStackIndex(stackList, tabId);
        updateTabStackIndex(menuStacks, tabId);
    }

    @Override
    public void onFragmentInteractionCallback(Bundle bundle) {
        String action = bundle.getString(ACTION);

        if (action != null) {
            switch (action) {

                case ACTION_DASHBOARD:
                    showFragment(bundle, new DashboardFragment());
                    break;
                case ACTION_DETAIL:
                    showFragment(bundle, new DetailFragment());
                    break;
                case ACTION_ZOOM:
                    showFragment(bundle, new ZoomFragment());
                    break;
                case ACTION_DESCRIPTION:
                    showFragment(bundle, new DescriptionFragment());
                    break;

            }
        }
    }

    private Fragment getCurrentFragmentFromShownStack() {
        return stacks.get(currentTab).elementAt(stacks.get(currentTab).size() - 1);
    }

    private void assignCurrentFragment(Fragment current) {
        currentFragment = current;
    }

    private void showFragment(Bundle bundle, Fragment fragmentToAdd) {
        String tab = bundle.getString(DATA_KEY_1);
        boolean shouldAdd = bundle.getBoolean(DATA_KEY_2);
        fragmentToAdd.setArguments(bundle);
        addShowHideFragment(getSupportFragmentManager(), stacks, tab, fragmentToAdd, getCurrentFragmentFromShownStack(), R.id.container, shouldAdd);
        assignCurrentFragment(fragmentToAdd);
    }

    @Override
    public void onBackPressed() {
        resolveBackPressed();
    }

    public void resolveBackPressed() {

        if (currentTab.equals(TAB_HOME) && stacks.get(TAB_HOME).size() == 1) {
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
            }
            Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.press_one_more_to_exit_app), Snackbar.LENGTH_SHORT).show();
            doubleBackToExitPressedOnce = true;
        } else {
            if (stacks.get(currentTab).size() > 1) {
                popFragment();
            } else {

                showHideTabFragment(getSupportFragmentManager(), stacks.get(TAB_HOME).lastElement(), currentFragment);
                resolveStackLists(TAB_HOME);
                assignCurrentFragment(stacks.get(TAB_HOME).lastElement());


            }
            doubleBackToExitPressedOnce = false;
        }
    }

    private void popFragment() {
        /*
         * Select the second last fragment in current tab's stack,
         * which will be shown after the fragment transaction given below
         */
        Fragment fragment = stacks.get(currentTab).elementAt(stacks.get(currentTab).size() - 2);

        /*pop current fragment from stack */
        stacks.get(currentTab).pop();

        removeFragment(getSupportFragmentManager(), fragment, currentFragment);

        assignCurrentFragment(fragment);



    }
}