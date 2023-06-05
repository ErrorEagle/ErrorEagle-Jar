package models;

import java.time.LocalTime;

public class MedidaRetorno {

    private Double percentual;
    private String hora;

    public MedidaRetorno(Double percentual, String hora) {
        this.percentual = percentual;
        this.hora = hora;
    }

    public MedidaRetorno() {
    }

    public Double getPercentual() {
        return percentual;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return String.format("MedidaRetorno{ percentual=%.3f, hora='%s'}\n", percentual, hora);
    }
    }

