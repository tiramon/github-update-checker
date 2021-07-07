# Github update checker

This library offers a Service to extract the version from the manifest of a given class and compare it with the version of the latest release of a project on github.

**This will only work if SemanticVersions are used.**

Github releases should be created out of tags whose name is a SemanticVersion prefixed with a 'v' like 'v1.0.0'

## How to add the Version to the Manifest

You can add the version of your pom to your jar manifest by setting following configuration to true
```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-jar-plugin</artifactId>
			<version>3.1.0</version>
			<configuration>
				<archive>
					<manifest>
						<addDefaultImplementationEntries>true</addDefaultImplementationEntries>
					</manifest>
				</archive>
			</configuration
		</plugin>
	</plugins>
</build>
```	

## Example project
[https://github.com/tiramon/du-map-companion](https://github.com/tiramon/du-map-companion)