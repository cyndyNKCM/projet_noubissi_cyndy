package com.ensta.librarymanager.modele;

public class Membre {
    private int idM;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private abonnement a;

    public Membre(){
        super();
    }

    public Membre(int idM,String nom,String prenom,String adresse,String email,String telephone,abonnement a){
        this.idM=idM;
        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
        this.email=email;
        this.telephone=telephone;
        this.a=a;
    }

    public Membre(String nom,String prenom,String adresse,String email,String telephone){
        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
        this.email=email;
        this.telephone=telephone;
        this.a=abonnement.BASIC;
    }


    public int getIdM(){
        return this.idM;}

    public String getNom(){
        return this.nom;}

    public String getPrenom(){
        return this.prenom;}

    public String getAdresse(){
        return this.adresse;}

    public String getEmail(){
        return this.email;}

    public String getTel(){
        return this.telephone;}

    public abonnement getAbonnement(){
        return this.a;}

    public void setIdM(int id){
        this.idM=id;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    public void setAdresse(String adresse){
        this.adresse=adresse;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setTelephone(String tel){
        this.telephone=tel;
    }

    public void setA(abonnement a){
        this.a=a;
    }

    public String toString(){
        return String.format("id %d \n nom :%s \n prenom :%s \n adresse : %s\n email : %s\n telephone : %s\n abonnement : %s\n" , idM, nom, prenom, adresse, email, telephone, a );}

}
