package com.tobaccofeed.tobaccofeed;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private enum ActivePage {
        MAIN, SEARCH, TOBACCO, UPROFILE, UFAVOURITES, SETTINGS
    }

    private NavigationView navigationView;
    private ActivePage activePage;
    private boolean loggedIn = false;


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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
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
    public boolean onPrepareOptionsMenu(Menu menu) {

        boolean actionSave = (activePage == ActivePage.SETTINGS);
        System.out.println(actionSave);

        menu.findItem(R.id.action_search).setVisible(!actionSave);
        menu.findItem(R.id.action_save).setVisible(actionSave);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("TobaccoFeed", "DATA SEARCHED: " + query);
                Toast.makeText(getApplicationContext(),
                        getString(R.string.search_lock_message) + query + "'", Toast.LENGTH_LONG).show();
                MenuItemCompat.collapseActionView(menu.findItem(R.id.action_search));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search: {
                return true;
            }
            case R.id.action_save: {
                Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();
                navigationView.setCheckedItem(R.id.nav_main);
                onNavigationItemSelected(navigationView.getMenu().getItem(0));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;

        String pageName = item.getTitle().toString();

        switch (item.getItemId()) {
            case R.id.nav_main:
                fragmentClass = MainFragment.class;
                activePage = ActivePage.MAIN;
                Log.i("TobaccoFeed", "Jump to main");
                break;

            case R.id.nav_search:
                fragmentClass = SearchFragment.class;
                activePage = ActivePage.SEARCH;
                Log.i("TobaccoFeed", "Jump to search");
                break;

            case R.id.nav_tobacco:
                fragmentClass = TobaccoFragment.class;
                activePage = ActivePage.TOBACCO;
                Log.i("TobaccoFeed", "Jump to tobacco");
                break;

            case R.id.nav_profile:
                fragmentClass = UserProfileFragment.class;
                activePage = ActivePage.UPROFILE;
                Log.i("TobaccoFeed", "Jump to user profile");
                break;

            case R.id.nav_favourites:
                fragmentClass = UserFavouritesFragment.class;
                activePage = ActivePage.UFAVOURITES;
                Log.i("TobaccoFeed", "Jump to user favourites");
                break;

            case R.id.nav_logout:
                fragmentClass = MainFragment.class;
                activePage = ActivePage.MAIN;
                navigationView.getMenu().getItem(0).setChecked(true);

                Toast.makeText(getApplicationContext(),
                        R.string.log_out_message,
                        Toast.LENGTH_SHORT).show();

                pageName = navigationView.getMenu().getItem(0).getTitle().toString();

                // Process logout here

                navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_favourites).setVisible(false);

                Log.i("TobaccoFeed", "Jump to logout");
                break;

            case R.id.nav_login:
                fragmentClass = MainFragment.class;
                activePage = ActivePage.MAIN;
                navigationView.getMenu().getItem(0).setChecked(true);

                Toast.makeText(getApplicationContext(),
                        R.string.log_in_message,
                        Toast.LENGTH_SHORT).show();

                pageName = navigationView.getMenu().getItem(0).getTitle().toString();

                // Process login here

                navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_favourites).setVisible(true);

                Log.i("TobaccoFeed", "Jump to login");
                break;

            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                activePage = ActivePage.SETTINGS;
                Log.i("TobaccoFeed", "Jump to settings");
                break;

            default:
                fragmentClass = MainFragment.class;
                activePage = ActivePage.MAIN;
                if (!navigationView.getMenu().getItem(0).isChecked()) {
                    navigationView.getMenu().getItem(0).setChecked(true);
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

        try {
            getSupportActionBar().setTitle(pageName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
