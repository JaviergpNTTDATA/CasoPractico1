package org.javinttdata.cliente.model;

import org.javinttdata.common.Globales;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Entidad Cliente
 */
public class Cliente {

    private boolean bandera = false;//Bandera para validar campos vacios
    private static final Pattern PATRON_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");//Patron que valida el email

    //Propiedades
    private long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;

    /**
     * Constructor
     * @param nombre
     * @param apellidos
     * @param dni
     * @param email
     * @param telefono
     */
    public Cliente(String nombre, String apellidos, String dni, String email, String telefono) {
        setNombre(nombre);
        setApellidos(apellidos);
        setDni(dni);
        setEmail(email);
        setTelefono(telefono);
    }

    public Cliente() {

    }

    //Getter y Setters

    // El ID solo lo asigna el Repository
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        campoObligatorio(nombre, "Nombre");
        this.nombre = nombre.trim();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        campoObligatorio(apellidos, "Apellidos");
        this.apellidos = apellidos.trim();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        campoObligatorio(dni, "dni");
        this.dni = dni.trim().toUpperCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        campoObligatorio(email, "Email");
        email = email.trim().toLowerCase();

        if (!PATRON_EMAIL.matcher(email).matches() && !bandera) {
            throw new IllegalArgumentException("Patron de email no valido\nVolviendo al menu");
        }

        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        campoObligatorio(telefono, "Teléfono");
        this.telefono = telefono.trim();
    }

    /**
     * Metodo que comprueba que el dato introducido no este vacio
     * @param valor valor introducido
     * @param campo campo que se intenta asignar
     */
    private void campoObligatorio(String valor, String campo) {
        if (Objects.isNull(valor) && !bandera || valor.trim().isEmpty() && !bandera) {
            //Pongo lo de la bandera para que no salten mas de una vez un error de campo vacio, con uno ya se sabe que estan mas introducido los datos
            this.bandera = true;
            throw new IllegalArgumentException("ERROR: El campo " + campo.toUpperCase() + " no puede estar vacio\nVolviendo al menu");
        }
    }

    /**
     * Metodo toString
     * @return devuelve una cadena con los datos del cliente
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
