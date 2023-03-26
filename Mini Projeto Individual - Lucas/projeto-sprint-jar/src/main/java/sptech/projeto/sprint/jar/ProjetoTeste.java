/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sptech.projeto.sprint.jar;

import java.util.Scanner;

/**
 *
 * @author LUCAS
 */
public class ProjetoTeste {

    public static void main(String[] args) {

        Scanner leitor = new Scanner(System.in);
        PaperGold paper = new PaperGold();
        Double custoBotijao, temperaturaFogao, pesoBotijao, aux_gas;
        
        Integer opcao, duvida;
        Boolean erro = false;
  
            
            paper.exibirBem_Vindo();
            
            do {
                
                
                erro = paper.validarOpcao(erro);
                
                paper.exibirMenu();
                opcao = leitor.nextInt();

                switch (opcao) {
                    case 1:
                        paper.exibirDicaGas();
                        custoBotijao = leitor.nextDouble();
                        
                        if(custoBotijao < 0){
                            System.out.println("O custo do Botijão não pode ser menor que 0!");
                            break;
                        }
                        
                        System.out.println("Qual o peso do Botijão?");
                        pesoBotijao = leitor.nextDouble();
                        
                        if(pesoBotijao < 0){
                            System.out.println("Esse botijão não tem gás!");
                            break;
                        }
                        
                        System.out.println("Em que temperatura você está cozinhando?");
                        temperaturaFogao = leitor.nextDouble();
                        
                        if(temperaturaFogao >= 200.0){
                           aux_gas = 0.250;
                        
                        } else if(temperaturaFogao >= 180.0){
                            aux_gas = 0.225;
                        } else if(temperaturaFogao >= 140.0) {
                            aux_gas = 0.200;
                        }else {
                            System.out.println("Não tem como assar um bolo assim!");
                            break;
                        }
                        
                        System.out.printf("O gasto por hora é cerca de: R$:%.2f\n", (aux_gas*custoBotijao/pesoBotijao));
                        break;
                    case 2:
                        
                        do {
                        erro = paper.validarOpcao(erro);
                        
                        paper.exibirDuvidas();
                        duvida = leitor.nextInt();
                        
                        erro = paper.exibirResposta(duvida, erro);
                        
                        } while (duvida != 0);

                       break;
                    case 3:
                            paper.exibirReceitaAleatoria();
                            break;

                    case 0:
                        System.out.printf("%s","Você optou por sair! Até mais...");
                        break;
                    default:
                        erro = true;
                }
            } while (opcao != 0);
        }
    }


