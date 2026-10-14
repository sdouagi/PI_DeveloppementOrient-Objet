package fr.insarouen.iti.prog.aventure;

import fr.insarouen.iti.prog.aventure.elements.Entite;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

/**
 * Classe Monde
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class Monde implements Serializable {
    static  Random random = new Random();
    private String nom ;
    private Map<String, Entite> entites  = new HashMap<>();
    
    //Constructeurs

    /**
     * Constructeur de monde à partir de son nom
     * 
     * @param nom : chaine de caractères du nom du monde
     */
    public Monde(String nom){
        this.nom = nom ;
    }

    //Méthodes

    /**
     * Permet d'obtenir le nom du monde
     * 
     * @return chaine de caractères du nom du monde
     */
    
    public String getNom(){
        return this.nom;
    }

    public String generateNom(String pref){
        String res ;
        boolean okay = false ; 
        do
            res = String.format("%s%s", pref, random.nextInt(150));
        while (getEntite(res) != null);
        return res ;
    }


    /**
     * Permet d'obtenir une entité du monde à partir de son nom
     * 
     * @param nom : nom de l'entité recherchée dans le monde
     * 
     * @return entite recherché si trouvée
     * @return null sinon
     */
    public Entite getEntite(String nom){
        return this.entites.get(nom);
        
        /*or(int i=0; i<this.tabEntite.length;i++){
            if (this.tabEntite[i].getNom().equals(nom))
                return this.tabEntite[i];
        }
        return null;*/
    }

    /**
     * Permet d'obtenir une entité du monde à partir de son nom
     *
     * 
     * @return entites recherché si trouvée
     *
     */
    public Collection<Entite> getEntites(){
        return new ArrayList<Entite>(this.entites.values());
        //return this.entites.values().toArray(new Entite[this.entites.size()]);
    }

    

    /**
     * Permet d'ajouter une entité dans le monde
     * 
     * @param entite : entite ajoutée dans le monde
     */
    public void ajouterEntite(Entite entite) throws NomDEntiteDejaUtiliseDansLeMondeException, EntiteDejaDansUnAutreMondeException{
        if (this.entites.containsKey(entite.getNom())) {
            throw new NomDEntiteDejaUtiliseDansLeMondeException("Entité déjà présente dans le monde");
        }

        if (entite.getMonde() != this) {
           throw new EntiteDejaDansUnAutreMondeException("L'entité appartient à un autre monde");
        }

         this.entites.put(entite.getNom(), entite);

    }
    
    /**
     * Redéfinition de toString
     * 
     * @return chaine de caractères décrivant le monde
     */
    public  String toString(){
        StringBuilder builder = new StringBuilder("nom monde ");
        builder.append(this.nom );
        for(Entite e : this.entites.values()){
            builder.append(" \n entité ");
            builder.append(e.getNom());
        }
        return builder.toString();
        
    }

    
}
