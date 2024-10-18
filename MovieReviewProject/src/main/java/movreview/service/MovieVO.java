package movreview.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieVO {
	@JsonProperty("id")
	private int movieId;
	@JsonProperty("original_title")
	private String titleKr;
	@JsonProperty("title")
	private String titleEn;
	@JsonProperty("genre_ids")
	private List<String> genre;
	private String genreDB;
	@JsonProperty("release_date")
	private String releaseDate;
	@JsonProperty("overview")
	private String overview;
	@JsonProperty("backdrop_path")
	private String backdropPath;
	@JsonProperty("poster_path")
	private String posterPath;
	@JsonProperty("belongs_to_collection")
	private CollectionVO belongsToCollection;
	@JsonProperty("collection_id")
	private int collectionId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("tagline")
	private String tagline;
	
	private boolean adult;
	private String original_language;
	private boolean video;
	
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	public String getTitleKr() {
		return titleKr;
	}
	public void setTitleKr(String titleKr) {
		this.titleKr = titleKr;
	}
	
	public String getTitleEn() {
		return titleEn;
	}
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
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
	
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	public String getBackdropPath() {
		return backdropPath;
	}
	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}
	
	public String getPosterPath() {
		return posterPath;
	}
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	
	public CollectionVO getBelongsToCollection() {
		return belongsToCollection;
	}
	public void setBelongsToCollection(CollectionVO belongsToCollection) {
		this.belongsToCollection = belongsToCollection;
	}
	
	public int getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTagline() {
		return tagline;
	}
	public void setTagline(String tagline) {
		this.tagline =  tagline;
	}
	
}
