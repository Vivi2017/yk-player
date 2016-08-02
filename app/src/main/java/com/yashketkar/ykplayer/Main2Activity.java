package com.yashketkar.ykplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,VideosFragment.OnFragmentInteractionListener, TorrentsFragment.OnFragmentInteractionListener, LiveTVFragment.OnFragmentInteractionListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_videos) {
            // Handle the camera action
            fragmentClass = VideosFragment.class;
        } else if (id == R.id.nav_network_stream) {
            AlertDialog.Builder nwalert = new AlertDialog.Builder(this);
            nwalert.setTitle(getString(R.string.nw_alert_title));
            nwalert.setMessage(getString(R.string.nw_alert_message));
            final EditText nwinput = new EditText(this);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                // only for gingerbread and newer versions
                nwinput.setTextColor(Color.WHITE);
            }
            nwinput.setSingleLine();
            nwalert.setView(nwinput);
            nwalert.setPositiveButton(getString(R.string.nw_alert_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            playvideo(nwinput.getText().toString()
                                    .replaceAll("[\\t\\n\\r]", ""));
                        }
                    });
            nwalert.setNegativeButton(getString(R.string.nw_alert_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            // Canceled.
                        }
                    });
            nwalert.show();
        } else if (id == R.id.nav_torrent_stream) {

        } else if (id == R.id.nav_live_tv) {
            fragmentClass = LiveTVFragment.class;
        } else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_message));
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_title));
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_chooser)));
        } else if (id == R.id.nav_website) {

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void playvideo(String id) {
        //displayInterstitial();
        Intent intent = new Intent(Main2Activity.this,
                VideoPlayerActivity.class);
        intent.putExtra("EXTRA_URL", id);
        startActivity(intent);
    }

    public void onVideosFragmentInteraction(String id) {
        playvideo(id);
    }

    public void onLiveTVFragmentInteraction(String id) {
        playvideo(id);
    }

    public void onTorrentsFragmentInteraction(String id) {
        playvideo(id);
    }

    public void onSectionAttached(int number) {
        //mTitle = getResources().getStringArray(R.array.nav_drawer_items)[number];
    }
}
