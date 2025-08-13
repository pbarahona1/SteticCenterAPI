package BuSmart.APIBuSmart.Exceptions.ExcepCliente;

public class ExcepcionClienteDuplicado extends RuntimeException {
    private String campoDuplicado;

    public ExcepcionClienteDuplicado(String message, String campoDuplicado) {
        super(message);
        this.campoDuplicado = campoDuplicado;
    }

    public String getCampoDuplicado() {
        return campoDuplicado;
    }
}