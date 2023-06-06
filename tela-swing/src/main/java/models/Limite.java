/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import conexao.ConexaoAzure;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author LUCAS
 */
public class Limite {

    private Integer fkComponente;
    private Double maximo;
    private Double minimo;
    private String criticidade;
    private List<Limite> listaLimites;
    
    Log log = new Log();

    public Limite() {
    }

    public void validarLimites(JdbcTemplate conA, JdbcTemplate conL) {

        validarLimiteIdeal(conA, conL);
        validarLimiteAtencao(conA, conL);
        validarLimiteUrgente(conA, conL);
        validarLimiteCritico(conA, conL);

    }

    public void validarLimiteIdeal(JdbcTemplate conA, JdbcTemplate conL) {

        listaLimites = new ArrayList();

        listaLimites = conA.query("select * from Limite where fkTipoAlerta = 1", new BeanPropertyRowMapper(Limite.class));

        if (listaLimites.isEmpty()) {

            Configuracao cf = new Configuracao();
            // Cadastrar os limites dos componentes
            log.writeRecordToLogFile("Inserindo Limites Ideis aos Componentes...");
            conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 1, 50.0, 0.0), (1, 2, 50.0, 0.0), (1, 3, 50.0, 0.0), (1, 4, 50.0, 0.0)");
            conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 1, 50.0, 0.0), (1, 2, 50.0, 0.0), (1, 3, 50.0, 0.0), (1, 4, 50.0, 0.0)");

