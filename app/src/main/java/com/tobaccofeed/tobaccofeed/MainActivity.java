package com.tobaccofeed.tobaccofeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_main));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        switch (item.getItemId()) {
            case R.id.nav_main:
                fragmentClass = MainFragment.class;
                Log.i("TobaccoFeed", "Jump to main");
                break;

            case R.id.nav_search:
                fragmentClass = SearchFragment.class;
                Log.i("TobaccoFeed", "Jump to search");
                break;

            case R.id.nav_tobacco:
                fragmentClass = TobaccoFragment.class;
                Log.i("TobaccoFeed", "Jump to tobacco");
                break;

            case R.id.nav_profile:
                fragmentClass = UserProfileFragment.class;
                Log.i("TobaccoFeed", "Jump to user profile");
                break;

            case R.id.nav_favourites:
                fragmentClass = UserFavouritesFragment.class;
                Log.i("TobaccoFeed", "Jump to user favourites");
                break;

            case R.id.nav_logout:
                fragmentClass = MainFragment.class;
                navigationView.getMenu().getItem(0).setChecked(true);
                Toast.makeText(getApplicationContext(),
                        "Logged out successfully!",
                        Toast.LENGTH_SHORT).show();
                // Process logout here
                Log.i("TobaccoFeed", "Jump to logout");
                break;

            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                Log.i("TobaccoFeed", "Jump to settings");
                break;

            default:
                fragmentClass = MainFragment.class;
                if (!navigationView.getMenu().getItem(0).isChecked()) {
                    navigationView.getMenu().getItem(0).setChecked(true);
                    System.out.println("CHECK MAIN");
                }
                Log.i("TobaccoFeed", "Jump to main (default)");
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameField, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
