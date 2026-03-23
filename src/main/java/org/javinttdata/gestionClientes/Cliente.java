package org.javinttdata.gestionClientes;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class Cliente {

    //Aqui hacemos los hashset para hacer las comprobaciones de que no existan ya los dnis,emails y telefonos
    private static final Set<String> DNIS_REGISTRADOS = new HashSet<>();
    private static final Set<String> EMAILS_REGISTRADOS = new HashSet<>();
    private static final Set<String> TELEFONOS_REGISTRADOS = new HashSet<>();
    private static final Pattern PATRON_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    //private static final Pattern TELEFONO_PATTERN = Pattern.compile("^(\\+34|0034)?[\\s-]?[6-9](\\d[\\s-]?){8}$");
    //Pongo solo el patron del numero español, esto se adaptaria despues a cualquier tipo de nuemero con un desplegable

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
        this.apellidos = apellidos;//Aqui yo hariamos dos campos apellidos y despues uniria en uno aparte ambos, pero no me quiero salir del enunciado
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        campoObligatorio(dni, "dni");
        dni = dni.trim().toUpperCase();

        if (DNIS_REGISTRADOS.contains(dni)) {//Comprobamos que no exista un cliente con ese DNI
            throw new IllegalArgumentException("ERROR : Ya existe un cliente con el DNI " + dni.toUpperCase());
        }

        if (this.dni != null) {//Tras investigar en internet, tambien puedes cambiar de dni, por lo que la opcion tiene que estar activa
            DNIS_REGISTRADOS.remove(this.dni);
        }

        DNIS_REGISTRADOS.add(dni);
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        campoObligatorio(email, "Email");
        email = email.trim().toLowerCase();

        if (!PATRON_EMAIL.matcher(email).matches()) {//Comprobamos que el email tenga un formato correcto, SOLO SE EVALUA EN ESTE CAMPO POR PARTE DEL ENUNCIADO
            throw new IllegalArgumentException("Formato de EMAIL no valido.");
        }

        if (EMAILS_REGISTRADOS.contains(email)) {//Comprobamos que no exista un cliente con ese email
            throw new IllegalArgumentException("ERROR : Ya existe un cliente con el EMAIL " + email.toUpperCase());
        }

        if (this.email != null) {//Este if es para eliminar el antiguo telefono por si en algun momento el cliente cambia de telefono
            EMAILS_REGISTRADOS.remove(this.email);
        }

        EMAILS_REGISTRADOS.add(email);
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        campoObligatorio(telefono, "Teléfono");//Comprobamos que no este vacio el campo
        telefono = telefono.trim();

        if (TELEFONOS_REGISTRADOS.contains(telefono)) {//Comprobamos que no exista un cliente con ese telefono
            throw new IllegalArgumentException("ERROR : Ya existe un cliente con el TELEFONO " + telefono);
        }

        if (this.telefono != null) {//Este if es para eliminar el antiguo telefono por si en algun momento el cliente cambia de telefono
            TELEFONOS_REGISTRADOS.remove(this.telefono);
        }

        TELEFONOS_REGISTRADOS.add(telefono);
        this.telefono = telefono;
    }

    private void campoObligatorio(String valor, String campo) {
        if (Objects.isNull(valor) || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El campo " + campo.toUpperCase() + " no puede estar vacio");
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
