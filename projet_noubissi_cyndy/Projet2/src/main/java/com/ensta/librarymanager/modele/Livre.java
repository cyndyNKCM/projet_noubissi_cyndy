package com.ensta.librarymanager.modele;

public class Livre {
    private int idL;
    private String titre;
    private String auteur;
    private String isbn;

    public Livre() {
        super();
    }

    public Livre(int idL, String titre, String auteur, String isbn) {
        this.idL = idL;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
    }

    public Livre(String titre, String auteur, String isbn) {

        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
    }

    public int getIdL() {
        return this.idL;
    }

    public String getTitre() {
        return this.titre;
    }

    public String getAuteur() {
        return this.auteur;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setId(int id) {
        this.idL = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isEqualTo(Livre l){
        boolean equal=false;
        if (l.idL == this.idL)
            equal = true;
        else
            equal = false;
        return equal;

    }


    public String toString() {
        return String.format("id %d \n titre :%s \n Auteur :%s \n Isbn : %s\n", idL, titre, auteur, isbn);
    }
}
