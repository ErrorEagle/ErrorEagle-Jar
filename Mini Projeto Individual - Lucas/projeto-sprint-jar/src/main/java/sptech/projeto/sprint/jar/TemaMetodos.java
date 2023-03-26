/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sptech.projeto.sprint.jar;

/**
 *
 * @author LUCAS
 */
public class TemaMetodos {

    Boolean erro;

    

    Boolean verificarLogin(String email, String senha) {

        if (email.equals("lucas.barrosoj@sptech.school") && senha.equals("#Lb2002")) {
            System.out.println("Bem-vindo Sr(a) Lucas B");
            erro = false;
        } else {
            erro = true;
        }

        return erro;

    }
}
