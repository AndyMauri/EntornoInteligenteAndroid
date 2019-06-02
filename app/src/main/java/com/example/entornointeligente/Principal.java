package com.example.entornointeligente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.zip.Inflater;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ControlFragment.OnFragmentInteractionListener,
        ComponentesFragment.OnFragmentInteractionListener, Fragment_Informacion.OnFragmentInteractionListener,
        UsuarioFragment.OnFragmentInteractionListener{
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("aaaa");
                        TextView Nombre = findViewById(R.id.nombre1);
                        Nombre.setText(LoginActivity.nombre);
                        TextView correo = findViewById(R.id.correo1);
                        correo.setText(LoginActivity.E);
                        String idRol=LoginActivity.idRol;

                        if(idRol.equals("1")){
                            navigationView.getMenu().clear();
                            navigationView.inflateMenu(R.menu.activity_principal_drawer);
                        }else{
                            navigationView.getMenu().clear();
                            navigationView.inflateMenu(R.menu.activity_principal_drawer2);
                        }
                    }
                });
            }
        }).start();
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
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Logaut) {

            Intent logaut = new Intent(this, LoginActivity.class);
            logaut.putExtra("Logaut", "Sesión finalizada");
            startActivity(logaut);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean FragmentTransaction = false;
        Fragment fragment = null;


        if (id == R.id.nav_camera) {
            // Handle the camera action


        } else if (id == R.id.Usuario) {
            fragment = new UsuarioFragment();
            FragmentTransaction = true;

        } else if (id == R.id.control) {
            fragment = new ControlFragment();
            FragmentTransaction = true;

        } else if (id == R.id.simulador) {
           /* fragment = new Fragment_Informacion();
            FragmentTransaction = true;*/

        } else if (id == R.id.componentes) {
            fragment = new ComponentesFragment();
            FragmentTransaction = true;

        } else if (id == R.id.informacion) {
            fragment = new Fragment_Informacion();
            FragmentTransaction = true;

        }

        if(FragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
