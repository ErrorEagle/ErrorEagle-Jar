package application;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Conexao conexao = new Conexao();

        JdbcTemplate con = conexao.getConexaoDoBanco();
        
        con.execute("drop table if exists filme;");
    }
    
}
