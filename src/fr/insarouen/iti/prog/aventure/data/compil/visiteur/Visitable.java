package fr.insarouen.iti.prog.aventure.data.compil.visiteur;

public interface Visitable {

    public void accepter(Visiteur v) throws Throwable;
}
