package com.netopyr.reduxfx.examples.treeview.state;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This class represents a Scrum Story. In Scrum a Story is related some kind of development work unit
 * that has to be implemented. This can be a feature or a bugfix or some other unit of work.
 */
public class Story {
	private final int id;
	private final String title;

	private Story(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public static Story create() {
		return new Story(-1, "");
	}

	public int getId() {
		return id;
	}

	public Story withId(int id) {
		return new Story(id, this.title);
	}

	public String getTitle() {
		return title;
	}

	public Story withTitle(String title) {
		return new Story(this.id, title);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.toString();
	}
}
