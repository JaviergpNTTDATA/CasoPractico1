package org.javinttdata.cliente.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Cliente {

    private boolean bandera = false;
    private static final Pattern PATRON_EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private Integer id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;

    public Cliente(String nombre, String apellidos, String dni, String email, String telefono) {
        setNombre(nombre);
        setApellidos(apellidos);
        setDni(dni);
        setEmail(email);
        setTelefono(telefono);
    }

    // El ID solo lo asigna el Repository
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
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

        if (!PATRON_EMAIL.matcher(email).matches()) {
            System.out.println("Patron de email no valido\nVolviendo al menu");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

    private void campoObligatorio(String valor, String campo) {
        if (Objects.isNull(valor) && !bandera || valor.trim().isEmpty() && !bandera) {
            //Pongo lo de la bandera para que no salten mas de una vez un error de campo vacio, con uno ya se sabe que estan mas introducido los datos
            bandera = true;
            System.out.println("ERROR: El campo " + campo.toUpperCase() + " no puede estar vacio\nVolviendo al menu");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

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
