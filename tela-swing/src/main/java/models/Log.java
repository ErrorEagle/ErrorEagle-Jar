package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.sistema.Sistema;

public class Log {
    
        // /home/ubuntu/Desktop/logs/log.txt
        // C:/logs/log.txt
	private Looca looca = new Looca();
	private String LOG_FILE_PATH = "/home/ubuntu/Desktop/logs/log.txt";
	private Boolean primeiraLinha = false;
	private final int INSERT_LINE = 6;
	private String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	public String buildLogEntry(String name) {
            File pasta = new File("/home/ubuntu/Desktop/logs/");
            
            if (!pasta.exists()) {
                pasta.mkdir();
            }
            
		return timestamp + " ErrorEagle" + "\nNova sessão iniciada por: " + name + System.lineSeparator()
				+ "Copyright (c) Error Eagle" + System.lineSeparator() + "Sistema operacional: "
				+ looca.getSistema().getSistemaOperacional() + " " + looca.getSistema().getArquitetura()
				+ System.lineSeparator();
	}

	public void writeLogEntry(String logEntry, String oldContent) {
		File logFile = new File(LOG_FILE_PATH);

		if (logFile.length() > 40000 && LOG_FILE_PATH.equals("/home/ubuntu/Desktop/logs/log.txt")) {
			LOG_FILE_PATH = "/home/ubuntu/Desktop//logs/log2.txt";
			primeiraLinha = true;
		} else if (logFile.length() > 0000 && LOG_FILE_PATH.equals("/home/ubuntu/Desktop/logs/log2.txt")) {
			LOG_FILE_PATH = "/home/ubuntu/Desktop/logs/log.txt";
			primeiraLinha = true;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, false))) {
			
			if (LOG_FILE_PATH.equals("/home/ubuntu/Desktop/logs/log.txt") && primeiraLinha) {
				writer.write(logEntry);
				writer.newLine();
				writer.newLine();
				primeiraLinha = false;
			} else if (LOG_FILE_PATH.equals("/home/ubuntu/Desktop/logs/log.txt") && new File(LOG_FILE_PATH).length() == 0) {
				writer.write(logEntry);
				writer.newLine();
				writer.newLine();
				primeiraLinha = false;
			} else {
				writer.write(logEntry);
				writer.newLine();
				writer.newLine();
				writer.write(oldContent);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readLogFile() {
		StringBuilder sb = new StringBuilder();

		try {
			File logFile = new File(LOG_FILE_PATH);
			if (logFile.exists()) {
				java.util.Scanner scanner = new java.util.Scanner(logFile);
				while (scanner.hasNextLine()) {
					sb.append(scanner.nextLine());
					sb.append(System.lineSeparator());
				}
				scanner.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void writeRecordToLogFile(String msg) {

		String logMessege = timestamp + " ErrorEagle | Registro: " + msg;

		try {
			File logFile = new File(LOG_FILE_PATH);
			File tempFile = new File(LOG_FILE_PATH + ".temp");

			BufferedReader reader = new BufferedReader(new FileReader(logFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			int lineNumber = 1;

			while ((line = reader.readLine()) != null) {
				if (lineNumber == INSERT_LINE) {
					writer.write(logMessege);
					writer.newLine();
				}

				writer.write(line);
				writer.newLine();

				lineNumber++;
			}

			if (lineNumber <= INSERT_LINE) {
				writer.write(logMessege);
				writer.newLine();
			}

			reader.close();
			writer.close();

			if (!logFile.delete()) {
				throw new IOException("Falha ao excluir o arquivo de log original.");
			}

			if (!tempFile.renameTo(logFile)) {
				throw new IOException("Falha ao renomear o arquivo temporário.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}