/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.github.britooo.looca.api.core.Looca;
import conexao.ConexaoAzure;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author LUCAS
 */
public class TipoAlerta {
    
    private String criticidade;
    private Integer id;
    private List<TipoAlerta> listaAlertas;
    
    public TipoAlerta() {
    }    
    
    
    public void validarTiposAlertas(JdbcTemplate conA, JdbcTemplate conL){

        Log log = new Log();

        listaAlertas = new ArrayList();
        log.writeRecordToLogFile("Verificando se já tem Tipos de Alerta...");
        listaAlertas = conA.query("select * from TipoAlerta",
                new BeanPropertyRowMapper(TipoAlerta.class));

        if (listaAlertas.isEmpty()) {
            System.out.println("Vou cadastrar tipos de alertas na Azure");
            log.writeRecordToLogFile("Cadastrando Tipos de Alerta na Azure...");
            conA.update("insert into TipoAlerta(Criticidade) values ('Ideal'), ('Atenção'), ('Urgente'), ('Crítico')");
            conL.update("insert into TipoAlerta(Criticidade) values ('Ideal'), ('Atenção'), ('Urgente'), ('Crítico')");
            
            validarTiposAlertas(conA, conL);

        } else {
            log.writeRecordToLogFile("Verificando se Tipos de Alertas foram cadastrados...");
            Boolean hasIdeal = true, hasAtencao = true, hasUrgente = true, hasCritico = true;
            for (TipoAlerta alerta : listaAlertas) {
                Integer id = alerta.getId();
                switch (id) {
                    case 1: hasIdeal = false;
                        break;
                    case 2: hasAtencao = false;
                        break;
                    case 3: hasUrgente = false;
                        break;
                    case 4: hasCritico = false;
                        break;
                }
            }
            
            if (hasIdeal) {
                log.writeRecordToLogFile("Cadastrando Tipo de Alerta Ideal");
                conA.update("insert into TipoAlerta(Criticidade) values ('Ideal')");
                conL.update("insert into TipoAlerta(Criticidade) values ('Ideal')");
            }
            if (hasAtencao) {
                log.writeRecordToLogFile("Cadastrando Tipo de Alerta Atenção");
                conA.update("insert into TipoAlerta(Criticidade) values ('Atenção')");
                conL.update("insert into TipoAlerta(Criticidade) values ('Atenção')");
            }
            if (hasUrgente) {
                log.writeRecordToLogFile("Cadastrando Tipo de Alerta Urgente");
                conA.update("insert into TipoAlerta(Criticidade) values ('Urgente')");
                conL.update("insert into TipoAlerta(Criticidade) values ('Urgente')");
            }
            if (hasCritico) {
                log.writeRecordToLogFile("Cadastrando Tipo de Alerta Critico");
                conA.update("insert into TipoAlerta(Criticidade) values ('Crítico')");
                conL.update("insert into TipoAlerta(Criticidade) values ('Crítico')");
            }
            
        }
        
       
    } 

    
    public String getCriticidade() {
        return criticidade;
    }

    public void setCriticidade(String criticidade) {
        this.criticidade = criticidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List getListaAlertas() {
        return listaAlertas;
    }

    public void setListaAlertas(List listaAlertas) {
        this.listaAlertas = listaAlertas;
    }

    @Override
    public String toString() {
        return "TipoAlerta{" + "criticidade=" + criticidade + ", id=" + id + ", listaAlertas=" + listaAlertas + '}';
    }

}
