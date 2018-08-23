package mt.com.eventosapp.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegistraEvento implements Serializable{

   /* @SerializedName("dni")
    @Expose
    private Long id;*/

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("dni")
    @Expose
    private Long dni;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("id_evento")
    @Expose
    private Long id_evento;

    @SerializedName("id_usuario")
    @Expose
    private Long id_usuario;

    @SerializedName("id_estado")
    @Expose
    private Long id_estado;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId_evento() {
        return id_evento;
    }

    public void setId_evento(Long id_evento) {
        this.id_evento = id_evento;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_estado() {
        return id_estado;
    }

    public void setId_estado(Long id_estado) {
        this.id_estado = id_estado;
    }



}
