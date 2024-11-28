package movreview.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TvSeasonVO {
	private int id;
	@JsonProperty("series_id")
	private int seriesId;
	private String name;
	@JsonProperty("air_date")
	private String airDate;
	@JsonProperty("episode_count")
	private int episodeCount;
	@JsonProperty("poster_path")
	private String posterPath;
	private String overview;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAirDate() {
		return airDate;
	}
	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}
	public int getEpisodeCount() {
		return episodeCount;
	}
	public void setEpisodeCount(int episodeCount) {
		this.episodeCount = episodeCount;
	}
	public String getPosterPath() {
		return posterPath;
	}
	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}

}