            validarLimiteIdeal(conA, conL);

        } else {
            log.writeRecordToLogFile("Verificando se todos os Limites Ideais foram cadastrados...");
            Boolean hasCPU = true, hasDisco = true, hasRAM = true, hasRede = true;

            for (Limite LimiteIdeal : listaLimites) {
                Integer componente = LimiteIdeal.getFkComponente();

                switch (componente) {
                    case 1:
                        hasCPU = false;
                        break;
                    case 2:
                        hasRAM = false;
                        break;
                    case 3:
                        hasDisco = false;
                        break;
                    case 4:
                        hasRede = false;
                        break;
                }
            }

            if (hasCPU) {
                log.writeRecordToLogFile("Cadastrando Limite da CPU");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 1, 50.0, 0.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 1, 50.0, 0.0)");
            }
            if (hasRAM) {
                log.writeRecordToLogFile("Cadastrando Limite da RAM");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 2, 50.0, 0.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 2, 50.0, 0.0)");
            }
            if (hasDisco) {
                log.writeRecordToLogFile("Cadastrando Limite da DISCO");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 3, 50.0, 0.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 3, 50.0, 0.0)");
            }
            if (hasRede) {
                log.writeRecordToLogFile("Cadastrando Limite da REDE");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 4, 50.0, 0.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 4, 50.0, 0.0)");
            }

        }
        
    }

    public void validarLimiteAtencao(JdbcTemplate conA, JdbcTemplate conL) {

        listaLimites = new ArrayList();
        log.writeRecordToLogFile("Verificando se já existem Limites de Atenção...");
        listaLimites = conA.query("select * from Limite where fkTipoAlerta = 2", new BeanPropertyRowMapper(Limite.class));

        if (listaLimites.isEmpty()) {

            Configuracao cf = new Configuracao();
            // Cadastrar os limites dos componentes
            log.writeRecordToLogFile("Cadastrando Limites de Atenção");
            conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 1, 75.0, 50.0), (2, 2, 75.0, 50.0), (2, 3, 75.0, 50.0), (2, 4, 75.0, 50.0)");
            conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 1, 75.0, 50.0), (2, 2, 75.0, 50.0), (2, 3, 75.0, 50.0), (2, 4, 75.0, 50.0)");

            validarLimiteIdeal(conA, conL);

        } else {
            log.writeRecordToLogFile("Verificando se Limites de Atenção foram cadastrados...");
            Boolean hasCPU = true, hasDisco = true, hasRAM = true, hasRede = true;

            for (Limite LimiteAtencao : listaLimites) {
                Integer componente = LimiteAtencao.getFkComponente();

                switch (componente) {
                    case 1:
                        hasCPU = false;
                        break;
                    case 2:
                        hasRAM = false;
                        break;
                    case 3:
                        hasDisco = false;
                        break;
                    case 4:
                        hasRede = false;
                        break;
                }
            }
            if (hasCPU) {
                log.writeRecordToLogFile("Cadastrando Limite de Alerta da CPU");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 1, 75.0, 50.0");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 1, 75.0, 50.0");
            }
            if (hasRAM) {
                log.writeRecordToLogFile("Cadastrando Limite de Alerta da RAM");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 2, 75.0, 50.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 2, 75.0, 50.0)");
            }
            if (hasDisco) {
                log.writeRecordToLogFile("Cadastrando Limite de Alerta do DISCO");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 3, 75.0, 50.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 3, 75.0, 50.0)");
            }
            if (hasRede) {
                log.writeRecordToLogFile("Cadastrando Limite de Alerta da REDE");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 4, 75.0, 50.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (2, 4, 75.0, 50.0)");
            }

        }
    }

    public void validarLimiteUrgente(JdbcTemplate conA, JdbcTemplate conL) {


        listaLimites = new ArrayList();
        log.writeRecordToLogFile("Verificando se já existem Limites Urgentes");
        listaLimites = conA.query("select * from Limite where fkTipoAlerta = 3", new BeanPropertyRowMapper(Limite.class));

        if (listaLimites.isEmpty()) {
            
            Configuracao cf = new Configuracao();
            // Cadastrar os limites dos componentes
            log.writeRecordToLogFile("Cadastrando Limites de Urgência...");
            conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (3, 1, 90.0, 75.0), (3, 2, 90.0, 75.0), (3, 3, 90.0, 75.0), (3, 4, 90.0, 75.0)");
            conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (3, 1, 90.0, 75.0), (3, 2, 90.0, 75.0), (3, 3, 90.0, 75.0), (3, 4, 90.0, 75.0)");

            validarLimiteIdeal(conA, conL);

        } else {
            log.writeRecordToLogFile("Verificando se os Limites de Urgência foram cadastrados...");
            Boolean hasCPU = true, hasDisco = true, hasRAM = true, hasRede = true;

            for (Limite LimiteUrgente : listaLimites) {
                Integer componente = LimiteUrgente.getFkComponente();

                switch (componente) {
                    case 1:
                        hasCPU = false;
                        break;
                    case 2:
                        hasRAM = false;
                        break;
                    case 3:
                        hasDisco = false;
                        break;
                    case 4:
                        hasRede = false;
                        break;
                }
            }

            if (hasCPU) {
                log.writeRecordToLogFile("Cadastrando Limite Urgente da CPU");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 1, 90.0, 75.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 1, 90.0, 75.0)");
            }
            if (hasRAM) {
                log.writeRecordToLogFile("Cadastrando Limite Urgente da RAM");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 2, 90.0, 75.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 2, 90.0, 75.0)");
            }
            if (hasDisco) {
                log.writeRecordToLogFile("Cadastrando Limite Urgente do DISCO");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 3, 90.0, 75.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 3, 90.0, 75.0)");
            }
            if (hasRede) {
                log.writeRecordToLogFile("Cadastrando Limite Urgente da REDE");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 4, 90.0, 75.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (1, 4, 90.0, 75.0)");
            }

        }

    }

    public void validarLimiteCritico(JdbcTemplate conA, JdbcTemplate conL) {

        listaLimites = new ArrayList();
        log.writeRecordToLogFile("Verificando se Limites Criticos estão cadastrados...");
        listaLimites = conA.query("select * from Limite where fkTipoAlerta = 4", new BeanPropertyRowMapper(Limite.class));

        if (listaLimites.isEmpty()) {
            log.writeRecordToLogFile("Cadastrando Limites Criticos...");
            Configuracao cf = new Configuracao();
            // Cadastrar os limites dos componentes

            conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 1, 100.0, 90.0), (4, 2, 100.0, 90.0), (4, 3, 100.0, 90.0), (4, 4, 100.0, 90.0)");
            conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 1, 100.0, 90.0), (4, 2, 100.0, 90.0), (4, 3, 100.0, 90.0), (4, 4, 100.0, 90.0)");

            validarLimiteIdeal(conA, conL);

        } else {
            log.writeRecordToLogFile("Verificando se Limites Criticos foram cadastrados...");
           Boolean hasCPU = true, hasDisco = true, hasRAM = true, hasRede = true;

            for (Limite limiteCritico : listaLimites) {
                Integer componente = limiteCritico.getFkComponente();

                switch (componente) {
                    case 1:
                        hasCPU = false;
                        break;
                    case 2:
                        hasRAM = false;
                        break;
                    case 3:
                        hasDisco = false;
                        break;
                    case 4:
                        hasRede = false;
                        break;
                }
            }
            
            if (hasCPU) {
                log.writeRecordToLogFile("Cadastrando Limite Critico da CPU");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 1, 100.0, 90.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 1, 100.0, 90.0)");
            }
            if (hasRAM) {
                log.writeRecordToLogFile("Cadastrando Limite Critico da RAM");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 2, 100.0, 90.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 2, 100.0, 90.0)");
            }
            if (hasDisco) {
                log.writeRecordToLogFile("Cadastrando Limite Critico do DISCO");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 3, 100.0, 90.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 3, 100.0, 90.0)");
            }
            if (hasRede) {
                log.writeRecordToLogFile("Cadastrando Limite Critico da REDE");
                conA.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 4, 100.0, 90.0)");
                conL.update("insert into Limite(fkTipoAlerta, fkComponente, maximo, minimo) values (4, 4, 100.0, 90.0)");
            }

        }
      
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public String getCriticidade() {
        return criticidade;
    }

    public void setCriticidade(String criticidade) {
        this.criticidade = criticidade;
    }
    
    @Override
    public String toString() {
        return "Limite{" + "maximo=" + maximo + ", minimo=" + minimo + '}';
    }

}
