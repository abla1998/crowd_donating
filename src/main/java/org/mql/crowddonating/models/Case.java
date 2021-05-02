package org.mql.crowddonating.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "_case")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column(unique = true)
    private String slug;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    private double amount;

    @Column
//    @DateTimeFormat(pattern = "MMM dd, yyyy")
    private Date deadLine;

    @Column
    private Date postedDate = new Date();

    @Column
    private String image;

    @Column
    private boolean disabled;

    @ManyToOne
    @JoinColumn(name = "association_id")
    private Association association;

    @OneToMany(mappedBy = "aCase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<File> files;

    @OneToMany(mappedBy = "aCase", fetch = FetchType.LAZY)
    private List<Donation> donations;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "case_type",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "case_id")
    )
    private List<Type> types;
    
    @Transient
    private double amountRaised;
    
    @Transient
    private double percentageRaised = 0;
    
    @Transient
    private int nbreDonations;
    
    private boolean urgent;

    public Case() {
        this.association = new Association(1);
        this.types = new ArrayList<>();
        this.files = new ArrayList<>();
        this.donations = new ArrayList<>();
    }

    public void addDonation(Donation donation) {
        donations.add(donation);
    }

    public void addFile(File file) {
        files.add(file);
    }

    public void addType(Type type) {
        types.add(type);
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

    public boolean isUrgent() {
		return urgent;
	}

	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}

	public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
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

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
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

    public Case setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    @Override
    public String toString() {
        return "Case{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", deadLine=" + deadLine +
                ", postedDate=" + postedDate +
                ", image='" + image + '\'' +
                ", association=" + association +
                ", files=" + files +
                ", donations=" + donations +
                ", types=" + types +
                '}';
    }
}