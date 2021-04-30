package org.mql.crowddonating.models;

import javax.persistence.*;
import java.util.List;
import java.util.Vector;

@Entity
public class Association extends User {

	@Column
	private String phone;

	@Column
	private String address;

	@Column
	private String cover = "cover.jpg";

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Column(columnDefinition = "text")
	private String description;

	private String webSite;

	@OneToMany(mappedBy = "association", fetch = FetchType.LAZY)
	private List<Case> cases;

	@OneToMany(mappedBy = "association", fetch = FetchType.LAZY)
	private List<Project> projects;

	@OneToMany(mappedBy = "association", fetch = FetchType.LAZY)
	private List<File> files;

	@OneToMany(mappedBy = "association", fetch = FetchType.LAZY)
	private List<Event> events;

	@ManyToMany
	@JoinTable(name = "association_domain", joinColumns = @JoinColumn(name = "domain_id"), inverseJoinColumns = @JoinColumn(name = "association_id"))
	private List<Domain> domains;

	public Association() {
		domains = new Vector<>();
		files = new Vector<>();
		projects = new Vector<Project>();
	}

	public Association(long id) {
		super();
		this.id = id;
	}

	public void addCase(Case aCase) {
		cases.add(aCase);
	}

	public void addFile(File file) {
		files.add(file);
	}

	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void addproject(Project project) {
		projects.add(project);
	}

	public void addDomain(Domain domain) {
		domains.add(domain);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Case> getCases() {
		return cases;
	}

	public void setCases(List<Case> cases) {
		this.cases = cases;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

	public String getCover() {
		return cover;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
}