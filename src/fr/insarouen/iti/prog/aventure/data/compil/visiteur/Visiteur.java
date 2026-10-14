package fr.insarouen.iti.prog.aventure.data.compil.visiteur;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface Visiteur {

    default public void visiter(Object leVisitable) throws Throwable {
	Class typeDuVisitable = leVisitable.getClass();
        boolean methodeInvoquee = false;
        Method methode;
        do {
            try {
                methode = getClass().getMethod("visiter", new Class[] {typeDuVisitable});
                methodeInvoquee = true;
                methode.invoke(this, new Object[] {leVisitable});
            } catch (NoSuchMethodException e) {
                if (typeDuVisitable != Object.class)
                    typeDuVisitable = typeDuVisitable.getSuperclass();                
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        } while (!methodeInvoquee && typeDuVisitable != Object.class);
	if (!methodeInvoquee)
	    throw new NoSuchMethodException("Impossible de trouver la bonne méthode visiter");
    }      
}
