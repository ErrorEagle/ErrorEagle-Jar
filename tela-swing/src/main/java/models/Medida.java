/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import conexao.ConexaoAzure;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import static service.Latencia.getLatencia;
import service.SendAlert;

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

    public void inserirMedidas(Integer id, Double bandaLarga, JdbcTemplate conA, JdbcTemplate conL) {

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

        Long latencia = 0L;

        if (getLatencia() != null) {
            latencia = getLatencia();
        }

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
        log.writeRecordToLogFile("Inserindos Medida da RAM");
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
        log.writeRecordToLogFile("Inserindos Medida do DISCO");
        conL.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,3)",
                porcentagemDisponivel,
                dataHora,
                id
        );

        log.writeRecordToLogFile("Inserindos Medida do REDE");
        conA.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,4)",
                latencia,
                dataHora,
                id);
        log.writeRecordToLogFile("Inserindos Medida do REDE");
        conL.update("INSERT INTO Medida (percentual, dataHora, fkTotem, fkComponente) VALUES (?,?,?,4)",
                latencia,
                dataHora,
                id);
    }

    public void salvarAlerta(JdbcTemplate conA, JdbcTemplate conL, Integer id, Integer componente, Double media, String mensagem) {
        SituacaoTotem st = new SituacaoTotem(mensagem, media, id, componente);
        System.out.println(st.toString());
        System.out.println("Select 3 salvando alerta");

        try {
            System.out.println("Salvando Alerta " + st.getMensagem() + " " + st.getFkComponente());
            conA.update("INSERT INTO situacaoTotem (mensagem, media, dataHora, fkTotem, fkComponente) VALUES (?, ?, ?, ?, ?)",
                    st.getMensagem(), st.getMedia(), st.getDatahora(), st.getFkTotem(), st.getFkComponente());
            conL.update("INSERT INTO situacaoTotem (mensagem, media, dataHora, fkTotem, fkComponente) VALUES (?, ?, ?, ?, ?)",
                    st.getMensagem(), st.getMedia(), st.getDatahora(), st.getFkTotem(), st.getFkComponente());
        } catch (DataAccessException e) {
            System.out.println("Erro durante a inserção: " + e.getMessage());
            // Faça algo com o erro, como logar ou tratar de acordo com a sua necessidade
        }
    }

    public void verificarAlerta(JdbcTemplate conA, JdbcTemplate conL, Integer id, Integer componente, Double media) throws IOException {

        String tipoComponente = "";
        String simbolo = "";
        System.out.println("Select 2");
        String SELECT_QUERY = "SELECT l.fkComponente, l.maximo, l.minimo, ta.Criticidade FROM Limite l JOIN TipoAlerta ta ON l.fkTipoAlerta = ta.id WHERE l.fkComponente = ?;";

        List<Limite> limites = conA.query(SELECT_QUERY, new BeanPropertyRowMapper<>(Limite.class), componente);

        switch (componente) {
            case 1:
                tipoComponente = "CPU";
                break;
            case 2:
                tipoComponente = "RAM";
                break;
            case 3:
                tipoComponente = "DISCO";
                break;

        }
        for (Limite limite : limites) {
            if (limite.getMaximo() >= media && limite.getMinimo() <= media) {
                //seta emojis
                switch (limite.getCriticidade()) {
                    case "Atenção":
                        simbolo = "⚠\uFE0F";
                        break;
                    case "Urgente":
                        simbolo = "\uD83D\uDFE0";
                        break;
                    case "Crítico":
                        simbolo = "\uD83D\uDD34";
                        break;
                }

                salvarAlerta(conA, conL, id, componente, media, limite.getCriticidade());

                Looca looca = new Looca();
                String hostNameAtual = looca.getRede().getParametros().getHostName();

                SendAlert.sendMessage(String.format(
                        " Maquina: %s      Componente: %s     Criticidade: %s %s      Percentual: %.2f %%",
                        hostNameAtual, tipoComponente, limite.getCriticidade(), simbolo, media));

                System.out.println(String.format(
                        "Alerta!!! Maquina: %s      Componente: %s     Criticidade: %s %s      Percentual: %.2f %%",
                        hostNameAtual, tipoComponente, limite.getCriticidade(), simbolo, media));
            }
        }
    }

    public void verificarAlertaRede(JdbcTemplate conA, JdbcTemplate conL, Integer id, Integer componente, Boolean aBoolean) {

        try {
            System.out.println("verificarAlertaRede");
            Double med = 0.0;
            if (aBoolean = true) {
                med = 1.0;
            }
            SendAlert.sendMessage(String.format(
                    " Maquina: %s      Componente: %s     Criticidade: %s ",
                    "Rede", aBoolean ? "Conexão com a internet" : "Sem conexão com a internet"));
            salvarAlerta(conA, conL, id, 4, med, aBoolean ? "Conexão com a internet ok" : "Sem conexão com a internet");
        } catch (IOException ex) {
            Logger.getLogger(Totem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Double verificarMedida(JdbcTemplate con, LocalDateTime data, Integer id, Integer componente, Integer minutos) {
//    System.out.println("Verificando medidas");
        List<MedidaRetorno> medidas = null;
        LocalDateTime dataHoraAtual = data;
        LocalDateTime dataHoraAnterior = dataHoraAtual.minusMinutes(minutos);
        System.out.println("Select");
        System.out.println("Componente: " + componente + " idTotem " + id + " dataHoraAnterior: " + dataHoraAnterior.toString() + " dataHoraAtual " + dataHoraAtual.toString());
        try {
            medidas = con.query(
                    "SELECT m.percentual, FORMAT(m.dataHora, 'HH:mm:ss') AS hora FROM Medida m WHERE m.fkComponente = ? AND m.fkTotem = ? AND CAST(m.dataHora AS TIME) BETWEEN CAST(? AS TIME) AND CAST( ? AS TIME) ORDER BY hora DESC",
                    new BeanPropertyRowMapper<>(MedidaRetorno.class), componente, id, dataHoraAnterior.toString(), dataHoraAtual.toString());
        } catch (DataAccessException e) {
            System.out.println("Erro" + e);
        }

        System.out.println("Tamanho: " + medidas.size() + " " + medidas);
        Double total = 0.0;
        for (MedidaRetorno medida : medidas) {
            total += medida.getPercentual();
        }
        Double media = total / medidas.size();
        return media;

    }

    public Boolean verificarMedidaRede(JdbcTemplate con, Integer id, Integer componente) throws InterruptedException {
        List<Tempo> tempo = con.query(
                "SELECT TOP 1 m.dataHora FROM Medida m WHERE m.fkComponente = ? AND m.fkTotem = ? ORDER BY m.dataHora DESC",
                new BeanPropertyRowMapper<>(Tempo.class), componente, id);
        LocalDateTime dataHoraInicial = tempo.get(0).getDataHora();
        LocalDateTime dataHoraFinal = null;
        Integer idMaquina = id;

        CountDownLatch latch = new CountDownLatch(1); // Inicializa o CountDownLatch com 1

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<Tempo> tempo2 = con.query(
                        "SELECT TOP 1 m.dataHora FROM Medida m WHERE m.fkComponente = ? AND m.fkTotem = ? ORDER BY m.dataHora DESC",
                        new BeanPropertyRowMapper<>(Tempo.class), componente, idMaquina);

                LocalDateTime dataHoraFinal = tempo2.get(0).getDataHora();

                if (!(dataHoraInicial.equals(dataHoraFinal))) {
                } else {
                    System.out.println("As datas são iguais");
                }

                latch.countDown(); // Sinaliza que a tarefa foi concluída
            }
        };

        long intervalo = 20000;

        // Agendando a tarefa para ser executada uma vez
        timer.schedule(task, intervalo);

        latch.await(); // Aguarda a conclusão da tarefa do TimerTask

        if (!(dataHoraInicial.equals(dataHoraFinal))) {
            return true;
        }

        return false;
    }

}
