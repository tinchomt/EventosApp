package mt.com.eventosapp.lectordni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import mt.com.eventosapp.MainActivity;
import mt.com.eventosapp.R;
import mt.com.eventosapp.modelo.Usuario;
import mt.com.eventosapp.remote.APIUtils;
import mt.com.eventosapp.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText editLogin, editClave;
    Button boton_ingresar;
    UserService userService;
    Usuario usr = null;

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressView = findViewById(R.id.login_progress);


        userService = APIUtils.getUserService();

        editLogin = (EditText) findViewById(R.id.campoLogin);
        editClave = (EditText) findViewById(R.id.campoClave);

        boton_ingresar = (Button) findViewById(R.id.bot_ingresar);


        boton_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = editLogin.getText().toString();
                String clave = editClave.getText().toString();

                System.out.println("Buscar Usuario 1");

                getUsuario(usuario,clave);
                System.out.println("Buscar Usuario 2");

//                if(usr != null){
//                    System.out.println("Buscar Usuario 3");
//                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    SharedPreferences.Editor editor = pref.edit();
//
//                    editor.putLong("id_usuario", usr.getId_usuario());
//                    editor.commit();
//
//                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intent);
//                }else{
//                    System.out.println("Buscar Usuario 4");
//                    Toast.makeText(getApplicationContext(), "Usuario Invalido/Clave Invalido AA",
//                            Toast.LENGTH_LONG).show();
//
//                }


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void getUsuario(String login, String clave){
        System.out.print("getUsuario "+login);
        //Call<List<User>> call = userService.getUsers();
        Call<Usuario> call = userService.getUsuario(login,clave);

        mProgressView.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                System.out.println("response.isSuccessful():1 ");
                mProgressView.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    System.out.println("response.isSuccessful()2: ");
                    if(response.body() !=null) {

                        System.out.println("response.isSuccessful()2.1: ");

                        System.out.println(response.body().toString());
                        System.out.println(response.body().getApellido());

                        usr = new Usuario();

                        usr.setId_usuario(response.body().getId_usuario());
                        usr.setLogin(response.body().getLogin());
                        usr.setClave(response.body().getClave());
                        usr.setId_grupo(response.body().getId_grupo());
                        usr.setApellido(response.body().getApellido());
                        usr.setNombre(response.body().getNombre());

                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putLong("id_usuario", usr.getId_usuario());
                        editor.putLong("id_grupo", usr.getId_grupo());

                        editor.commit();

                        //Intent intent_login = new Intent(getBaseContext(), MainActivity.class);
                        Intent intent_login = new Intent(getBaseContext(), SeleccionaEvento.class);
                        startActivity(intent_login);

                    }else{
                        System.out.println("response.isSuccessful()3: ");


                        Toast.makeText(getApplicationContext(), "Usuario Invalido",
                                Toast.LENGTH_LONG).show();



                    }

                    //list = response.body();
                    //listView.setAdapter(new UserAdapter(BuscarAsistente.this, R.layout.list_user, list));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mProgressView.setVisibility(View.GONE);

                Log.e("ERROR catch: ", t.getMessage());
                usr = null;
                Toast.makeText(getApplicationContext(), "Usuario Invalido",
                        Toast.LENGTH_LONG).show();

            }
        });

    }

}
