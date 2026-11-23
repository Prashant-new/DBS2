package de.hsh.dbs2.imdb.logic;

import java.util.ArrayList;
import java.util.List;

import de.hsh.dbs2.imdb.persistence.Genre;

public class GenreManager {

	/**
	 * Ermittelt eine vollstaendige Liste aller in der Datenbank abgelegten Genres
	 * Die Genres werden alphabetisch sortiert zurueckgeliefert.
	 * @return Alle Genre-Namen als String-Liste
	 * @throws Exception error describing e.g. the database problem
	 */
	public List<String> getGenres() throws Exception {
		/* TODO */
		List<String> names = new ArrayList<>();

		for(Genre genre : Genre.findAll()){
			names.add(genre.getGenre());
		}

		return names;
	}


}
