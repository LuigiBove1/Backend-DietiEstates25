package org.example.dto;

import java.util.Objects;

public class Amministratore {
    private String nomeAdmin;
    private String password;
    private Agenzia agenzia;

    public Amministratore(String nomeAdmin, String password, Agenzia agenzia) {
        this.nomeAdmin = nomeAdmin;
        this.password = password;
        this.agenzia = agenzia;
    }

    public Amministratore() {
    }

    public String getNomeAdmin() {
        return nomeAdmin;
    }

    public void setNomeAdmin(String nomeAdmin) {
        this.nomeAdmin = nomeAdmin;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amministratore that = (Amministratore) o;
        return Objects.equals(nomeAdmin, that.nomeAdmin);
    }


    @Override
    public String toString() {
        return "Amministratore{" +
                "nomeAdmin='" + nomeAdmin + '\'' +
                ", password='" + password + '\'' +
                ", agenzia=" + agenzia +
                '}';
    }
}
