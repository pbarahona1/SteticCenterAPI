package BuSmart.APIBuSmart.Exceptions.ExcepProducto;

public class ExcepcionProductoDuplicado extends RuntimeException {
    private String campoDuplicado;

    public ExcepcionProductoDuplicado(String campoDuplicado, String message) {
        super(message);
        this.campoDuplicado = campoDuplicado;
    }

    public String getCampoDuplicado() {
        return campoDuplicado;
    }
}