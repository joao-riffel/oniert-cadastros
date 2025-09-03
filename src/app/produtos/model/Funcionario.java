package app.produtos.model;

public class Funcionario {

    private String nome;
    private String usuario;
    private String senha;

    public Funcionario(){}

    public Funcionario(String nome, String usuario, String senha) {

        setNome(nome);
        setUsuario(usuario);
        setSenha(senha);

    }

    public String getNome() {return this.nome;};
    public String getUsuario() {return this.usuario;};
    public String getSenha() {return this.senha;};

    public void setNome(String nome){this.nome = nome;};
    public void setUsuario(String usuario){this.usuario = usuario;};
    public void setSenha(String senha){this.senha = senha;};





}
