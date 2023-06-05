/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;

/**
 *
 * @author fernandes
 */
public class Tempo {

  private LocalDateTime dataHora;

  public Tempo(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }

  public Tempo() {

  }

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }

  @Override
  public String toString() {
    return "Tempo{" + "dataHora=" + dataHora + '}';
  }

}
