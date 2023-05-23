package application;

import java.io.File;

import entities.Log;

public class Program {
	public static void main(String[] args) {

		Log log = new Log();

		String name = "John Doe";
		String logEntry = log.buildLogEntry(name);
		String oldContent = log.readLogFile();
		log.writeLogEntry(logEntry, oldContent);
		
		

		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		log.writeRecordToLogFile("Teste inserindo na quinta linha!	");
		log.writeRecordToLogFile("Teste inserindo na quinta linha, segunda vez!");
		
		String caminho = "C:/logs/log.txt";
		File logFile = new File(caminho);
		
		System.out.println("Tamanho do arquivo " + logFile.length());
		
	}
}
