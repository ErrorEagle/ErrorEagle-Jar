package models;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.rede.RedeInterfaceGroup;
import conexao.ConexaoAzure;
import conexao.ConexaoLocal;
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
    
    public void validarComponentes(JdbcTemplate conA, JdbcTemplate conL) {
        Looca looca = new Looca();
        Log log = new Log();
        listaComponentes = new ArrayList();

        DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
        List<Disco> discos = grupoDeDiscos.getDiscos();

        RedeInterfaceGroup grupoDeInterfaces = looca.getRede().getGrupoDeInterfaces();
        List<RedeInterface> grupodeInterface = grupoDeInterfaces.getInterfaces();

        listaComponentes = conA.query("select * from Componente", new BeanPropertyRowMapper(Componentes.class));

        if (listaComponentes.isEmpty()) {
            log.writeRecordToLogFile("Cadastrando Componentes...");
            //Cadastrar CPU
            conA.update("insert into Componente(nome) values ('CPU')");
            conL.update("insert into Componente(nome) values ('CPU')");

            //Cadastrar RAM
            conA.update("insert into Componente(nome) values ('RAM')");
            conL.update("insert into Componente(nome) values ('RAM')");

            //Cadastrar Disco
            conA.update("insert into Componente(nome) values ('DISCO')");
            conL.update("insert into Componente(nome) values ('DISCO')");

            //Cadastrar Rede
            conA.update("insert into Componente(nome) values ('REDE')");
            conL.update("insert into Componente(nome) values ('REDE')");

            System.out.println("Coloquei dados de componentes lá na tabela componentes!");
        } else {

            // verificar se falta algum para cadastrar
            log.writeRecordToLogFile("Validando se falta cadastrar algum Componente...");
            Boolean hasRAM = true, hasCPU = true, hasDisco = true, hasRede = true;

            for (Componentes componente : listaComponentes) {
                if (componente.getNome().equalsIgnoreCase("RAM")) {
                    log.writeRecordToLogFile("RAM cadastrada!");
                    hasRAM = false;
                } else if (componente.getNome().equalsIgnoreCase("CPU")) {
                    log.writeRecordToLogFile("CPU cadastrada!");
                    hasCPU = false;
                } else if (componente.getNome().equalsIgnoreCase("REDE")) {
                    log.writeRecordToLogFile("REDE cadastrada!");
                    hasRede = false;
                } else {
                    log.writeRecordToLogFile("DISCO cadastrada!");
                    hasDisco = false;
                }
            }

            if (hasCPU) {
                log.writeRecordToLogFile("Inserindo Componente CPU");
                conA.update("insert into Componente(nome) values ('CPU')");
                conL.update("insert into Componente(nome) values ('CPU')");
            }
            if (hasRAM) {
                log.writeRecordToLogFile("Inserindo Componente RAM");
                conA.update("insert into Componente(nome) values ('RAM')");
                conL.update("insert into Componente(nome) values ('RAM')");
            }
            if (hasDisco) {
                log.writeRecordToLogFile("Inserindo Componente DISCO");
                conA.update("insert into Componente(nome) values ('DISCO')");
                conL.update("insert into Componente(nome) values ('DISCO')");
            }
            if (hasRede) {
                log.writeRecordToLogFile("Inserindo Componente REDE");
                conA.update("insert into Componente(nome) values ('REDE')");
                conL.update("insert into Componente(nome) values ('REDE')");
            }

        }

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
