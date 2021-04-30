package org.mql.crowddonating.models;

import javax.persistence.*;

@Entity
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String path;

	@Column
	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "association_id")
	private Association association;

	@ManyToOne
	@JoinColumn(name = "case_id")
	private Case aCase;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	public File() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association association) {
		this.association = association;
	}

	public Case getCase() {
		return aCase;
	}

	public void setCase(Case aCase) {
		this.aCase = aCase;
	}

	public Case getaCase() {
		return aCase;
	}

	public void setaCase(Case aCase) {
		this.aCase = aCase;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}