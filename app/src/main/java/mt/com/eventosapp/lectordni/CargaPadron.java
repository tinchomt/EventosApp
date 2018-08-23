package mt.com.eventosapp.lectordni;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;

import mt.com.eventosapp.R;

public class CargaPadron extends AppCompatActivity {

    String valores;
    Map<String, String> mapaResultados = new TreeMap<String, String>();
    String mostrar_valores;
    TextView valoresDNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_padron);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        valoresDNI = (TextView)findViewById(R.id.valoresDNI);

        Intent intent = getIntent();
        valores = intent.getStringExtra("valores");
        System.out.println("Resultados: "+valores);

        parserResultados(valores);

        mostrar_valores = "DNI: "+mapaResultados.get("dni")+"\n"+
                "SEXO: "+mapaResultados.get("sexo")+"\n"+
                "APELLIDO: "+mapaResultados.get("apellido")+"\n"+
                "NOMBRE: "+mapaResultados.get("nombre")+"\n"+
                "FECHA NACIMIENTO: "+mapaResultados.get("f_nacimiento");


        valoresDNI.setText(mostrar_valores);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    //00196390135@THEILER@MARTIN@M@27898586@A@25/03/1980@05/06/2013

    public void parserResultados(String valores){

        String[] val = valores.split("@");
        mapaResultados.put("apellido",val[1]);
        mapaResultados.put("nombre",val[2]);
        mapaResultados.put("sexo",val[3]);
        mapaResultados.put("dni",val[4]);
        mapaResultados.put("f_nacimiento",val[6]);

    }


}
