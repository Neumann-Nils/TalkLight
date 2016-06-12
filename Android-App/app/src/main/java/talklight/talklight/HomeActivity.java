package talklight.talklight;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//import talklight.backend.LocalMe;
import backend.LocalMe;
import talklight.R;
import talklight.talklight.fragments.ContentGridFragment;
import talklight.talklight.fragments.NoConnectionFragment;
import talklight.talklight.fragments.PlayGameFragment;
import talklight.talklight.interfaces.Myself;
import talklight.talklight.interfaces.NotPairedException;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoConnectionFragment.OnFragmentInteractionListener, ContentGridFragment.ContentFragListener,PlayGameFragment.OnFragmentInteractionListener
{


    private Myself myself;
    private Toolbar toolbar;
    int myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myself = new LocalMe();

        Intent intent = getIntent();
        myId = intent.getIntExtra("id", 0);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toolbar.setBackgroundColor(Color.parseColor("#00000000"));
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.my_partners_item);
        Menu subMenu = item.getSubMenu();
        int id = 1680;
        subMenu.add(Menu.NONE, id, Menu.NONE, "Girlfriend");
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        if(id == R.id.privateMode)
        {
            try {
                myself.getPaired().setPrivate(!myself.getPaired().getPrivate());
                String state = "";
                if(myself.getPaired().getPrivate())
                {
                    item.setIcon(R.drawable.ic_private_active);
                    state = "aktiv";
                }else
                {
                    item.setIcon(R.drawable.ic_private_inactive);
                    state = "inaktiv";
                }
                Toast.makeText(this, "Privat Modus " + state, Toast.LENGTH_SHORT).show();
            } catch (NotPairedException e) {
                e.printStackTrace();
            }
        }else if(id == R.id.silentMode)
        {
            try {
                myself.getPaired().setSilent(!myself.getPaired().getSilent());
                String state = "";
                if(myself.getPaired().getSilent())
                {
                    item.setIcon(R.drawable.ic_silent_active);
                    state = "aktiv";
                }else
                {
                    item.setIcon(R.drawable.ic_silent_inactive);
                    state = "inaktiv";
                }
                Toast.makeText(this, "Ruhe Modus " + state, Toast.LENGTH_SHORT).show();
            } catch (NotPairedException e) {
                e.printStackTrace();
            }
        }
*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_add_partner) {
            Toast.makeText(this, "Add Partner Action", Toast.LENGTH_SHORT).show();
        }
        else if(id == 1680)
        {
            toolbar.setTitle("Freundin");
            ContentGridFragment frag = ContentGridFragment.newInstance(myId);
            final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentFragment,frag, "NewFragmentTag");
            frag.setSelf(myself);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onContentFragmentInteraction()
    {

    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}
