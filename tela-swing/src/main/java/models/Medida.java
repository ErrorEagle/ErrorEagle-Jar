/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import conexoes.ConexaoAzure;
import conexoes.ConexaoLocal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author LUCAS
 */
public class Medida {

    Looca looca = new Looca();
    private Double usoCPU = 0.0, usoRAM = 0.0, usoDisco = 0.0, usoRede = 0.0;
    private List<Medida> listaMedidas;
    
    Log log = new Log();

    public Medida() {
    }

    public void inserirMedidas(Integer id, Double bandaLarga) {

        ConexaoAzure conexaoA = new ConexaoAzure();
        JdbcTemplate conA = conexaoA.getConnection();
        ConexaoLocal conexaoL = new ConexaoLocal();
        JdbcTemplate conL = conexaoL.getConnection();

        LocalDateTime dataHora = LocalDateTime.now();

        usoCPU = looca.getProcessador().getUso();

        usoRAM = looca.getMemoria().getEmUso().doubleValue();
        Double totalRam = looca.getMemoria().getTotal().doubleValue();

        DiscoGrupo discoGrupo = new DiscoGrupo();
        Double volumeDisponivel = 0.0;
        Double volumeTotal = 0.0;
        List<Volume> volumes = discoGrupo.getVolumes();

        for (Volume volume : volumes) {
            if (volume.getNome().equals("/") || volume.getNome().contains("C:")) {  
                volumeDisponivel += volume.getDisponivel().doubleValue();
                volumeTotal += volume.getTotal().doubleValue();
            }
        }

        volumeDisponivel /= Math.pow(2, 30);
        volumeTotal /= Math.pow(2, 30);

        Double porcentagemDisponivel = (volumeDisponivel / volumeTotal) * 100;
        Double porcentagemUsoRam = (usoRAM / totalRam) * 100;

        
        // Os Bytes são cumulativos, ou seja, sempre aumenta, temos que pegar [tmp1] sobe, [temp1, temp2] (2 - 1) sobe e shifta, [temp2, temp3]... assim temos o minimo de rede que usou;
        List<RedeInterface> redeInterfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();
        Long redeEnviados = 0L;
        Long redeRecebidos = 0L;
        
        for (RedeInterface rede : redeInterfaces) {
            redeEnviados += rede.getBytesEnviados();
            redeRecebidos += rede.getBytesRecebidos();
        }

        Double capacidade = bandaLarga;

        Long taxaTransferencia = (redeEnviados + redeRecebidos) / 8;
        String frequenciaFormatada = String.format("%.2f", taxaTransferencia / Math.pow(2, 20));
        System.out.println(frequenciaFormatada);        frequenciaFormatada = frequenciaFormatada.replace(",", ".");
        Double usoRede = Double.parseDouble(frequenciaFormatada);

        Double percentualRede = usoRede * 100 / capacidade;
        log.writeRecordToLogFile("Inserindos Medida da CPU");
        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,1)",
                usoCPU,
                dataHora,
                id
        );
        conL.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,1)",
                usoCPU,
                dataHora,
                id
        );
        
        
        log.writeRecordToLogFile("Inserindos Medida da RAM");
        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,2)",
                porcentagemUsoRam,
                dataHora,
                id
        );
        conL.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,2)",
                porcentagemUsoRam,
                dataHora,
                id
        );
        
        

        log.writeRecordToLogFile("Inserindos Medida do DISCO");
        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,3)",
                porcentagemDisponivel,
                dataHora,
                id
        );
        conL.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,3)",
                porcentagemDisponivel,
                dataHora,
                id
        );
        
        

        log.writeRecordToLogFile("Inserindos Medida da REDE");
        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,4)",
                percentualRede,
                dataHora,
                id
        );
        conL.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,4)",
                percentualRede,
                dataHora,
                id
        );

    }

}
