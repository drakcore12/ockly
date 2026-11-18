package co.edu.pascualbravo.ockly.domain.exception;

public class DatosInvalidosException extends IllegalArgumentException {
    public DatosInvalidosException(String mensaje) { super(mensaje); }
}
