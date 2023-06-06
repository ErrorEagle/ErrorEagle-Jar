/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import models.Componentes;
import conexao.ConexaoAzure;
import models.Totem;
import com.github.britooo.looca.api.core.Looca;
import conexao.ConexaoLocal;
import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.standard.Media;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import models.Configuracao;
import models.Empresa;
import models.Funcionario;
import models.Limite;
import models.Log;
import models.Medida;
import models.TipoAlerta;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class VerificarTotem extends javax.swing.JFrame {

    Looca looca = new Looca();
    Log log = new Log();
    private List<Totem> listaTotem;
    private Integer fkEmpresa;
    private Double bandaLarga;

    public VerificarTotem(Funcionario usuario, Empresa empresa) {
        initComponents();

        this.fkEmpresa = usuario.getFkEmpresa();
        this.bandaLarga = empresa.getBandaLarga();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                TipoAlerta tp = new TipoAlerta();
                Componentes cp = new Componentes();
                Limite lm = new Limite();
                Configuracao cf = new Configuracao();
                ConexaoAzure conexaoA = new ConexaoAzure();
                ConexaoLocal conexaoL = new ConexaoLocal();
                JdbcTemplate conL = conexaoL.getConnection();
                JdbcTemplate conA = conexaoA.getConnection();

                // Criar e executar o SwingWorker para realizar as validações em segundo plano
                SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        validarMaquinaRegistrada(conA, conL);
                        publish("Verificando Máquina!");

                        cp.validarComponentes(conA, conL);
                        publish("Verificando Componentes!");

                        cf.validarConfiguracao(listaTotem.get(0).getId(), bandaLarga, conA, conL);
                        publish("Verificando Configuração!");

                        tp.validarTiposAlertas(conA, conL);
                        publish("Validando alertas!");

                        lm.validarLimites(conA, conL);
                        publish("Verificando Limites!");

                        return null;
                    }

                    @Override
                    protected void process(List<String> chunks) {

                        String labelText = chunks.get(0);
                        lblTitulo.setText(labelText);

                    }

                    @Override
                    protected void done() {
             
                        lblTitulo.setText("Capturando Dados!");
                        Medida md = new Medida();
                        iniciarCaptura(conA, conL, md);
//                        iniciarCaptura(conL, md);
                        agendamentoVerificacao(md);
                    }
                };

                worker.execute();
            }
        });

    }

    private void validarMaquinaRegistrada(JdbcTemplate conA, JdbcTemplate conL) {

        log.writeRecordToLogFile("Validando se a máquina já está cadastrada...");
        String hostNameAtual = looca.getRede().getParametros().getHostName();

        listaTotem = new ArrayList();

        listaTotem = conA.query("select * from Totem where hostName = ?",
                new BeanPropertyRowMapper(Totem.class), hostNameAtual);

        if (listaTotem.isEmpty()) {

            log.writeRecordToLogFile("Máquina não cadastrada! Executando cadastro azure...");
            conA.update("insert into Totem(hostName, fkEmpresa) values (?, ?)", hostNameAtual, fkEmpresa);
            conL.update("insert into Totem(hostName, fkEmpresa) values (?, ?)", hostNameAtual, fkEmpresa);

            listaTotem = new ArrayList();

            listaTotem = conA.query("select * from Totem where hostName = ?",
                    new BeanPropertyRowMapper(Totem.class), hostNameAtual);
            log.writeRecordToLogFile("Máquina cadastrada!");

        } else {
            if (!listaTotem.get(0).getFkEmpresa().equals(fkEmpresa)) {
                JOptionPane.showMessageDialog(null, "Essa máquina já está cadastrata em outra empresa!");
                log.writeRecordToLogFile("Máquina cadastrada em outra empresa!");
                System.exit(0);
            }
        }
    }

    public void iniciarCaptura(JdbcTemplate conA, JdbcTemplate conL, Medida md) {

        Integer fkTotem = listaTotem.get(0).getId();
        // Agendamento para inserção das medidas do totem a cada 20 segundos
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            log.writeRecordToLogFile("Capturando dados...");
            md.inserirMedidas(fkTotem, bandaLarga, conA, conL);
        }, 0, 20, TimeUnit.SECONDS);

       

    }
    
    public void agendamentoVerificacao(Medida md) {
       
        ConexaoAzure conexaoA = new ConexaoAzure();
        JdbcTemplate conA = conexaoA.getConnection();
        Integer fkTotem = listaTotem.get(0).getId();
        ConexaoAzure conexaoL = new ConexaoAzure();
        JdbcTemplate conL = conexaoA.getConnection();
        
         // Agendamento para verificação de alertas a cada 5 minuto
        ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);
        scheduler1.scheduleAtFixedRate(() -> {
            LocalDateTime date = LocalDateTime.now();
            try {
                System.out.println("Agendando CPU:");
                md.verificarAlerta(conA, conL, fkTotem, 1, md.verificarMedida(conA, date, fkTotem, 1, 3));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println("Agendando RAM:");
                md.verificarAlerta(conA, conL, fkTotem, 2, md.verificarMedida(conA, date, fkTotem, 2, 3));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, 5, TimeUnit.MINUTES);

        // Agendamento para verificação de alertas a cada 10 minutos
        ScheduledExecutorService scheduler3 = Executors.newScheduledThreadPool(1);
        scheduler3.scheduleAtFixedRate(() -> {
            LocalDateTime date = LocalDateTime.now();
            try {
                System.out.println("Agendando Disco:");
                md.verificarAlerta(conA, conL, fkTotem, 3, md.verificarMedida(conA, date, fkTotem, 3, 10));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, 10, TimeUnit.MINUTES);

        // Agendamento para verificação de alertas de rede a cada 1 minuto
        ScheduledExecutorService scheduler2 = Executors.newScheduledThreadPool(1);
        scheduler1.scheduleAtFixedRate(() -> {
            LocalDateTime date = LocalDateTime.now();
            try {
                System.out.println("Agendando Rede:");
                md.verificarAlertaRede(conA, conL, fkTotem, 4, md.verificarMedidaRede(conA, fkTotem, 4));
            } catch (InterruptedException ex) {
                Logger.getLogger(VerificarTotem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Captura de Dados");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(460, 290));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 255, 0));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Aguarde!");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 350, 60));

        background.setBackground(new java.awt.Color(51, 51, 51));
        background.setOpaque(true);
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 290));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables

}
