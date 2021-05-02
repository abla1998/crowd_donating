package org.mql.crowddonating.models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Donation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String paypal_id;

	@Column
	private String transaction_id;

	@Column
	private double amount;

	@Column
	private double transaction_fee;

	@Column
	private Date date;

	@ManyToOne
	@JoinColumn(name = "case_id")
	private Case aCase;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "donor_id")
	private Donor donor;

	public Donation() {
		date = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Case getCase() {
		return aCase;
	}

	public void setCase(Case aCase) {
		this.aCase = aCase;
	}

	public String getPaypalId() {
		return paypal_id;
	}

	public Donation setPaypalId(String paypal_id) {
		this.paypal_id = paypal_id;
		return this;
	}

	public String getTransactionId() {
		return transaction_id;
	}

	public Donation setTransactionId(String transaction_id) {
		this.transaction_id = transaction_id;
		return this;
	}

	public double getTransactionFee() {
		return transaction_fee;
	}

	public Donation setTransactionFee(double transaction_fee) {
		this.transaction_fee = transaction_fee;
		return this;
	}

	public Donor getDonor() {
		return donor;
	}

	public Donation setDonor(Donor donor) {
		this.donor = donor;
		return this;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Donation{" + "id=" + id + ", paypal_id='" + paypal_id + '\'' + ", transaction_id='" + transaction_id
				+ '\'' + ", amount=" + amount + ", transaction_fee=" + transaction_fee + ", date=" + date + ", aCase="
				+ aCase.getName() + ", donor=" + donor.getName() + '}';
	}
}