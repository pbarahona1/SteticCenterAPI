package BuSmart.APIBuSmart.Exceptions.ExcepEspecialista;

public class ExceptionEspecialidadDuplicados extends RuntimeException {
  private String campoDuplicado;

  public ExceptionEspecialidadDuplicados(String campoDuplicado) {
    super("Ya existe un registro con el valor duplicado en el campo: " + campoDuplicado);
    this.campoDuplicado = campoDuplicado;
  }

  public String getCampoDuplicado() {
    return campoDuplicado;
  }
}
