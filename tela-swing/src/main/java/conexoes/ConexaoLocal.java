/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexoes;

import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author LUCAS
 */
public class ConexaoLocal {

    private JdbcTemplate connection;

    BasicDataSource dataSource = new BasicDataSource();

    public ConexaoLocal() {

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/erroreagle?autoReconnect=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("urubu100");

        this.connection = new JdbcTemplate(dataSource);

    }

    public JdbcTemplate getConnection() {

        return connection;

    }

    public Boolean testeConexao() {
        try {
            connection.execute("Select * from Admin");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {

        try {
            dataSource.close();
            System.out.println("Conexão com a Local encerrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Falha ao encerrar conexão com a Local");
        }
    }

}
