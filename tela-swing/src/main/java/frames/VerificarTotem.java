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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.standard.Media;
import javax.swing.JOptionPane;
import models.Configuracao;
import models.Empresa;
import models.Funcionario;
import models.Limite;
import models.Medida;
import models.TipoAlerta;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class VerificarTotem extends javax.swing.JFrame {

    Looca looca = new Looca();
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
                // Fazer retorno booleano
                pbTeste.setValue(07);
                validarMaquinaRegistrada();
                pbTeste.setValue(33);
                cp.validarComponentes();
                pbTeste.setValue(66);
                cf.validarConfiguracao(listaTotem.get(0).getId(), bandaLarga);
                pbTeste.setValue(78);
                tp.validarTiposAlertas();
                pbTeste.setValue(99);
                lm.validarLimites();
                
                jLabel1.setText("Capturando Dados!");
                iniciarCaptura();
                
            }
        });
    }

    private void validarMaquinaRegistrada() {

        ConexaoAzure conexaoA = new ConexaoAzure();
        JdbcTemplate conA = conexaoA.getConnection();

        String hostNameAtual = looca.getRede().getParametros().getHostName();

        listaTotem = new ArrayList();

        listaTotem = conA.query("select * from Totem where hostName = ?",
                new BeanPropertyRowMapper(Totem.class), hostNameAtual);

        if (listaTotem.isEmpty()) {

            conA.update("insert into Totem(hostName, fkEmpresa) values (?, ?)", hostNameAtual, fkEmpresa);

            listaTotem = new ArrayList();

            listaTotem = conA.query("select * from Totem where hostName = ?",
                    new BeanPropertyRowMapper(Totem.class), hostNameAtual);

        } else {
            if (!listaTotem.get(0).getFkEmpresa().equals(fkEmpresa)) {
                JOptionPane.showMessageDialog(null, "Essa máquina já está cadastrata em outra empresa!");
                System.exit(0);
            }
        }

        conexaoA.closeConnection();

//   -------------------------------------- LOCAL -----------------------------------------
        
//        ConexaoLocal conexaoL = new ConexaoLocal();
//        JdbcTemplate conL = conexaoL.getConnection();
//        
//        listaTotem = new ArrayList();
//
//        listaTotem = conA.query("select * from Totem where hostName = ?",
//                new BeanPropertyRowMapper(Totem.class), hostNameAtual);
//
//        if (listaTotem.isEmpty()) {
//
//            conL.update("insert into Totem(hostName, fkEmpresa) values (?, ?)", hostNameAtual, fkEmpresa);
//
//            listaTotem = conL.query("select * from Totem where hostName = ?",
//                    new BeanPropertyRowMapper(Totem.class), hostNameAtual);
//
//        } else {
//            if (!listaTotem.get(0).getFkEmpresa().equals(fkEmpresa)) {
//                JOptionPane.showMessageDialog(null, "Essa máquina já está cadastrata em outra empresa!");
//                System.exit(0);
//            }
//        }
//
//        conexaoL.closeConnection();
    }

    public void iniciarCaptura() {
        Medida medida = new Medida();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                medida.inserirMedidas(listaTotem.get(0).getId(), bandaLarga);
                // Restante do código...
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

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("j");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 280, 90));
        getContentPane().add(pbTeste, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 270, 10));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar pbTeste;
    // End of variables declaration//GEN-END:variables

}
