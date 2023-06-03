/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import models.Componentes;
import conexoes.ConexaoAzure;
import models.Totem;
import com.github.britooo.looca.api.core.Looca;
import conexoes.ConexaoLocal;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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

                // Configurar o valor máximo da barra de progresso
                pbTeste.setMaximum(100);

                // Configurar o valor inicial da barra de progresso
                pbTeste.setValue(0);

                // Criar e executar o SwingWorker para realizar as validações em segundo plano
                SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        validarMaquinaRegistrada(conA, conL);
                        publish("Verificando Máquina!;7"); // Atualizar o texto do label e o valor da barra de progresso

                        cp.validarComponentes(conA);
                        publish("Verificando Componentes!;33"); // Atualizar o texto do label e o valor da barra de progresso

                        cf.validarConfiguracao(listaTotem.get(0).getId(), bandaLarga, conA);
                        publish("Verificando Configuração!;66"); // Atualizar o texto do label e o valor da barra de progresso

                        tp.validarTiposAlertas(conA);
                        publish("Validando alertas!!;86"); // Atualizar o texto do label e o valor da barra de progresso

                        lm.validarLimites(conA);
                        publish("Verificando Limites!;100"); // Atualizar o texto do label e o valor da barra de progresso

                        return null;
                    }

                    @Override
                    protected void process(List<String> chunks) {
                        // Último chunk contém a informação mais recente
                        String[] values = chunks.get(chunks.size() - 1).split(";");
                        String labelText = values[0];
                        int progressValue = Integer.parseInt(values[1]);

                        // Atualizar o texto do label
                        jLabel1.setText(labelText);

                        // Atualizar a barra de progresso com o valor atual
                        pbTeste.setValue(progressValue);
                    }

                    @Override
                    protected void done() {
                        // Executado quando o SwingWorker é concluído
                        jLabel1.setText("Capturando Dados!");
                        pbTeste.setVisible(false);
                        iniciarCaptura();
                    }
                };

                // Executar o SwingWorker
                worker.execute();
            }
        });

    }

    private void validarMaquinaRegistrada(JdbcTemplate con, JdbcTemplate con2) {

        log.writeRecordToLogFile("Validando se a máquina já está cadastrada...");
        String hostNameAtual = looca.getRede().getParametros().getHostName();

        listaTotem = new ArrayList();

        listaTotem = con.query("select * from Totem where hostName = ?",
                new BeanPropertyRowMapper(Totem.class), hostNameAtual);

        if (listaTotem.isEmpty()) {

            log.writeRecordToLogFile("Máquina não cadastrada! Executando cadastro azure...");
            con.update("insert into Totem(hostName, fkEmpresa) values (?, ?)", hostNameAtual, fkEmpresa);
            con2.update("insert into Totem(hostName, fkEmpresa) values (?, ?)", hostNameAtual, fkEmpresa);

            log.writeRecordToLogFile("Máquina cadastrada!");
            validarMaquinaRegistrada(con, con2);

        } else {
            if (!listaTotem.get(0).getFkEmpresa().equals(fkEmpresa)) {
                JOptionPane.showMessageDialog(null, "Essa máquina já está cadastrata em outra empresa!");
                log.writeRecordToLogFile("Máquina cadastrada em outra empresa!");
                System.exit(0);
            }
        }
    }

    public void iniciarCaptura() {
        Medida medida = new Medida();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.writeRecordToLogFile("Capturando dados...");
                medida.inserirMedidas(listaTotem.get(0).getId(), bandaLarga);
                // Restante do código...

                System.out.println(listaTotem);
            }
        }, 0, 5000);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        pbTeste = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Teste de Tela");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(460, 290));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Aguarde!");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 270, 60));
        getContentPane().add(pbTeste, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 270, 20));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar pbTeste;
    // End of variables declaration//GEN-END:variables

}
