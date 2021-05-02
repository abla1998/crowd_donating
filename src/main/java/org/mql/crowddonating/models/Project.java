package org.mql.crowddonating.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;
	private String slug;

	@Column(columnDefinition = "text")
	private String description;
	private int amount;
	private Date deadLine;
	private Date postedDate = new Date();
	private String image;

	@ManyToOne
	@JoinColumn(name = "association_id")
	private Association association;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<File> files;

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	private List<Donation> donations;

	@Transient
	private double amountRaised;

	@Transient
	private double percentageRaised = 0;

	@Transient
	private int nbreDonations;

	@Column
	private boolean disabled;

	public Project() {
		this.files = new ArrayList<>();
		this.donations = new ArrayList<>();
	}

	public void addDonation(Donation donation) {
		donations.add(donation);
	}

	public void addFile(File file) {
		files.add(file);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association association) {
		this.association = association;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}

	public double getAmountRaised() {
		return amountRaised;
	}

	public void setAmountRaised(double amountRaised) {
		this.amountRaised = amountRaised;
	}

	public double getPercentageRaised() {
		return percentageRaised;
	}

	public void setPercentageRaised(double percentageRaised) {
		this.percentageRaised = percentageRaised;
	}

	public int getNbreDonations() {
		return nbreDonations;
	}

	public void setNbreDonations(int nbreDonations) {
		this.nbreDonations = nbreDonations;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", slug=" + slug + ", description=" + description + ", amount="
				+ amount + ", deadLine=" + deadLine + ", postedDate=" + postedDate + ", image=" + image
				+ ", association=" + association + ", files=" + files + ", donations=" + donations + ", amountRaised="
				+ amountRaised + ", percentageRaised=" + percentageRaised + ", nbreDonations=" + nbreDonations + "]";
	}

}
