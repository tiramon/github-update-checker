package de.tiramon.github.update.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;

public class SemanticVersionTest {

	@Test
	public void emptyStringVersion() throws ParseException {
		SemanticVersion version = new SemanticVersion((String) null);
		assertFalse(version.isStable());
		assertEquals(0, version.major);
		assertEquals(0, version.minor);
		assertEquals(0, version.patch);
		assertNotNull(version.preRelase);
		assertEquals(0, version.preRelase.length);
		assertNotNull(version.buildMeta);
		assertEquals(0, version.buildMeta.length);
	}

	@Test
	public void inputVersion() {
		SemanticVersion version = new SemanticVersion(1, 2, 3);
		assertTrue(version.isStable());
		assertEquals(1, version.major);
		assertEquals(2, version.minor);
		assertEquals(3, version.patch);
		assertNotNull(version.preRelase);
		assertEquals(0, version.preRelase.length);
		assertNotNull(version.buildMeta);
		assertEquals(0, version.buildMeta.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeMajorVersionNumber() {
		new SemanticVersion(-1, 0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeMinorVersionNumber() {
		new SemanticVersion(0, -1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativePatchVersionNumber() {
		new SemanticVersion(0, 0, -1);
	}

	@Test
	public void inputVersionComplete() {
		SemanticVersion version = new SemanticVersion(1, 2, 3, new String[0], new String[0]);
		assertTrue(version.isStable());
		assertEquals(1, version.major);
		assertEquals(2, version.minor);
		assertEquals(3, version.patch);
		assertNotNull(version.preRelase);
		assertEquals(0, version.preRelase.length);
		assertNotNull(version.buildMeta);
		assertEquals(0, version.buildMeta.length);

		assertFalse(version.hasPreRelease("ABC"));
		assertFalse(version.hasBuildMeta("DEF"));
	}

	@Test
	public void inputVersionCompleteWithMetaPrerelease() {
		SemanticVersion version = new SemanticVersion(1, 2, 3, new String[] { "ABC" }, new String[] { "DEF" });
		assertFalse(version.isStable());
		assertEquals(1, version.major);
		assertEquals(2, version.minor);
		assertEquals(3, version.patch);
		assertNotNull(version.preRelase);
		assertEquals(1, version.preRelase.length);
		assertEquals("ABC", version.preRelase[0]);
		assertTrue(version.hasPreRelease("ABC"));
		assertFalse(version.hasPreRelease("GHI"));
		assertNotNull(version.buildMeta);
		assertEquals(1, version.buildMeta.length);
		assertEquals("DEF", version.buildMeta[0]);
		assertTrue(version.hasBuildMeta("DEF"));
		assertFalse(version.hasBuildMeta("GHI"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidPrerelease() {
		new SemanticVersion(1, 2, 3, new String[] { "ABC?" }, new String[] { "DEF" });
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidBuildMeta() {
		new SemanticVersion(1, 2, 3, new String[] { "ABC" }, new String[] { "DEF?" });
	}

	@Test(expected = ParseException.class)
	public void invalidMajorString() throws ParseException {
		new SemanticVersion("a.0.0");
	}

	@Test(expected = ParseException.class)
	public void invalidMinorString() throws ParseException {
		new SemanticVersion("0.a.0");
	}

	@Test(expected = ParseException.class)
	public void invalidPatchString() throws ParseException {
		new SemanticVersion("0.0.a");
	}

	@Test(expected = ParseException.class)
	public void invalidDelimiterString() throws ParseException {
		new SemanticVersion("0,0,0");
	}

	@Test(expected = ParseException.class)
	public void missingMinorString() throws ParseException {
		new SemanticVersion("0..0");
	}

	@Test(expected = ParseException.class)
	public void onlyMajorString() throws ParseException {
		new SemanticVersion("1");
	}

	@Test(expected = ParseException.class)
	public void onlyMajorAndMinorString() throws ParseException {
		new SemanticVersion("1.1");
	}

	@Test(expected = NullPointerException.class)
	public void nullPrerelease() {
		new SemanticVersion(0, 0, 0, null, new String[0]);
	}

	@Test(expected = IllegalArgumentException.class)
	public void prereleaseContainNull() {
		new SemanticVersion(0, 0, 0, new String[] { null }, new String[0]);
	}

	@Test(expected = IllegalArgumentException.class)
	public void metaContainNull() {
		new SemanticVersion(0, 0, 0, new String[0], new String[] { null });
	}

	@Test(expected = NullPointerException.class)
	public void nullMeta() {
		new SemanticVersion(0, 0, 0, new String[0], null);
	}

	@Test
	public void validPatchFromString() throws ParseException {
		SemanticVersion version = new SemanticVersion("0.0.1");
		assertFalse(version.isStable());
		assertEquals(0, version.major);
		assertEquals(0, version.minor);
		assertEquals(1, version.patch);
		assertNotNull(version.preRelase);
		assertEquals(0, version.preRelase.length);
		assertNotNull(version.buildMeta);
		assertEquals(0, version.buildMeta.length);
	}

	@Test
	public void stableCheck() throws ParseException {
		SemanticVersion version;
		// only patch version
		version = new SemanticVersion("0.0.1");
		assertFalse("Major version < 1 should not be stable", version.isStable());

		version = new SemanticVersion("0.0.1-SNAPSSHOT");
		assertFalse("Major version < 1 should not be stable", version.isStable());

		version = new SemanticVersion("0.0.1+Something");
		assertFalse("Major version < 1 should not be stable", version.isStable());

		// only minor version
		version = new SemanticVersion("0.1.0");
		assertFalse("Major version < 1 should not be stable", version.isStable());

		version = new SemanticVersion("0.1.0-SNAPSHOT");
		assertFalse("Major version < 1 should not be stable", version.isStable());

		version = new SemanticVersion("0.1.0+Something");
		assertFalse("Major version < 1 should not be stable", version.isStable());

		// only major version
		version = new SemanticVersion("1.0.0");
		assertTrue("Major version > 0 without preRelease should be stable", version.isStable());

		// major prerelease version
		version = new SemanticVersion("1.0.0-SNAPSHOT");
		assertFalse("Major version > 0 with preRelease should not be stable", version.isStable());

		// major meta version
		version = new SemanticVersion("1.0.0+Something");
		assertTrue("Major version > 0 without preRelease should be stable", version.isStable());

		// major prerelease meta version
		version = new SemanticVersion("1.0.0-SNAPSHOT+Something");
		assertFalse("Major version > 0 with preRelease should not be stable", version.isStable());
	}

	@Test
	public void isUpdate() throws ParseException {
		SemanticVersion version = new SemanticVersion("2.3.4");
		SemanticVersion snapshotversion = new SemanticVersion("2.3.4-SNAPSHOT");

		assertFalse(new SemanticVersion("0.0.0").isUpdateFor(version));
		assertFalse(new SemanticVersion("0.0.1").isUpdateFor(version));
		assertFalse(new SemanticVersion("0.1.0").isUpdateFor(version));
		assertFalse(new SemanticVersion("1.0.0").isUpdateFor(version));
		assertFalse(new SemanticVersion("2.0.0").isUpdateFor(version));
		assertFalse(new SemanticVersion("2.3.4").isUpdateFor(version));
		assertFalse(new SemanticVersion("2.3.4+Something").isUpdateFor(version));

		assertTrue(new SemanticVersion("3.0.0-SNAPSHOT").isUpdateFor(version));
		assertTrue(new SemanticVersion("2.4.0-SNAPSHOT").isUpdateFor(version));
		assertTrue(new SemanticVersion("2.3.5-SNAPSHOT").isUpdateFor(version));

		assertTrue(new SemanticVersion("3.0.0").isUpdateFor(version));
		assertTrue(new SemanticVersion("2.4.0").isUpdateFor(version));
		assertTrue(new SemanticVersion("2.3.5").isUpdateFor(version));
		assertTrue(new SemanticVersion("2.3.5+Soemthing").isUpdateFor(version));

		assertFalse(snapshotversion.isUpdateFor(version));
		assertTrue(version.isUpdateFor(snapshotversion));
	}

	@Test
	public void isCompatibleUpdate() throws ParseException {
		SemanticVersion version = new SemanticVersion("2.3.4");
		SemanticVersion snapshotversion = new SemanticVersion("2.3.4-SNAPSHOT");

		assertFalse(new SemanticVersion("0.0.0").isCompatibleUpdateFor(version));
		assertFalse(new SemanticVersion("0.0.1").isCompatibleUpdateFor(version));
		assertFalse(new SemanticVersion("0.1.0").isCompatibleUpdateFor(version));
		assertFalse(new SemanticVersion("1.0.0").isCompatibleUpdateFor(version));
		assertFalse(new SemanticVersion("2.0.0").isCompatibleUpdateFor(version));
		assertFalse(new SemanticVersion("2.3.4").isCompatibleUpdateFor(version));
		assertFalse(new SemanticVersion("2.3.4+Something").isCompatibleUpdateFor(version));

		assertFalse(new SemanticVersion("3.0.0-SNAPSHOT").isCompatibleUpdateFor(version));
		assertTrue(new SemanticVersion("2.4.0-SNAPSHOT").isCompatibleUpdateFor(version));
		assertTrue(new SemanticVersion("2.3.5-SNAPSHOT").isCompatibleUpdateFor(version));

		assertFalse(new SemanticVersion("3.0.0").isCompatibleUpdateFor(version));
		assertTrue(new SemanticVersion("2.4.0").isCompatibleUpdateFor(version));
		assertTrue(new SemanticVersion("2.3.5").isCompatibleUpdateFor(version));
		assertTrue(new SemanticVersion("2.3.5+Soemthing").isCompatibleUpdateFor(version));

		assertFalse(snapshotversion.isCompatibleUpdateFor(version));
		assertTrue(version.isCompatibleUpdateFor(snapshotversion));
	}

	@Test
	public void equals() throws ParseException {
		SemanticVersion version = new SemanticVersion("2.3.4");
		assertTrue(version.equals(version));

		assertTrue(new SemanticVersion("2.3.4").equals(new SemanticVersion("2.3.4")));
		assertFalse(new SemanticVersion("2.3.4").equals(new SemanticVersion("2.3.4-SNAPSHOT")));
		assertTrue(new SemanticVersion("2.3.4-SNAPSHOT").equals(new SemanticVersion("2.3.4-SNAPSHOT")));
		assertFalse(new SemanticVersion("2.3.4-RC1").equals(new SemanticVersion("2.3.4-SNAPSHOT")));
		assertFalse(new SemanticVersion("2.3.4").equals(new SemanticVersion("2.3.4+Something")));
		assertTrue(new SemanticVersion("2.3.4+Something").equals(new SemanticVersion("2.3.4+Something")));
		assertFalse(new SemanticVersion("2.3.4+Something").equals(new SemanticVersion("2.3.4+SomethingElse")));
		assertFalse(new SemanticVersion("2.3.4").equals(new SemanticVersion("2.3.3")));
		assertFalse(new SemanticVersion("2.3.4").equals(new SemanticVersion("2.2.4")));
		assertFalse(new SemanticVersion("2.3.4").equals(new SemanticVersion("1.3.4")));

		assertFalse(new SemanticVersion("2.3.4").equals("2.3.4"));
		assertFalse(new SemanticVersion("2.3.4").equals(null));
	}
}
