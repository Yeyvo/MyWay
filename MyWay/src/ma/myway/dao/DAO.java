package ma.myway.dao;

import java.sql.Connection;
import java.util.Set;

public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO(Connection conn) {
		this.connect = conn;
	}

	/**
	 * Méthode de création
	 * 
	 * @param obj
	 * @return boolean
	 */
	//public abstract boolean create(T obj);

	/**
	 * Méthode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 */
	//public abstract boolean delete(T obj);

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @return boolean
	 */
	//public abstract boolean update(T obj);

	/**
	 * Méthode de recherche des informations
	 * 
	 * @param id
	 * @return T (peut retourner null )
	 */
	public abstract T find(String id);
	
	/**
	 * Méthode qui permet de charger tout les enregistrement
	 * 
	 * @param id
	 * @return Set<T> 
	 */
	public abstract Set<T> all();
}
