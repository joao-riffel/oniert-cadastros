package app.produtos.model;

public class Produto {

    private int id;
    private String nome;
    private double preco;
    private int quantidade;


    public Produto(){}

    public Produto(String nome, double preco, int quantidade) {

        setNome(nome);
        setPreco(preco);
        setQuantidade(quantidade);

    }


    public Produto (int id, String nome, double preco, int quantidade){

        setId(id);
        setNome(nome);
        setPreco(preco);
        setQuantidade(quantidade);

    }

    // Getters e Setters
    public int getId() {return id;}
    public String getNome () {return nome;}
    public double getPreco () {return preco;}
    public int getQuantidade() {return quantidade;}


    public void setId (int id) {this.id = id;}
    public void setNome (String nome) {this.nome = nome;}
    public void setPreco (double preco) {this.preco = preco;}
    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}


    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Preço: R$ " + String.format("%.2f", preco);
    }


}
