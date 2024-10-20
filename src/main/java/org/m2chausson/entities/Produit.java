package org.m2chausson.entities;

import javax.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "type", nullable = false)
    private String type;

    public Produit() {}

    public Produit(String nom, Double prix, String type) {
        this.nom = nom;
        this.prix = prix;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Produit [id=" + id + ", nom=" + nom + ", prix=" + prix + ", type=" + type + "]";
    }
}
