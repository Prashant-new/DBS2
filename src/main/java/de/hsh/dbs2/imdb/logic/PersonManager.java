package de.hsh.dbs2.imdb.logic;

import java.util.ArrayList;
import java.util.List;

import de.hsh.dbs2.imdb.persistence.Person;
import de.hsh.dbs2.imdb.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class PersonManager {

	/**
	 * Liefert eine Liste aller Personen, deren Name den Suchstring enthaelt.
	 * @param name Suchstring
	 * @return Liste mit passenden Personennamen, die in der Datenbank eingetragen sind.
	 * @throws Exception Beschreibt evtl. aufgetretenen Fehler
	 */
	public List<String> getPersonList(String name) throws Exception {
		/* TODO */
		Connection connection = DBConnection.getConnection();

		try {
			List<String> names = new ArrayList<>();

			for (Person person : Person.findByNamePart(name)){
				names.add(person.getName());
			}

			connection.commit();
			return names;
		} catch (Exception failure){
			rollback(connection, failure);
			throw failure;
		}
	}

	/**
	 * Liefert die ID einer Person, deren Name genau name ist. Wenn die Person nicht existiert,
	 * wird eine Exception geworfen.
	 * @param name Exakter Name der Person
	 * @return ID der Person
	 * @throws Exception Beschreibt evtl. aufgetretenen Fehler
	 */
	public int getPerson(String name) throws Exception {
		/* TODO */
		Connection connection =DBConnection.getConnection();

		try {
			int personId =Person.findByName(name).getPersonId();

			connection.commit();
			return personId;
		} catch (Exception failure){
			rollback(connection, failure);
			throw failure;
		}
		
	}

	private void rollback(Connection connection, Exception failure) {
    	try {
        	connection.rollback();
    	}catch (SQLException rollbackFailure) {
        	failure.addSuppressed(rollbackFailure);
    	}
	}
}
