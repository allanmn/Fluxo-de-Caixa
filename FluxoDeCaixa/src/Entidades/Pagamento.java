/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package entidades;

/**
 *
 * @author beraldo
 */
public enum Pagamento {
    CC("Cartão de Crédito", 1),
    DINHEIRO("DINHEIRO", 2),
    BOLETO("Boleto", 3),
    DEPOSITO("Deposito",4),
    CONVENIO("Convênio", 5);

    private int valor;
    private String nome;

    private Pagamento(String nome, int valor) {
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
