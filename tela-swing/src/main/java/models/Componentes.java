package models;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.rede.RedeInterfaceGroup;
import conexoes.ConexaoAzure;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class Componentes {

    private String nome;
    private Integer id;
    private List<Componentes> listaComponentes;
    
    public Componentes() {

    }
    
    public void validarComponentes() {
        Looca looca = new Looca();
        listaComponentes = new ArrayList();

        ConexaoAzure conexaoA = new ConexaoAzure();
        JdbcTemplate conA = conexaoA.getConnection();

        DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
        List<Disco> discos = grupoDeDiscos.getDiscos();

        RedeInterfaceGroup grupoDeInterfaces = looca.getRede().getGrupoDeInterfaces();
        List<RedeInterface> grupodeInterface = grupoDeInterfaces.getInterfaces();

        listaComponentes = conA.query("select * from Componente", new BeanPropertyRowMapper(Componentes.class));

        if (listaComponentes.isEmpty()) {

            //Cadastrar CPU
            conA.update("insert into Componente(nome) values ('CPU')");

            //Cadastrar RAM
            conA.update("insert into Componente(nome) values ('RAM')");

            //Cadastrar Disco
            conA.update("insert into Componente(nome) values ('DISCO')");

            //Cadastrar Rede
            conA.update("insert into Componente(nome) values ('REDE')");

            System.out.println("Coloquei dados de componentes lá na tabela componentes!");
        } else {

            // verificar se falta algum para cadastrar
            Boolean hasRAM = true, hasCPU = true, hasDisco = true, hasRede = true;

            for (Componentes componente : listaComponentes) {
                if (componente.getNome().equalsIgnoreCase("RAM")) {
                    hasRAM = false;
                } else if (componente.getNome().equalsIgnoreCase("CPU")) {
                    hasCPU = false;
                } else if (componente.getNome().equalsIgnoreCase("REDE")) {
                    hasRede = false;
                } else {
                    hasDisco = false;
                }
            }

            if (hasCPU) {
                conA.update("insert into Componente(nome) values ('CPU')");
            }
            if (hasRAM) {
                conA.update("insert into Componente(nome) values ('RAM')");
            }
            if (hasDisco) {
                conA.update("insert into Componente(nome) values ('DISCO')");
            }
            if (hasRede) {
                conA.update("insert into Componente(nome) values ('REDE')");
            }

        }

        conexaoA.closeConnection();

//        ----------------------------------------------------- LOCAL -----------------------------------------------------
//        listaComponentes = new ArrayList();
//        
//        ConexaoLocal conexaoL = new ConexaoLocal();
//        JdbcTemplate conL = conexaoL.getConnection();
//
//        listaComponentes = conL.query("select * from Componente", new BeanPropertyRowMapper(Componentes.class));
//
//        System.out.println(listaComponentes);
//
//        if (listaComponentes.isEmpty()) {
//
//            //Cadastrar CPU
//            conL.update("insert into Componente(nome) values ('CPU')");
//
//            //Cadastrar RAM
//            conL.update("insert into Componente(nome) values ('RAM')");
//
//            //Cadastrar Disco
//            conL.update("insert into Componente(nome) values ('DISCO')");
//
//            //Cadastrar Rede
//            conL.update("insert into Componente(nome) values ('REDE')");
//
//            System.out.println("Coloquei dados de componentes lá na tabela componentes!");
//        } else {
//            // verificar se falta algum para cadastrar
//
//            Boolean hasRAM = true, hasCPU = true, hasDisco = true, hasRede = true;
//
//            for (Componentes componente : listaComponentes) {
//                if (componente.getNome().equalsIgnoreCase("RAM")) {
//                    hasRAM = false;
//                } else if (componente.getNome().equalsIgnoreCase("CPU")) {
//                    hasCPU = false;
//                } else if (componente.getNome().equalsIgnoreCase("REDE")) {
//                    hasRede = false;
//                } else {
//                    hasDisco = false;
//                }
//            }
//
//            if (hasRede) {
//                conL.update("insert into Componente(nome) values ('REDE')");
//            }
//            if (hasDisco) {
//                conL.update("insert into Componente(nome) values ('DISCO')");
//            }
//            if (hasRAM) {
//                conL.update("insert into Componente(nome) values ('RAM')");
//            }
//            if (hasCPU) {
//                conL.update("insert into Componente(nome) values ('CPU')");
//            }
//            System.out.println("Tudo cadastrado!");
//        }
//      
//        conexaoL.closeConnection();
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Componentes{" + "nome=" + nome + ", id=" + id + '}';
    }
    
}
