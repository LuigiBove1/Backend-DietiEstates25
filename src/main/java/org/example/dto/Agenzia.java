package org.example.dto;

import java.util.Objects;

public class Agenzia {
    private String nome;
    private String citta;
    private String indirizzo;

    public Agenzia(String nome, String citta, String indirizzo) {
        this.nome = nome;
        this.citta = citta;
        this.indirizzo = indirizzo;
    }

    public Agenzia() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agenzia agenzia = (Agenzia) o;
        return Objects.equals(nome, agenzia.nome);
    }

    @Override
    public String toString() {
        return "Agenzia{" +
                "nome='" + nome + '\'' +
                ", citta='" + citta + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }
}
