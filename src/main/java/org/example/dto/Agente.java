package org.example.dto;

import java.util.Objects;

public class Agente {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Agenzia agenzia;

    public Agente(String nome, String cognome, String email, String password, Agenzia agenzia) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.agenzia = agenzia;
    }
    public Agente() {
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Agenzia getAgenzia() {
        return agenzia;
    }

    public void setAgenzia(Agenzia agenzia) {
        this.agenzia = agenzia;
    }

    @Override
    public String toString() {
        return "Agente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", agenzia=" + agenzia +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Agente agente = (Agente) o;
        return Objects.equals(email, agente.email);
    }

}
