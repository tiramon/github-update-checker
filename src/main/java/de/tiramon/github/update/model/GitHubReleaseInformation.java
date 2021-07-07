package de.tiramon.github.update.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class GitHubReleaseInformation {
	@SerializedName("html_url")
	private String htmlUrl;
	@SerializedName("tag_name")
	private String tagName;
	@SerializedName("published_at")
	private Date publishedAt;
	private GitHubAsset[] assets;

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public String getTagName() {
		return tagName;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public GitHubAsset[] getAssets() {
		return assets;
	}

}
