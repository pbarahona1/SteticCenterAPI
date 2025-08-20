package BuSmart.APIBuSmart.Exceptions.ExcepPaquetes;

public class ExcepcionPaquetesDuplicado extends RuntimeException {
    private String campoDuplicado;

    public ExcepcionPaquetesDuplicado(String campoDuplicado, String message) {
        super(message);
        this.campoDuplicado = campoDuplicado;
    }

    public String getCampoDuplicado() {
        return campoDuplicado;
    }
}