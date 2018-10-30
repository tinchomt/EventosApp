package mt.com.eventosapp.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable{

    @SerializedName("id_usuario")
    @Expose
    private Long id_usuario;

    @SerializedName("dni")
    @Expose
    private Long dni;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("clave")
    @Expose
    private String clave;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;


    @SerializedName("id_grupo")
    @Expose
    private Long id_grupo;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(Long id_grupo) {
        this.id_grupo = id_grupo;
    }



}
