package movreview.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TvVO {
	@JsonProperty("backdrop_path")
	private String backdropPath;
	private int id;
	private String name;
	@JsonProperty("original_name")
	private String originalName;
	private String overview;
	@JsonProperty("poster_path")
	private String posterPath;
	private boolean adult;
	@JsonProperty("genre_ids")
	private List<String> genre;
	private String genreDB;
	@JsonProperty("first_air_date")
	private String firstAirDate;
	@JsonProperty("origin_country")
	private List<String> originCountry;
	
	private boolean last;
	

	public String getBackdropPath() {
		return backdropPath;
	}
	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getPosterPath() {
		return posterPath;
	}
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	public boolean isAdult() {
		return adult;
	}
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	public List<String> getGenre() {
		return genre;
	}
	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
	public String getGenreDB() {
		return genreDB;
	}
	public void setGenreDB(String genreDB) {
		this.genreDB = genreDB;
	}
	public String getFirstAirDate() {
		return firstAirDate;
	}
	public void setFirstAirDate(String firstAirDate) {
		this.firstAirDate = firstAirDate;
	}
	public List<String> getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(List<String> originCountry) {
		this.originCountry = originCountry;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
}
