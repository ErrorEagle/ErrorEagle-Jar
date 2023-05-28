/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import conexoes.ConexaoAzure;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author LUCAS
 */
public class Configuracao {

    Looca looca = new Looca();
    Log log = new Log();

    private List<Configuracao> listaConfiguracao;
    private Double capacidade;
    private String unidadeMedida;
    private Integer fkComponente;

    public Configuracao() {

    }

    public Double getCapacidadeDisco() {
        Long tamanho = looca.getGrupoDeDiscos().getTamanhoTotal();

        String frequenciaFormatada = new DecimalFormat(",##").format(tamanho / Math.pow(2, 30));
        Double capacidade = Double.parseDouble(frequenciaFormatada);

        return capacidade;
    }

    public Double getCapacidadeCPU() {

        Long frequencia = looca.getProcessador().getFrequencia();

        String frequenciaFormatada = new DecimalFormat(",##").format(frequencia * Math.pow(10, -9));
        Double capacidade = Double.parseDouble(frequenciaFormatada);

        return capacidade;
    }

    public Double getCapacidadeRAM() {

        Long totalRAM = looca.getMemoria().getTotal();

        String frequenciaFormatada = new DecimalFormat(",##").format(totalRAM / Math.pow(2, 30));
        Double capacidade = Double.parseDouble(frequenciaFormatada);

        return capacidade;
    }

    public void validarConfiguracao(Integer idTotem, Double bandaLarga) {
        log.writeRecordToLogFile("Validando Configuração...");
        listaConfiguracao = new ArrayList();

        Configuracao config = new Configuracao();

        ConexaoAzure conexaoA = new ConexaoAzure();
        JdbcTemplate conA = conexaoA.getConnection();

        DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
        List<Disco> discos = grupoDeDiscos.getDiscos();

        listaConfiguracao = conA.query("select * from Configuracao where fkTotem = ?", new BeanPropertyRowMapper(Configuracao.class), idTotem);

        if (listaConfiguracao.isEmpty()) {
            log.writeRecordToLogFile("Associando Configuração a este Totem...");
            //Cadastrar CPU
            conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 1, ?, 'GHz')", idTotem, bandaLarga);

            //Cadastrar RAM
            conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 2, ?, 'GB')", idTotem, bandaLarga);

            //Cadastrar Disco
            for (Disco disco : discos) {
                long tamanhoDisco = disco.getTamanho();

                // Converter para GB
                Double tamanhoGB = Math.floor(tamanhoDisco / Math.pow(2, 30));

                // Converter para TB
                Double tamanhoTB = Math.floor(tamanhoGB / Math.pow(2, 10));

                if (tamanhoTB >= 1.0) {
                    conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 3, ?, 'TB')", idTotem, tamanhoTB);
                } else {
                    conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 3, ?, 'GB')", idTotem, tamanhoGB);
                }
            }

            //Cadastrar Rede
            conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 4, ?, 'Mbps')", idTotem, bandaLarga);

            validarConfiguracao(idTotem, bandaLarga);

        } else {
            log.writeRecordToLogFile("Verificando se a Configuração foi salva corretamente...");
            Boolean hasRAM = true, hasCPU = true, hasDisco = true, hasRede = true;
            Boolean updateRAM = true, updateCPU = false, updateDisco = true, updateRede = true;

            for (Configuracao configuracao : listaConfiguracao) {

                Integer fkComponente = configuracao.getFkComponente();
                Double capacidadeAtual = configuracao.getCapacidade();
                switch (fkComponente) {
                    case 1:
                        hasCPU = false;
                        if (capacidadeAtual != config.getCapacidadeCPU()) {
                            updateCPU = true;
                        }
                        break;
                    case 2:
                        hasRAM = false;
                        if (capacidadeAtual != config.getCapacidadeRAM()) {
                            updateRAM = true;
                        }
                        break;
                    case 3:
                        hasDisco = false;
                        if (capacidadeAtual != config.getCapacidadeDisco()) {
                            updateDisco = true;
                        }
                        break;
                    case 4:
                        hasRede = false;
                        if (!capacidadeAtual.equals(bandaLarga)) {
                            updateRede = true;
                        }
                        break;
                }
            }

            if (hasCPU) {
                log.writeRecordToLogFile("Cadastrando Configuracao CPU");
                conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 1, ?, 'GHz')", idTotem, config.getCapacidadeCPU());
            }
            if (hasRAM) {
                log.writeRecordToLogFile("Cadastrando Configuracao RAM");
                conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 2, ?, 'GB')", idTotem, config.getCapacidadeRAM());
            }
            if (hasDisco) {
                for (Disco disco : discos) {
                    long tamanhoDisco = disco.getTamanho();

                    // Converter para GB
                    Double tamanhoGB = Math.floor(tamanhoDisco / Math.pow(2, 30));

                    // Converter para TB
                    Double tamanhoTB = Math.floor(tamanhoGB / Math.pow(2, 10));

                    if (tamanhoTB >= 1.0) {
                        log.writeRecordToLogFile("Cadastrando Configuracao DISCO com medida em TB");
                        conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 3, ?, 'TB')", idTotem, tamanhoTB);
                    } else {
                        log.writeRecordToLogFile("Cadastrando Configuracao DISCO com medida em GB");
                        conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 3, ?, 'GB')", idTotem, tamanhoGB);
                    }
                }
            }
            if (hasRede) {
                log.writeRecordToLogFile("Cadastrando Configuracao REDE");
                conA.update("insert into Configuracao(fkTotem, fkComponente, capacidade, unidadeMedida) values (?, 4, ?, 'Mbps')", idTotem, bandaLarga);
            }

            if (updateCPU) {
                log.writeRecordToLogFile("Atualizando Configuração da CPU");
                conA.update("update Configuracao set capacidade = ? where fkTotem = ? and fkComponente = 1", config.getCapacidadeCPU(), idTotem);
            }
            if (updateRAM) {
                log.writeRecordToLogFile("Atualizando Configuração da RAM");
                conA.update("update Configuracao set capacidade = ? where fkTotem = ? and fkComponente = 2", config.getCapacidadeRAM(), idTotem);
            }
            if (updateDisco) {
                for (Disco disco : discos) {
                    long tamanhoDisco = disco.getTamanho();

                    // Converter para GB
                    Double tamanhoGB = Math.floor(tamanhoDisco / Math.pow(2, 30));

                    // Converter para TB
                    Double tamanhoTB = Math.floor(tamanhoGB / Math.pow(2, 10));

                    if (tamanhoTB >= 1.0) {
                        log.writeRecordToLogFile("Atualizando Configuração do DISCO em TB");
                        conA.update("update Configuracao set capacidade = ?, unidadeMedida = 'TB' where fkComponente = 3 and fkTotem = ? ", tamanhoTB, idTotem);
                    } else {
                        log.writeRecordToLogFile("Atualizando Configuração da DISCO em GB");
                        conA.update("update Configuracao set capacidade = ?, unidadeMedida = 'GB' where fkComponente = 3 and fkTotem = ? ", tamanhoGB, idTotem);
                    }
                }
            }
            if (updateRede) {
                log.writeRecordToLogFile("Atualizando Configuração da REDE");
                conA.update("update Configuracao set capacidade = ? where fkTotem = ? and fkComponente = 4", bandaLarga, idTotem);
            }

        }
        log.writeRecordToLogFile("Cadastro da Configuração concluída!");
        conexaoA.closeConnection();

    }

    public Double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

}
