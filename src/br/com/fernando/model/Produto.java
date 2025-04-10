package br.com.fernando.model;

// Classe que representa um Produto do estoque
public class Produto {

    // Atributos privados (encapsulamento)
    private String nome;
    private int quantidade;
    private String valorUnitario;

    // Construtor com parâmetros
    public Produto(String nome, int quantidade, String valorUnitario) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    // Getters para acessar os atributos
    public String getNome() {
        return nome;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public String getValorUnitario() {
        return valorUnitario;
    }

    // Metodo toString sobrescrito para formatar como linha de CSV
    @Override
    public String toString() {
        return nome + "; " + quantidade + "; " + valorUnitario + ";";
    }

    // Metodo adicionar quantidade
    public void adicionarQuantidade(int qtd){
        this.quantidade += qtd;
    }

    // Metodo remover quantidade
    public void removerQuantidade(int qtd){
        this.quantidade -= qtd;
        if (this.quantidade < 0)this.quantidade = 0;
    }
}
