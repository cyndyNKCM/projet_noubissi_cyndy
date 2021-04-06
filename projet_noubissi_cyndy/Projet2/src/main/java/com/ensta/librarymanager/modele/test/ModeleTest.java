package com.ensta.librarymanager.modele.test;

import java.time.LocalDate;

import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.modele.Emprunt;

public class ModeleTest {
    public static void main(String[] args){

        Livre l=new Livre(1,"Cahier d'un retour au pays natal","Aime Cesaire","9780814250204");
        Membre m=new Membre(1,"Noubissi","Cyndy","France","cyndynoubissi2@gmail.com","0753427757",abonnement.BASIC);
        Emprunt e=new Emprunt(1,m,l,LocalDate.parse("2021-03-09"),LocalDate.parse("2021-03-11"));

        String affichage1=l.toString();
        System.out.println(affichage1);

        String affichage2=m.toString();
        System.out.println(affichage2);

        String affichage3=e.toString();
        System.out.println(affichage3);
    }

}
