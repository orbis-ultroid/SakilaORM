package com.sakila.controllers;

import com.sakila.data.FilmDAO;
import com.sakila.models.Film;
import java.util.List;

/**
 * Controlador MVC para la gestión de películas (Film) y su colección List.
 * @author Estudiante UASD
 * @version 1.0
 */
public class FilmController {
    private final FilmDAO filmDAO;
    private List<Film> filmCache;

    /** Inicializa el controlador y carga la lista de películas. */
    public FilmController() {
        this.filmDAO = new FilmDAO();
        refresh();
    }

    /** Sincroniza la lista en memoria con los registros actuales de la base de datos. */
    public void refresh() {
        this.filmCache = filmDAO.get();
    }

    /**
     * Devuelve la lista en caché de películas.
     * @return Lista de objetos Film.
     */
    public List<Film> getAllFilms() {
        return filmCache;
    }

    /**
     * Busca una película por su ID.
     * @param id Identificador de la película.
     * @return Objeto Film o null.
     */
    public Film getFilmById(int id) {
        return filmDAO.get(id);
    }

    /**
     * Busca películas por título o descripción.
     * @param filter Texto de filtro.
     * @return Lista de películas coincidentes.
     */
    public List<Film> searchFilms(String filter) {
        return filmDAO.get(filter);
    }

    /**
     * Registra una nueva película.
     * @param film Película a crear.
     * @return true si fue creada.
     */
    public boolean createFilm(Film film) {
        boolean ok = filmDAO.post(film);
        if (ok) refresh();
        return ok;
    }

    /**
     * Actualiza una película existente.
     * @param film Película modificada.
     * @return true si fue actualizada.
     */
    public boolean updateFilm(Film film) {
        boolean ok = filmDAO.put(film);
        if (ok) refresh();
        return ok;
    }

    /**
     * Elimina una película por su ID.
     * @param id Identificador de película.
     * @return true si fue eliminada.
     */
    public boolean deleteFilm(int id) {
        boolean ok = filmDAO.delete(id);
        if (ok) refresh();
        return ok;
    }
}