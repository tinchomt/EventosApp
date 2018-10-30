package mt.com.eventosapp.lectordni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.TreeMap;

import mt.com.eventosapp.MainActivity;
import mt.com.eventosapp.R;
import mt.com.eventosapp.modelo.Asistente;
import mt.com.eventosapp.modelo.RegistraEvento;
import mt.com.eventosapp.modelo.User;
import mt.com.eventosapp.remote.APIUtils;
import mt.com.eventosapp.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistraAsistente extends AppCompatActivity {

    TextView txtDNI,txtNombre,txtApellido, txtRegistrado;
    Button botonIngreso, botonRegistraAsist;
    String valor_dni;
    UserService userService;
    Map<String, String> mapaResultados = new TreeMap<String, String>();
    String valores;
    EditText editObservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_asistente);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        valor_dni = intent.getStringExtra("docu");
        System.out.println("RegistraAsistente Documento: "+valor_dni);

        valores = intent.getStringExtra("valores");
        System.out.println("Resultados: "+valores);

        if(valores != null){

            parserResultados(valores);
            valor_dni = mapaResultados.get("dni");
        }



        userService = APIUtils.getUserService();

        txtDNI = (TextView) findViewById(R.id.txtDNI);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtApellido = (TextView) findViewById(R.id.txtApellido);
        txtRegistrado = (TextView) findViewById(R.id.txtRegistrado);
        editObservacion = (EditText) findViewById(R.id.txtObservacion);

        botonIngreso = (Button) findViewById(R.id.btnIngresa);
        botonRegistraAsist = (Button) findViewById(R.id.btnRegistraAsistente);

        getAsistente(valor_dni);

        botonIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("BOTON Ingreso");
                RegistraEvento registra = new RegistraEvento();

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                System.out.println("Usuario: "+pref.getLong("id_usuario", 1) );
                System.out.println("id_evento: "+pref.getLong("id_evento", 0) );
                System.out.println("id_grupo: "+pref.getLong("id_grupo", 0) );

                Long id_evento = pref.getLong("id_evento", 0) ;
                Long id_estado = Long.valueOf(2);
                Long id_usuario = pref.getLong("id_usuario",0);
                Long id_grupo = pref.getLong("id_grupo", 0);


                //registra.setDescripcion("");
                registra.setDni(Long.valueOf(txtDNI.getText().toString()));
                registra.setNombre(txtNombre.getText().toString());
                registra.setApellido(txtApellido.getText().toString());
                registra.setId_evento(id_evento);
                registra.setId_estado(id_estado);
                registra.setId_usuario(id_usuario);
                registra.setDescripcion(editObservacion.getText().toString());
                registra.setId_grupo(id_grupo);

                registrarAcceso(registra);

            }
        });

        botonRegistraAsist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_registrar = new Intent(getBaseContext(),AgregarAsistente.class);
                startActivity(i_registrar);

            }
        });
    }

    public void getAsistente(String dni){
        System.out.print("getAsistente "+dni);
        //Call<List<User>> call = userService.getUsers();
        Call<Asistente> call = userService.getAsistente(Integer.parseInt(dni));
        call.enqueue(new Callback<Asistente>() {
            @Override
            public void onResponse(Call<Asistente> call, Response<Asistente> response) {

                System.out.println("response.isSuccessful(): ");
                if(response.isSuccessful()){
                    System.out.println("response.isSuccessful(): ");
                    if(response.body() !=null) {

                        System.out.println(response.body().toString());
                        System.out.println(response.body().getApellido());

                        txtDNI.setText(response.body().getDni().toString());
                        txtNombre.setText(response.body().getNombre());
                        txtApellido.setText(response.body().getApellido());

                        txtRegistrado.setText("ESTA REGISTRADO");
                        txtRegistrado.setTextColor(Color.rgb(35,158,119));
                        botonRegistraAsist.setVisibility(View.INVISIBLE);

                    }else{

                        Toast.makeText(getApplicationContext(), "No esta Registrado el DNI",
                                Toast.LENGTH_LONG).show();

                        txtRegistrado.setText("NO ESTA REGISTRADO");
                        txtRegistrado.setTextColor(Color.rgb(253,25,25));

                        txtDNI.setText(valor_dni);
                        txtNombre.setVisibility(View.INVISIBLE);
                        txtApellido.setVisibility(View.INVISIBLE);
                        botonIngreso.setVisibility(View.INVISIBLE);
                        botonRegistraAsist.setVisibility(View.VISIBLE);

                    }

                    //list = response.body();
                    //listView.setAdapter(new UserAdapter(BuscarAsistente.this, R.layout.list_user, list));
                }
            }

            @Override
            public void onFailure(Call<Asistente> call, Throwable t) {
                Log.e("ERROR catch: ", t.getMessage());
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


    public void parserResultados(String valores){

        String[] val = valores.split("@");
        mapaResultados.put("apellido",val[1]);
        mapaResultados.put("nombre",val[2]);
        mapaResultados.put("sexo",val[3]);
        mapaResultados.put("dni",val[4]);
        mapaResultados.put("f_nacimiento",val[6]);

    }
}
