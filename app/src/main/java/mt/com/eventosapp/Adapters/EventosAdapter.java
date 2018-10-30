package mt.com.eventosapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mt.com.eventosapp.R;
import mt.com.eventosapp.modelo.Evento;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.MyViewHolder>{


    public List<Evento> listaEventos;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo;

        public MyViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.titulo);

        }
    }

    public EventosAdapter(List<Evento> eventosList) {
        this.listaEventos = eventosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eventos_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Evento evento = listaEventos.get(position);
        holder.titulo.setText(evento.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }
}
