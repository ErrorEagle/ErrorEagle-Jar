/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

/**
 *
 * @author gcmor
 */
public class Conexao {

    private JdbcTemplate conexaoDoBanco;

    public Conexao() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/MySQL Workbench"); // trocar o localhost:3306 pelo endereço do banco e o tecflix pelo nome do banco

        dataSource.setUsername("root"); //Usuario do banco

        dataSource.setPassword(""); //Senha do banco
        
        this.conexaoDoBanco = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }

}
