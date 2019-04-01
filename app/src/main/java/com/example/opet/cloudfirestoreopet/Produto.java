package com.example.opet.cloudfirestoreopet;

/**
 * Created by opet on 01/04/2019.
 */

public class Produto {
    private String nome;
    private String categoria;

    public Produto() {
    }

    public Produto(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto:\n" +
                "Nome: " + nome + '\n' +
                "Categoria: " + categoria + '\n';
    }
}
