package mt.com.eventosapp.remote;

import java.util.List;

import mt.com.eventosapp.modelo.Asistente;
import mt.com.eventosapp.modelo.Evento;
import mt.com.eventosapp.modelo.RegistraEvento;
import mt.com.eventosapp.modelo.User;
import mt.com.eventosapp.modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by martin on 10/08/18.
 */

public interface UserService {

    @GET("users/")
    Call<List<User>> getUsers();


    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);

    @POST("users/")
    Call<User> addUser(@Body User user);

    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @DELETE("users/{id}")
    Call<User> deleteUser(@Path("id") int id);


    @POST("asistentes/")
    Call<Asistente> registraAsistente(@Body Asistente asistente);


    @GET("asistentes/{dni}")
    Call<Asistente> getAsistente(@Path("dni") int dni);


    @POST("registrar-acceso/")
    Call<RegistraEvento> registraAcceso(@Body RegistraEvento registra);


    @GET("usuario/{login}/{clave}")
    Call<Usuario> getUsuario(@Path("login") String login,@Path("clave") String clave );


    @GET("eventos/porgrupo/{id_grupo}")
    Call<List<Evento>> getEventos(@Path("id_grupo") int id_grupo);

    @GET("eventos/")
    Call<List<Evento>> getEventos();


}
