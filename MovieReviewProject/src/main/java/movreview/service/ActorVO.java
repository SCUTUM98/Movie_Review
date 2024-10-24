package movreview.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorVO {
	@JsonProperty("id")
	private String actorId;
	@JsonProperty("gender")
	private int gender;
	@JsonProperty("name")
	private String actName;
	@JsonProperty("also_known_as")
	private List<String> knownAs;
	@JsonProperty("profile_path")
	private String profilePath;
	@JsonProperty("character")
	private String character;
	private String birthday;
	private String deathday;
	@JsonProperty("known_for_department")
	private String status;
	@JsonProperty("place_of_birth")
	private String homeplace;
	
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	
	public List<String> getKnownAs() {
		return knownAs;
	}
	public void setKnownAs(List<String> knownAs) {
		this.knownAs = knownAs;
	}
	
	public String getProfilePath() {
		return profilePath;
	}
	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}
	
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getDeathday() {
		return deathday;
	}
	public void setDeathday(String deathday) {
		this.deathday = deathday;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getHomeplace() {
		return homeplace;
	}
	public void setHomeplace(String homeplace) {
		this.homeplace = homeplace;
	}
}
