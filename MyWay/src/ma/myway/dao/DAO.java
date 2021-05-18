package ma.myway.dao;

import java.sql.Connection;
import java.util.Set;

public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO(Connection conn) {
		this.connect = conn;
	}

	/**
	 * M�thode de cr�ation
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean create(T obj);

	/**
	 * M�thode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean delete(T obj); // ali

	/**
	 * M�thode de mise � jour
	 * 
	 * @param obj
	 * @return boolean
	 */
	public boolean update(T oldobj, T newobj) {
		return false;
	}// ali

	/**
	 * M�thode de recherche des informations
	 * 
	 * @param id
	 * @return T (peut retourner null )
	 */
	public abstract T find(String id);

	/**
	 * M�thode qui permet de charger tout les enregistrement
	 * 
	 * @param id
	 * @return Set<T>
	 */
	public abstract Set<T> all();

}
