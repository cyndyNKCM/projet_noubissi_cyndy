package com.ensta.librarymanager.modele;
import java.time.LocalDate;
public class Emprunt {
    private int idE;
    private Membre m;
    private Livre l;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt(){
        super();
    }

    public Emprunt(int idE,Membre m,Livre l,LocalDate dateE,LocalDate dateR){
        this.idE=idE;
        this.m=m;
        this.l=l;
        this.dateEmprunt=dateE;
        this.dateRetour=dateR;
    }

    public Emprunt(Membre m,Livre l,LocalDate dateE,LocalDate dateR){
        this.m=m;
        this.l=l;
        this.dateEmprunt=dateE;
        this.dateRetour=dateR;
    }

    public int getIdE(){
        return this.idE;}

    public Membre getM(){
        return this.m;}

    public Livre getL(){
        return this.l;}

    public LocalDate getDateEmprunt(){
        return this.dateEmprunt;}

    public LocalDate getDateRetour(){
        return this.dateRetour;}

    public void setIdE(int id){
        this.idE=id;
    }

    public void setMembre(Membre m){
        this.m=m;
    }

    public void setLivre(Livre l){
        this.l=l;
    }

    public void setDateEmprunt(LocalDate d){
        this.dateEmprunt=d;
    }

    public void setDateRetour(LocalDate d){
        this.dateRetour=d;
    }

    public String toString(){
        return String.format("id %d \n Membre :%s \n Livre :%s \n dateEmprunt : %s\n dateRetour : %s\n", idE, m, l, dateEmprunt, dateRetour);}


}
