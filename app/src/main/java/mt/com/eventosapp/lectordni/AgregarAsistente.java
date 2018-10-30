package mt.com.eventosapp.lectordni;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.Map;
import java.util.TreeMap;

import mt.com.eventosapp.MainActivity;
import mt.com.eventosapp.R;
import mt.com.eventosapp.modelo.Asistente;
import mt.com.eventosapp.modelo.RegistraEvento;
import mt.com.eventosapp.remote.APIUtils;
import mt.com.eventosapp.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarAsistente extends AppCompatActivity {

    EditText txtDni,txtApellido,txtNombre, txtEmail, txtObservaciones;
    Button botonGuardar, botonScan;

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    Map<String, String> mapaResultados = new TreeMap<String, String>();

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_asistente);
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

        userService = APIUtils.getUserService();

        botonScan = (Button) findViewById(R.id.btnScan);
        botonGuardar = (Button) findViewById(R.id.btnGuardar);

        txtDni = (EditText) findViewById(R.id.edtDNI);
        txtApellido = (EditText) findViewById(R.id.edtApellido);
        txtNombre = (EditText) findViewById(R.id.edtNombre);
        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtObservaciones = (EditText) findViewById(R.id.txtObservacion);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        botonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ScanBarcodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("BOTON Registro");
                RegistraEvento registra = new RegistraEvento();

                Asistente asistente = new Asistente();

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                System.out.println("Usuario: "+pref.getLong("id_usuario", 1) );
                System.out.println("id_evento: "+pref.getLong("id_evento", 0) );
                System.out.println("id_grupo: "+pref.getLong("id_grupo", 0) );

                Long id_evento = pref.getLong("id_evento", 0) ;
                Long id_estado = Long.valueOf(1);
                Long id_usuario = pref.getLong("id_usuario",0);
                Long id_grupo = pref.getLong("id_grupo", 0);

                asistente.setDni(Long.valueOf(txtDni.getText().toString()));
                asistente.setApellido(txtApellido.getText().toString());
                asistente.setNombre(txtNombre.getText().toString());
                asistente.setProvincia("");
                asistente.setLocalidad("");
                asistente.setJurisdiccion("");
                asistente.setTelefono("");
                asistente.setCelular_1("");
                asistente.setCelular_2("");
                asistente.setEmail(txtEmail.getText().toString());
                asistente.setCargo("");
                asistente.setDescripcion(txtObservaciones.getText().toString());
                asistente.setId_grupo(id_grupo);

                //registra.setDescripcion("");
                registra.setDni(Long.valueOf(txtDni.getText().toString()));
                registra.setNombre(txtNombre.getText().toString());
                registra.setApellido(txtApellido.getText().toString());
                registra.setId_evento(id_evento);
                registra.setId_estado(id_estado);
                registra.setId_usuario(id_usuario);
                registra.setDescripcion(txtObservaciones.getText().toString());
                registra.setId_grupo(id_grupo);

                registrarAsistente(asistente);
                registrarAcceso(registra);


            }
        });


    }

    public void registrarAsistente(Asistente a){

        System.out.println("Agregar Asistente"+a);

        Call<Asistente> call = userService.registraAsistente(a);

        call.enqueue(new Callback<Asistente>() {
            @Override
            public void onResponse(Call<Asistente> call, Response<Asistente> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Se Registro Asistente!", Toast.LENGTH_SHORT).show();
                    //Intent i_resultado = new Intent(getBaseContext(),MainActivity.class);
                    //startActivity(i_resultado);
                }
            }

            @Override
            public void onFailure(Call<Asistente> call, Throwable t) {

                Log.e("ERROR Add: ", t.getMessage());
            }
        });
    }

    public void registrarAcceso(RegistraEvento e){

        System.out.println("Agregar "+e);

        Call<RegistraEvento> call = userService.registraAcceso(e);

        call.enqueue(new Callback<RegistraEvento>() {
            @Override
            public void onResponse(Call<RegistraEvento> call, Response<RegistraEvento> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Se Registro Correctamente!", Toast.LENGTH_SHORT).show();
                    Intent i_resultado = new Intent(getBaseContext(),MainActivity.class);

                    startActivity(i_resultado);
                }
            }

            @Override
            public void onFailure(Call<RegistraEvento> call, Throwable t) {

                Log.e("ERROR Add: ", t.getMessage());
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Resultado:: ");
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");

               System.out.println("RESULT: "+barcode.displayValue);
                parserResultados(barcode.displayValue);

                txtDni.setText(mapaResultados.get("dni"));
                txtApellido.setText(mapaResultados.get("apellido"));
                txtNombre.setText(mapaResultados.get("nombre"));


            }
        }
    }

    public void parserResultados(String valores){

        String[] val = valores.split("@");
        mapaResultados.put("apellido",val[1]);
        mapaResultados.put("nombre",val[2]);
        mapaResultados.put("sexo",val[3]);
        mapaResultados.put("dni",val[4]);
        mapaResultados.put("f_nacimiento",val[6]);

    }
}
