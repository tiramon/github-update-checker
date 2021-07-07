package de.tiramon.github.update.model;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.Gson;

import de.tiramon.github.update.model.GitHubAsset;

public class GitHubAssetTest {
	Gson gson = new Gson();
	// JSON taken from https://docs.github.com/en/rest/reference/repos#releases
	String assetJson = "{\"url\": \"https://api.github.com/repos/octocat/Hello-World/releases/assets/1\",\"browser_download_url\": \"https://github.com/octocat/Hello-World/releases/download/v1.0.0/example.zip\",\"id\": 1,\"node_id\": \"MDEyOlJlbGVhc2VBc3NldDE=\",\"name\": \"example.zip\",\"label\": \"short description\",\"state\": \"uploaded\",\"content_type\": \"application/zip\",\"size\": 1024,\"download_count\": 42,\"created_at\": \"2013-02-27T19:35:32Z\",\"updated_at\": \"2013-02-27T19:35:32Z\",\"uploader\": {\"login\": \"octocat\",\"id\": 1,\"node_id\": \"MDQ6VXNlcjE=\",\"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\",\"gravatar_id\": \"\",\"url\": \"https://api.github.com/users/octocat\",\"html_url\": \"https://github.com/octocat\",\"followers_url\": \"https://api.github.com/users/octocat/followers\",\"following_url\": \"https://api.github.com/users/octocat/following{/other_user}\",\"gists_url\": \"https://api.github.com/users/octocat/gists{/gist_id}\",\"starred_url\": \"https://api.github.com/users/octocat/starred{/owner}{/repo}\",\"subscriptions_url\": \"https://api.github.com/users/octocat/subscriptions\",\"organizations_url\": \"https://api.github.com/users/octocat/orgs\",\"repos_url\": \"https://api.github.com/users/octocat/repos\",\"events_url\": \"https://api.github.com/users/octocat/events{/privacy}\",\"received_events_url\": \"https://api.github.com/users/octocat/received_events\",\"type\": \"User\",\"site_admin\": false}}";

	@Test
	public void parseJson() {
		GitHubAsset asset = gson.fromJson(assetJson, GitHubAsset.class);
		assertEquals("example.zip", asset.getName());
		assertEquals(1024, asset.getSize());
		assertEquals("https://github.com/octocat/Hello-World/releases/download/v1.0.0/example.zip", asset.getBrowserDownloadUrl());
	}
}
