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

    public Medida() {
    }

    public void inserirMedidas(Integer id, Double bandaLarga) {

        ConexaoAzure conexaoA = new ConexaoAzure();
        JdbcTemplate conA = conexaoA.getConnection();

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

        List<RedeInterface> redeInterfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();
        Long redeEnviados = 0L;
        Long redeRecebidos = 0L;
        
        for (RedeInterface rede : redeInterfaces) {
            redeEnviados += rede.getBytesEnviados();
            redeRecebidos += rede.getBytesRecebidos();
        }

        Double capacidade = bandaLarga;

        Long taxaTransferencia = (redeEnviados + redeRecebidos) * 5 / 8;
        String frequenciaFormatada = new DecimalFormat(",##").format(taxaTransferencia / Math.pow(2, 20));
        Double usoRede = Double.parseDouble(frequenciaFormatada);

        Double percentualRede = usoRede * 100 / capacidade;

        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,1)",
                usoCPU,
                dataHora,
                id
        );
        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,2)",
                porcentagemUsoRam,
                dataHora,
                id
        );

        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,3)",
                porcentagemDisponivel,
                dataHora,
                id
        );

        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,4)",
                percentualRede,
                dataHora,
                id
        );

    }

}
