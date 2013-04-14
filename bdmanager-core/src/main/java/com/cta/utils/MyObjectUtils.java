package com.cta.utils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.util.ReflectionUtils;

public abstract class MyObjectUtils {

	/**
	 * Essaye de setter l'id d'un objet en appelant sa methode setId ayant pour parametre un objet du 
	 * meme type que le parametre 'id'. Si une telle methode n'existe pas, il ne se passe rien.
	 */
	public static void setId(Object object, Object id) {
		try {
            Method setIdMethod = ReflectionUtils.findMethod(object.getClass(), "setId", id.getClass());
            if(setIdMethod != null) {
                setIdMethod.invoke(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	/**
	 * Trie une liste soit en utilisant de maniere naturelle si les elements de la liste sont de type {@link Comparable},
	 * soit en utilisant la propriete id des elements si elle existe.
	 * Si le parametre reverse est a true, l'ordre de la liste est inverse.
	 */
    public static <T> void sortByNaturalOrId(List<T> list, boolean reverse) {
        Collections.sort(list, new Comparator<T>() {
            
            @Override
            @SuppressWarnings("unchecked")
            public int compare(T o1, T o2) {
                if(o1 instanceof Comparable) {
                    return ((Comparable<T>)o1).compareTo(o2);
                } else {
                    try {
                        Method getIdMethod = ReflectionUtils.findMethod(o1.getClass(), "getId");
                        if(getIdMethod == null) {
                            return 0;
                        } else {
                            return ((Long)getIdMethod.invoke(o1)).compareTo((Long) getIdMethod.invoke(o2));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } 
                }
            }
        });
        
        if(reverse) {
        	Collections.reverse(list);
        }
    }
    
    /**
     * Trie une liste soit en utilisant de maniere naturelle si les elements de la liste sont de type {@link Comparable},
     * soit en utilisant la propriete id des elements si elle existe.
     */
    public static <T> void sortByNaturalOrId(List<T> list) {
    	sortByNaturalOrId(list, false);
    }
}
