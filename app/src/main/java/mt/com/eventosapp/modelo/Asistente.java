package mt.com.eventosapp.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Asistente {

    @SerializedName("dni")
    @Expose
    private Long dni;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("provincia")
    @Expose
    private String provincia;

    @SerializedName("localidad")
    @Expose
    private String localidad;

    @SerializedName("jurisdiccion")
    @Expose
    private String jurisdiccion;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("celular_1")
    @Expose
    private String celular_1;

    @SerializedName("celular_2")
    @Expose
    private String celular_2;

    @SerializedName("email")
    @Expose
    private String email;

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

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getJurisdiccion() {
        return jurisdiccion;
    }

    public void setJurisdiccion(String jurisdiccion) {
        this.jurisdiccion = jurisdiccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular_1() {
        return celular_1;
    }

    public void setCelular_1(String celular_1) {
        this.celular_1 = celular_1;
    }

    public String getCelular_2() {
        return celular_2;
    }

    public void setCelular_2(String celular_2) {
        this.celular_2 = celular_2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
