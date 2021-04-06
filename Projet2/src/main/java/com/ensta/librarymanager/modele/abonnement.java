package com.ensta.librarymanager.modele;

public enum abonnement {
    BASIC(2),
    PREMIUM(5),
    VIP(20);

    final private int quota;

    abonnement(int quota){
        this.quota=quota;
    }

    public int getQuota(){
        return this.quota;
    }


}
