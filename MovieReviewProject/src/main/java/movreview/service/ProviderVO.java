package movreview.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderVO {
	private int movieId;
	@JsonProperty("logo_path")
	private String logoPath;
	@JsonProperty("provider_id")
	private int providerId;
	@JsonProperty("provider_name")
	private String providerName;
	@JsonProperty("display_priority")
	private String displayPriority;
	
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	public int getProviderId() {
		return providerId;
	}
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	public String getDisplayPriority() {
		return displayPriority;
	}
	public void setDisplayPriority(String displayPriority) {
		this.displayPriority = displayPriority;
	}
}
