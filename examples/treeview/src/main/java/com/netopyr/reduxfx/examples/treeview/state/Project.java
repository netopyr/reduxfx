package com.netopyr.reduxfx.examples.treeview.state;

import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This class represents a Scrum Project. In Scrum a project consists of many {@link Sprint}s.
 */
public class Project {
	private final Seq<Sprint> sprints;

	private Project(Seq<Sprint> sprints) {
		this.sprints = sprints;
	}

	public static Project create() {
		return new Project(Array.empty());
	}

	public Seq<Sprint> getSprints() {
		return sprints;
	}

	public Project withSprints(Iterable<Sprint> sprints) {
		return new Project(Array.ofAll(sprints));
	}

	public Project appendSprints(Iterable<Sprint> sprints) {
		return withSprints(this.sprints.appendAll(sprints));
	}

	public Project appendSprints(Sprint... sprints) {
		return appendSprints(Array.of(sprints));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.toString();
	}
}
