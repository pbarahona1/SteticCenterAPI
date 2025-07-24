package BuSmart.APIBuSmart.Exceptions.ExcepUsuarios;

import lombok.Getter;

public class ExcepcionEncargadoDuplicados extends RuntimeException {


  @Getter
  private String campoDuplicado;

  public ExcepcionEncargadoDuplicados(String message) {
        super(message);
    }

  public ExcepcionEncargadoDuplicados(String message, String campoDuplicado) {
    super(message);
    this.campoDuplicado = campoDuplicado;
  }
}
