package de.tiramon.github.update.model;

import com.google.gson.annotations.SerializedName;

public class GitHubAsset {
	private String name;
	private long size;
	@SerializedName("browser_download_url")
	private String browserDownloadUrl;

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public String getBrowserDownloadUrl() {
		return browserDownloadUrl;
	}

}
