package com.sakila.data;

import com.sakila.models.Film;
import java.sql.*;

/**
 * Implementación concreta y final de DataContext para la tabla film.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class FilmDAO extends DataContext<Film> {

    public FilmDAO() {
        super("film", "film_id");
    }

    @Override
    protected String buildInsertSQL(Film entity) {
        return "INSERT INTO film (title, description, release_year, language_id, rental_duration, rental_rate, replacement_cost) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void bindInsertParams(PreparedStatement stmt, Film entity) throws SQLException {
        stmt.setString(1, entity.getTitle());
        stmt.setString(2, entity.getDescription());
        stmt.setInt(3, entity.getReleaseYear());
        stmt.setInt(4, entity.getLanguageId());
        stmt.setInt(5, entity.getRentalDuration());
        stmt.setDouble(6, entity.getRentalRate());
        stmt.setDouble(7, entity.getReplacementCost());
    }

    @Override
    protected void assignGeneratedId(Film entity, int generatedId) {
        entity.setFilmId(generatedId);
    }

    @Override
    protected String buildUpdateSQL(Film entity) {
        return "UPDATE film SET title = ?, description = ?, release_year = ?, language_id = ?, rental_duration = ?, rental_rate = ?, replacement_cost = ? WHERE film_id = ?";
    }

    @Override
    protected void bindUpdateParams(PreparedStatement stmt, Film entity) throws SQLException {
        bindInsertParams(stmt, entity);
        stmt.setInt(8, entity.getFilmId());
    }

    @Override
    protected String buildSearchSQL(String filter) {
        return "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";
    }

    @Override
    protected void bindSearchParams(PreparedStatement stmt, String filter) throws SQLException {
        String pattern = "%" + filter + "%";
        stmt.setString(1, pattern);
        stmt.setString(2, pattern);
    }

    @Override
    protected Film mapResultSet(ResultSet rs) throws SQLException {
        return new Film(
            rs.getInt("film_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getInt("release_year"),
            rs.getInt("language_id"),
            rs.getInt("rental_duration"),
            rs.getDouble("rental_rate"),
            rs.getDouble("replacement_cost")
        );
    }

    @Override
    protected boolean validateForeignKeys(Film entity) {
        return existsForeignKey("language", "language_id", entity.getLanguageId());
    }
}