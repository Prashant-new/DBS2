package de.hsh.dbs2.imdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Person {

	// in this setting, Persons are not updated, thus the attributes are final
	// else we would need setters, an update-method, etc.
	private Integer personId;
	private final String name;

	private Person(int personId, String name) {
		this.personId = personId;
		this.name = name;
	}
	
	private Person(ResultSet rs) throws SQLException {
		this(rs.getInt("personid"), rs.getString("name"));
	}

	public Person(String name) {
		personId = null;
		this.name = name;
	}

	public Integer getPersonId() {
		return personId;
	}

	public String getName() {
		return name;
	}


	public void insert() throws SQLException {
		/* TODO */
	}

	public void delete() throws SQLException {
		/* TODO */
	}

	public static Person findById(int personId) throws SQLException, DoesNotExistException {
		/* TODO */
		return null;
	}

	public static List<Person> findByNamePart(String name) throws SQLException {
		/* TODO */
		return new ArrayList<>();
	}

	public static Person findByName(String name) throws SQLException, DoesNotExistException {
		/* TODO */
		return null;
	}

}
