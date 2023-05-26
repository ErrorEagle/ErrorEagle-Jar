/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author LUCAS
 */
public class Empresa {
    private Integer statusEmpresa;
    private Double  bandaLarga;

    public Empresa() {
    }

    public Integer getStatusEmpresa() {
        return statusEmpresa;
    }

    public void setStatusEmpresa(Integer statusEmpresa) {
        this.statusEmpresa = statusEmpresa;
    }

    public Double getBandaLarga() {
        return bandaLarga;
    }

    public void setBandaLarga(Double bandaLarga) {
        this.bandaLarga = bandaLarga;
    }
    
    @Override
    public String toString() {
        return "Empresa{" + "statusEmpresa=" + statusEmpresa + '}';
    }

    
}
