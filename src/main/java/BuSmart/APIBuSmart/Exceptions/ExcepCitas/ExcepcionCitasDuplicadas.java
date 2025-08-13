package BuSmart.APIBuSmart.Exceptions.ExcepCitas;

public class ExcepcionCitasDuplicadas extends RuntimeException {
    private String campoDuplicado;

    public ExcepcionCitasDuplicadas(String mensaje, String campoDuplicado) {
        super(mensaje);
        this.campoDuplicado = campoDuplicado;
    }

    public String getCampoDuplicado() {
        return campoDuplicado;
    }
}
