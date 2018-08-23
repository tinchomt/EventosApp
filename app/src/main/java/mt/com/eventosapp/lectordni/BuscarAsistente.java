package mt.com.eventosapp.lectordni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mt.com.eventosapp.Adapters.UserAdapter;
import mt.com.eventosapp.R;
import mt.com.eventosapp.modelo.User;
import mt.com.eventosapp.remote.APIUtils;
import mt.com.eventosapp.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarAsistente extends AppCompatActivity {

    String valor_dni;

    Button btnGetUsersList,btnGetUser, btnAddUser;
    ListView listView;

    UserService userService;
    List<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_asistente);

        final Intent intent = getIntent();
        valor_dni = intent.getStringExtra("docu");
        System.out.println("Documento: "+valor_dni);

        btnGetUsersList = (Button) findViewById(R.id.btnGetUsersList);
        btnGetUser = (Button) findViewById(R.id.btnGetUser);
        btnAddUser = (Button) findViewById(R.id.btnAddUser);

        listView = (ListView) findViewById(R.id.listView);
        userService = APIUtils.getUserService();

        btnGetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("BOTON");
                getUser(valor_dni);
            }
        });

        btnGetUsersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("BOTON");
                getUsersList();
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_agregar = new Intent(BuscarAsistente.this, AgregarUsuario.class);
                intent_agregar.putExtra("user_name","");
                startActivity(intent_agregar);
            }
        });
    }


    public void getUsersList(){
        System.out.print("getUsersList ");
        //Call<List<User>> call = userService.getUsers();
        Call<List<User>> call = userService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listView.setAdapter(new UserAdapter(BuscarAsistente.this, R.layout.list_user, list));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    public void getUser(String dni){
        System.out.print("getUser "+dni);
        //Call<List<User>> call = userService.getUsers();
        Call<User> call = userService.getUser(Integer.parseInt(dni));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                System.out.println("response.isSuccessful(): ");
                if(response.isSuccessful()){
                    System.out.println("response.isSuccessful(): ");
                    if(response.body() !=null) {

                        System.out.println(response.body().toString());
                        System.out.println(response.body().getFirstName());

                    }else{

                        Toast.makeText(getApplicationContext(), "No esta Registrado el DNI",
                                Toast.LENGTH_LONG).show();
                    }

                    //list = response.body();
                    //listView.setAdapter(new UserAdapter(BuscarAsistente.this, R.layout.list_user, list));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERROR catch: ", t.getMessage());
            }
        });

    }

}


