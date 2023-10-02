/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Kizo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public final class Movie implements Comparable<Movie> {

    @XmlAttribute
    private int id;
    private String title;
    @XmlElement(name = "pubdate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDateTime pubDate;
    @XmlElement(name = "originaltitle")
    private String originalTitle;
    private String description;
    private int duration;
    private int year;
    private String poster;
    private String link;
    private String reservation;
    @XmlElement(name = "displaydate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDateTime displayDate;
    private String performances;
    private String trailer;
    @XmlElementWrapper
    @XmlElement(name = "genre")
    private List<Genre> genres;
    @XmlElementWrapper
    @XmlElement(name = "actor")
    private List<Person> actors;
    @XmlElementWrapper
    @XmlElement(name = "director")
    private List<Person> directors;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Movie() {
    }

    public Movie(String title, LocalDateTime pubDate, String originalTitle, String description, int duration, int year, String poster, String link, String reservation, LocalDateTime displayDate, String performances, String trailer) {
        this.title = title;
        this.pubDate = pubDate;
        this.originalTitle = originalTitle;
        this.description = description;
        this.duration = duration;
        this.year = year;
        this.poster = poster;
        this.link = link;
        this.reservation = reservation;
        this.displayDate = displayDate;
        this.performances = performances;
        this.trailer = trailer;
    }

    public Movie(int id, String title, LocalDateTime pubDate, String originalTitle, String description, int duration, int year, String poster, String link, String reservation, LocalDateTime displayDate, String performances, String trailer) {
        this(title, pubDate, originalTitle, description, duration, year, poster, link, reservation, displayDate, performances, trailer);
        this.id = id;
    }

    public Movie(int id, String title, LocalDateTime pubDate, String originalTitle, String description, int duration, int year, String poster, String link, String reservation, LocalDateTime displayDate, String performances, String trailer, List<Genre> genres, List<Person> actors, List<Person> directors) {
        this(id, title, pubDate, originalTitle, description, duration, year, poster, link, reservation, displayDate, performances, trailer);
        this.genres = genres;
        this.actors = actors;
        this.directors = directors;
    }

    public String getReservation() {
        return this.reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    @Override
    public int compareTo(Movie otherMovie) {
        return this.title.compareTo(otherMovie.title);
    }

    @Override
    public String toString() {
        return this.title;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPubDate() {
        return this.pubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getDisplayDate() {
        return this.displayDate;
    }

    public void setDisplayDate(LocalDateTime displayDate) {
        this.displayDate = displayDate;
    }

    public String getPerformances() {
        return this.performances;
    }

    public void setPerformances(String performances) {
        this.performances = performances;
    }

    public String getTrailer() {
        return this.trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public List<Genre> getGenres() {
        return this.genres == null ? Collections.emptyList() : this.genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Person> getActors() {
        return this.actors == null ? Collections.emptyList() : this.actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public List<Person> getDirectors() {
        return this.directors == null ? Collections.emptyList() : this.directors;
    }

    public void setDirectors(List<Person> directors) {
        this.directors = directors;
    }

    public void addActor(Person actor) {
        if (this.actors == null) {
            this.actors = new ArrayList<>();
        }
        this.actors.add(actor);
    }

    public void addDirector(Person director) {
        if (this.directors == null) {
            this.directors = new ArrayList<>();
        }
        this.directors.add(director);
    }

    public void addGenre(Genre genre) {
        if (this.genres == null) {
            this.genres = new ArrayList<>();
        }
        this.genres.add(genre);
    }

}
