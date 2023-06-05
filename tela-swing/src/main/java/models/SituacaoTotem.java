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
public class SituacaoTotem {

  private String mensagem;
  private Double media;
  private LocalDateTime datahora;
  private Integer fkTotem;
  private  Integer fkComponente;

  public SituacaoTotem(String mensagem, Double media, Integer fkTotem, Integer fkComponente) {
    this.mensagem = mensagem;
    this.media = media;
    this.datahora = LocalDateTime.now();
    this.fkTotem = fkTotem;
    this.fkComponente = fkComponente;
  }
  public SituacaoTotem() {
    }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public Double getMedia() {
    return media;
  }

  public void setMedia(Double media) {
    this.media = media;
  }

  public LocalDateTime getDatahora() {
    return datahora;
  }

  public void setDatahora(LocalDateTime datahora) {
    this.datahora = datahora;
  }

  public Integer getFkTotem() {
    return fkTotem;
  }

  public void setFkTotem(Integer fkTotem) {
    this.fkTotem = fkTotem;
  }

  public Integer getFkComponente() {
    return fkComponente;
  }

  public void setFkComponente(Integer fkComponente) {
    this.fkComponente = fkComponente;
  }

  @Override
  public String toString() {
    return String.format("SituacaoTotem{mensagem=%s, media=%.2f, datahora=%s, fkTotem=%d, fkComponente=%d}", mensagem, media, datahora, fkTotem, fkComponente);
  }
}
