package mt.com.eventosapp.lectordni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mt.com.eventosapp.Adapters.EventosAdapter;
import mt.com.eventosapp.Adapters.RecyclerTouchListener;
import mt.com.eventosapp.Adapters.UserAdapter;
import mt.com.eventosapp.MainActivity;
import mt.com.eventosapp.R;
import mt.com.eventosapp.modelo.Evento;
import mt.com.eventosapp.modelo.User;
import mt.com.eventosapp.remote.APIUtils;
import mt.com.eventosapp.remote.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionaEvento extends AppCompatActivity {

    private List<Evento> eventosList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventosAdapter mAdapter;
    UserService userService;

    private View mProgressViewEvento;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_evento);
        System.out.println("SeleccionaEvento: " );

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //recyclerView.setAdapter(mAdapter);

        mProgressViewEvento = findViewById(R.id.selecciona_progress);


        System.out.println("SeleccionaEvento: 2" );
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        System.out.println("Shared id_usuario: "+pref.getLong("id_usuario", 1) );
        System.out.println("Shared 2 id_grupo: "+pref.getLong("id_grupo", 1) );




        mAdapter = new EventosAdapter(eventosList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //System.out.println("SeleccionaEvento: 3 " );
        userService = APIUtils.getUserService();

        //System.out.println("SeleccionaEvento: 4" );

        getEventosList();
       // System.out.println("SeleccionaEvento: 5" );

    }

    public void getEventosList(){
        System.out.println("getEventosList ");
        //Call<List<User>> call = userService.getUsers();
        Call<List<Evento>> call = userService.getEventos();
        mProgressViewEvento.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
              //  System.out.println("getEventosList3 "+response);
                mProgressViewEvento.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    System.out.println("getEventosList 4 "+response.body());
                    eventosList = response.body();
                    mAdapter = new EventosAdapter(eventosList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(SeleccionaEvento.this, LinearLayoutManager.VERTICAL));

                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    // row click listener
                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Evento evento = eventosList.get(position);
                            Toast.makeText(getApplicationContext(), evento.getDescripcion() + " id Grupo: "+ evento.getIdEvento() , Toast.LENGTH_SHORT).show();


                            pref.edit().putLong("id_evento",evento.getIdEvento()).commit();
                            pref.edit().putString("evento",evento.getDescripcion()).commit();

                            Intent intent_selecciona = new Intent(getBaseContext(), MainActivity.class);

                            startActivity(intent_selecciona);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));


                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                mProgressViewEvento.setVisibility(View.GONE);
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


}
