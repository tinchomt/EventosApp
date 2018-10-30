package mt.com.eventosapp.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Evento {

    @SerializedName("id_evento")
    @Expose
    private Integer idEvento;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("fecha_inicio")
    @Expose
    private String fechaInicio;
    @SerializedName("fecha_fin")
    @Expose
    private String fechaFin;
    @SerializedName("id_grupo")
    @Expose
    private Integer idGrupo;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

}