package de.hsh.dbs2.imdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.hsh.dbs2.imdb.util.DBConnection;

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
		int newID = PersistenceHelper.getId();
		String sql = "INSERT INTO person (personid, name) VALUES (?, ?)";

		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setInt(1, newID);
			stmt.setString(2, name);
			stmt.executeUpdate();
		}

		personId = newID;
	}

	public void delete() throws SQLException {
		/* TODO */
		if (personId == null) {
			throw new SQLException("Cannot delete an unsaved person");
		}

		String sql = "DELETE FROM person WHERE personId = ?";

		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setInt(1,personId);
			stmt.executeUpdate();
		}
	}

	public static Person findById(int personId) throws SQLException, DoesNotExistException {
		/* TODO */
		String sql = "Select personid, name FROM person WHERE personid = ?";

		try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setInt(1, personId);

			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()){
					return new Person(rs);
				}
			}
		}

		throw new DoesNotExistException("PersonID not found: " + personId);
	}

	public static List<Person> findByNamePart(String name) throws SQLException {
		/* TODO */
		String sql = """
						SELECT personid, name
						FROM person 
						WHERE name ILIKE ?
						ORDER BY name, personid
						"""; 
		List<Person> people = new ArrayList<>();
		
		try(PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)){
			stmt.setString(1, "%" + name + "%");

			try (ResultSet rs = stmt.executeQuery()){
				while(rs.next()){
					people.add(new Person(rs));
				}
			}
		}
		return people;
	}

	public static Person findByName(String name) throws SQLException, DoesNotExistException {
		/* TODO */
		String sql = "SELECT personid, name FROM person WHERE name = ?";

		try (PreparedStatement stmt =DBConnection.getConnection().prepareStatement(sql)){
			stmt.setString(1, name);

			try (ResultSet rs = stmt.executeQuery()) {
				if(rs.next()){
					return new Person(rs);
				}
			}
		}
		throw new DoesNotExistException("Person name not found: " + name);
	}

}
