package mt.com.eventosapp.lectordni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mt.com.eventosapp.R;
import mt.com.eventosapp.modelo.User;
import mt.com.eventosapp.remote.APIUtils;
import mt.com.eventosapp.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarUsuario extends AppCompatActivity {

    UserService userService;
    EditText edtUId;
    EditText edtUsername,edtApellido,edtEmail;
    Button btnSave;
    Button btnDel;
    TextView txtUId,txtUUsername,txtUApellido,txtUEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        setTitle("Alta Usuarios");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUId = (TextView) findViewById(R.id.txtUId);
        edtUId = (EditText) findViewById(R.id.edtUId);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtApellido = (EditText) findViewById(R.id.edtApellido);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        userService = APIUtils.getUserService();
        Bundle extras = getIntent().getExtras();
        final String userId = extras.getString("user_id");
        String userName = extras.getString("user_name");
        String userApellido = extras.getString("user_apellido");
        String userEmail = extras.getString("user_email");


        edtUId.setText(userId);
        edtUsername.setText(userName);
        edtApellido.setText(userApellido);
        edtEmail.setText(userEmail);

        if(userId != null && userId.trim().length() > 0 ){
            edtUId.setFocusable(false);
        } else {
            txtUId.setVisibility(View.INVISIBLE);
            edtUId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Agregar ");
                User u = new User();
                u.setFirstName(edtUsername.getText().toString());
                u.setLastName(edtApellido.getText().toString());
                u.setEmail(edtEmail.getText().toString());

                if(userId != null && userId.trim().length()>0){
                    System.out.println("Agregar 1");
                    //update user
                    updateUser(Integer.parseInt(userId), u);
                }else{
                    System.out.println("Agregar 2");
                    addUser(u);
                }


            }
        });
    }


    public void addUser(User u){

        System.out.println("Agregar "+u);

        Call<User> call = userService.addUser(u);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    Toast.makeText(AgregarUsuario.this, "Se Agrego Correctamente!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Log.e("ERROR Add: ", t.getMessage());
            }
        });
    }


    public void updateUser(int id, User u){

        Call<User> call = userService.updateUser(id,u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){

                    Toast.makeText(AgregarUsuario.this, "Se Actualizo Correctamente!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }



}
