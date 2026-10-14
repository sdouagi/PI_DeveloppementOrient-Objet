package fr.insarouen.iti.prog.aventure.data.compil.ast;

import fr.insarouen.iti.prog.aventure.EtatDuJeu;

public class Argument {
    
}

// public class Argument{
//     public enum Type {IDENTIFIANT, ENTIER, CHAINE, ETATJEU, APPEL }
    
//     private Type type;
//     private Object arg;


//     public Argument(Type type, Object arg ){
//         this.type = type;
//         this.arg = arg.toString();
//     }
    
//     public Type getType(){
//         return this.type;
//     }

//     public int getValeur() throws TypeException{
//         if (this.type != Type.ENTIER) {
//             throw new TypeException("c'est pas un entier");
//         }
//         return (int)this.arg;
//     }

//     public String getNom() throws TypeException{
//         if (this.type == Type.CHAINE) {
//             throw new TypeException("c'est un string");
//         }
//         return this.arg.toString();
//     }

//     public AppelFonction getAppel() throws TypeException{
//         if (this.type == Type.APPEL) {
//             throw new TypeException("c'est un string");
//         }
//         return (AppelFonction) this.arg;
//     }

//     public EtatDuJeu getEtat() throws TypeException{
//         if (this.type == Type.ETATJEU) {
//             throw new TypeException("c'est un string");
//         }
//         return switch (this.arg.toString()){
//             case "SUCCES" ->EtatDuJeu.SUCCES;
//             case "ECHEC" -> EtatDuJeu.ECHEC;
//             case "ENCOURS" -> EtatDuJeu.ENCOURS;
//             default -> null;
//         };
//     }


//     @Override
//     public  String toString(){
//         return switch(this.type) {
//             case ENTIER -> String.format("type : %s\n arg : %s",this.type,Integer.toString((int) this.arg));
//             case CHAINE,IDENTIFIANT, ETATJEU, APPEL -> String.format("type : %s\n arg : %s",this.type.toString(),this.arg.toString() );
//         };
//     }
// }