/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author LUCAS
 */
public class Conexao {

  private JdbcTemplate connection;

  public Conexao() {

    BasicDataSource dataSource = new BasicDataSource();

    // Procurar na internet o c driver da Azure
    dataSource​.setDriverClassName("com.mysql.cj.jdbc.Driver");

    //Colocar o caminho até o banco de dados da Azure
    dataSource​.setUrl("jdbc:mysql://localhost:3306/ErrorEagle");

    dataSource​.setUsername("Lucas Barroso");

    dataSource​.setPassword("Lukinhas123");

    this.connection = new JdbcTemplate(dataSource);

  }

  public JdbcTemplate getConnection() {

    return connection;

  }

}
