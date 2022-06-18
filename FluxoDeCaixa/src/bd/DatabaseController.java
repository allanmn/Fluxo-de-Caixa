/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bd;

import com.mysql.cj.util.StringUtils;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author a2374170
 */
public class DatabaseController {

    /**
     * @param args the command line arguments
     */
    
    
    public DatabaseController(){
    }

    public static void createDB() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String senha = "root";
        String database = "fluxo";

        try {
            Class.forName(driver);
            Connection conexao = DriverManager.getConnection(url, user, senha);
            Statement sessao = conexao.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS "+database+";"; 
            sessao.executeUpdate(sql);
            System.out.println("Database "+database+" criado com sucesso!!!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver errado: " + driver);
        } catch (SQLException ex) {
            System.out.println("Erro Sql Excetion: " + ex.getMessage());
        }
    }
    public static void createTables(){
        String driver = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String senha = "root";
        String database = "fluxo";
        String url = "jdbc:mysql://localhost:3306/"+database;
        String sql = new String();
        
        Leitor leia = new Leitor();
        try{
            sql = leia.read().toString();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado");
        } catch (EOFException e) {
            System.out.println("Fim de arquivo!");
        } catch (IOException ex) {
            System.out.println("Erro na leitura " + ex.getMessage());
        } 
        
        
        try {
            String[] queries = sql.split(";");
            Class.forName(driver);
            Connection conexao = DriverManager.getConnection(url, user, senha);
            for(String querie : queries){
                Statement sessao = conexao.createStatement();
                sessao.executeUpdate(querie.trim());
            }
            System.out.println("Tabela criada com sucesso!!!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver errado: " + driver);
        } catch (SQLException ex) {
            System.out.println("Erro Sql Excetion: " + ex.getMessage());
        }
    }
}
