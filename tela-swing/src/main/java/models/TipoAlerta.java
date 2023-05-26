/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.github.britooo.looca.api.core.Looca;
import conexoes.ConexaoAzure;
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
    
    
    public void validarTiposAlertas(){

        ConexaoAzure conexaoA = new ConexaoAzure();
        JdbcTemplate conA = conexaoA.getConnection();

        listaAlertas = new ArrayList();

        listaAlertas = conA.query("select * from TipoAlerta",
                new BeanPropertyRowMapper(TipoAlerta.class));

        if (listaAlertas.isEmpty()) {
            System.out.println("Vou cadastrar tipos de alertas na Azure");
            
            conA.update("insert into TipoAlerta(Criticidade) values ('Ideal'), ('Atenção'), ('Urgente'), ('Crítico')");
            
            validarTiposAlertas();

        } else {
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
                conA.update("insert into TipoAlerta(Criticidade) values ('Ideal')");
            }
            if (hasAtencao) {
                conA.update("insert into TipoAlerta(Criticidade) values ('Atenção')");
            }
            if (hasUrgente) {
                conA.update("insert into TipoAlerta(Criticidade) values ('Urgente')");
            }
            if (hasCritico) {
                conA.update("insert into TipoAlerta(Criticidade) values ('Crítico')");
            }
            
        }
        
        
        conexaoA.closeConnection();
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
