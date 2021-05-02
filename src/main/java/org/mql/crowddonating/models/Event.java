package org.mql.crowddonating.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    private String image;

    @Column
    private Date plannedDate;

    @ManyToOne
    @JoinColumn(name = "association_id")
    private Association association;

    private String address;
    
    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public String getAddress() {
		return address;
	}
    
    public void setAddress(String address) {
		this.address = address;
	}
    
    @Override
    public String toString() {
        return "Event [id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
                + ", plannedDate=" + plannedDate + ", association=" + association + "]";
    }
}