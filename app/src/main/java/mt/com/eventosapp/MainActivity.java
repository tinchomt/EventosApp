package mt.com.eventosapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import mt.com.eventosapp.lectordni.AgregarAsistente;
import mt.com.eventosapp.lectordni.BuscarAsistente;
import mt.com.eventosapp.lectordni.CargaPadron;
import mt.com.eventosapp.lectordni.RegistraAsistente;
import mt.com.eventosapp.lectordni.ScanBarcodeActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button scanbtn, buscarPorDni;
    TextView result, var_docu;

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        System.out.println("Evento: "+pref.getString("evento", "Evento") );
        getSupportActionBar().setTitle(pref.getString("evento", "Evento"));

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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        System.out.println("RegistraAsistente 1");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        buscarPorDni = (Button)findViewById(R.id.bot_buscar_dni);

        scanbtn = (Button) findViewById(R.id.scanbtn);

        result = (TextView) findViewById(R.id.result);
        var_docu = (TextView) findViewById(R.id.var_docu);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ScanBarcodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        buscarPorDni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("RegistraAsistente ");
                //Intent intent = new Intent(getBaseContext(), BuscarAsistente.class);
                Intent intent = new Intent(MainActivity.this, RegistraAsistente.class);
                System.out.println("docu: "+var_docu.getText().toString());
                intent.putExtra("docu",var_docu.getText().toString());
                startActivity(intent);
                // startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(barcode.displayValue);
                        Intent i_resultado = new Intent(getBaseContext(),RegistraAsistente.class);
                        i_resultado.putExtra("valores",barcode.displayValue);
                        startActivity(i_resultado);

                    }
                });
            }
        }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            //System.out.println("docu: "+var_docu.getText().toString());
            //intent.putExtra("docu",var_docu.getText().toString());
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(getBaseContext(), AgregarAsistente.class);
            startActivity(intent);

          //  Intent intent = new Intent(getBaseContext(), RegistraAsistente.class);
          //  System.out.println("docu: "+var_docu.getText().toString());
          //  intent.putExtra("docu",var_docu.getText().toString());
          //  startActivity(intent);

//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
