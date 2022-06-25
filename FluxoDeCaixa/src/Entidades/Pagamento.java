/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Entidades;

/**
 *
 * @author beraldo
 */
public enum Pagamento {
    CC(0,"Cartão de Crédito"),
    DINHEIRO(1,"Dinheiro"),
    BOLETO(2,"Boleto"),
    DEPOSITO(3,"Deposito"),
    CONVENIO(4,"Convênio");

    private int valor;
    private String nome;

    private Pagamento(int valor, String nome) {
        this.valor = valor;
        this.nome = nome;
    }

   public void selecionarPagamento(){
       
   }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
