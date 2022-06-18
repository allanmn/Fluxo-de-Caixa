package bd;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author beraldo
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class Leitor {

    File pasta = new File("src/bd");
    File arq = new File(pasta, "sql.txt"); // identificar arquivo

    FileReader arqTxt;

    public Leitor() {
    }

    public StringBuffer read() throws FileNotFoundException, EOFException, IOException {
        arqTxt = new FileReader(arq); // abrindo arquivo
        BufferedReader arquivo = new BufferedReader(arqTxt);
        StringBuffer linhas = new StringBuffer();
        String linha = null;
        while ((linha = arquivo.readLine()) != null) {
            linhas.append(linha);
        }
        arquivo.close();
        return linhas;
    }
;

};
