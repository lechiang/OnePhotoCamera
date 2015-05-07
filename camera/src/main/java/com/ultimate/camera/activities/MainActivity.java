/*
 * Copyright (c) 2014 Rex St. John on behalf of AirPair.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ultimate.camera.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.ultimate.camera.NavigationDrawerFragment;
import com.ultimate.camera.R;
import com.ultimate.camera.fragments.BaseFragment;
import com.ultimate.camera.fragments.HorizontalPhotoGalleryFragment;
import com.ultimate.camera.fragments.NativeCameraFragment;
import com.ultimate.camera.fragments.OnboardingFragment;
import com.ultimate.camera.fragments.PhotoFragment;
import com.ultimate.camera.fragments.SimpleAndroidImagePickerFragment;
import com.ultimate.camera.fragments.SimpleCameraIntentFragment;
import com.ultimate.camera.fragments.SimplePhotoGalleryListFragment;

/**
 * Created by Rex St. John (on behalf of AirPair.com) on 3/4/14.
 */
public class MainActivity extends CameraActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, BaseFragment.OnFragmentInteractionListener {

    /**
     * Actions
     */
    public static final int SELECT_PHOTO_ACTION = 0;

    /**
     * Fragment Identifiers
     */
    public static final int SIMPLE_CAMERA_INTENT_FRAGMENT = 0;
    public static final int SIMPLE_PHOTO_GALLERY_FRAGMENT = 1;
    public static final int SIMPLE_PHOTO_PICKER_FRAGMENT = 2;
    public static final int NATIVE_CAMERA_FRAGMENT = 3;
    public static final int HORIZONTAL_GALLERY_FRAGMENT = 4;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));*/
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        FragmentManager fragmentManager = getFragmentManager();
        mPrefs = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        Boolean inst = mPrefs.getBoolean("onboarding", false);
        String photo = mPrefs.getString("photo", "");
        Log.d("OnePhotoCamera", "Onboarding logged: " + inst);
        if (inst == false) {
            BaseFragment targetFragment = OnboardingFragment.newInstance(1);
            fragmentManager.beginTransaction().replace(R.id.container, targetFragment).commit();
        } else if (!photo.equalsIgnoreCase("")){
            BaseFragment targetFragment = PhotoFragment.newInstance(1);
            fragmentManager.beginTransaction().replace(R.id.container, targetFragment).commit();
        } else {

            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.replace(R.id.camera_fragment, new NativeCameraFragment()).commit();
            BaseFragment targetFragment = NativeCameraFragment.newInstance(1);
            fragmentManager.beginTransaction().replace(R.id.container, targetFragment).commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        BaseFragment targetFragment = null;

        // Populate the fragment
        switch (position) {
            case SIMPLE_CAMERA_INTENT_FRAGMENT: {
                targetFragment = SimpleCameraIntentFragment.newInstance(position + 1);
                break;
            }
            default:
                break;
        }

        // Select the fragment.
        fragmentManager.beginTransaction()
                .replace(R.id.container, targetFragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle Incoming messages from contained fragments.
     */

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onFragmentInteraction(int actionId) {

    }
}
