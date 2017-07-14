package com.netopyr.reduxfx.examples.treeview.state;

import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This class represents a Scrum Sprint.
 * In Scrum a Sprint is a development iteration which is typically 1 to 4 weeks long.
 * A sprint has a title and can contain {@link Story}s that are planned to be finished in this
 * sprint.
 */
public class Sprint {
	private final int id;
	private final String title;

	private final Seq<Story> stories;

	private Sprint(int id, String title, Iterable<Story> stories) {
		this.id = id;
		this.title = title;
		this.stories = Array.ofAll(stories);
	}

	public static Sprint create() {
		return new Sprint(0, "", Array.empty());
	}

	public int getId() {
		return id;
	}

	public Sprint withId(int id) {
		return new Sprint(id, this.title, this.stories);
	}

	public String getTitle() {
		return title;
	}

	public Sprint withTitle(String title) {
		return new Sprint(this.id, title, this.stories);
	}

	public Seq<Story> getStories() {
		return stories;
	}

	public Sprint withStories(Iterable<Story> stories) {
		return new Sprint(this.id, this.title, stories);
	}

	public Sprint appendStories(Story... stories) {
		return appendStories(Array.of(stories));
	}

	public Sprint appendStories(Iterable<Story> stories) {
		return new Sprint(this.id, this.title, this.stories.appendAll(stories));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.toString();
	}
}
