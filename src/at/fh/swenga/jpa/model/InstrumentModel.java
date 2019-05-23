package at.fh.swenga.jpa.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Instrument")

@NamedQueries({
	
	@NamedQuery(name = "InstrumentModel.findByNamedQuery",
	  query = "select i from InstrumentModel i where i.category like :search or i.name like :search"),
		
})

public class InstrumentModel implements java.io.Serializable {


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 30)
	private String category;
	 
	@Column(nullable = false, length = 30)
	private String name;
	 
	@Column(nullable = false, length = 1000)
	private String description;
	
	@Column(nullable = false, length = 10)
	private double price;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@NotNull(message = "Date cannot be null")
	@Temporal(TemporalType.DATE)
	private Date dateofavailability;
	
	@Column(nullable = false, length = 8)
	private double weight;
	
	@Column(nullable = false, length = 4)
	private int amount;
	
	
	
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private ManufacturerModel manufacturer;

	
	public InstrumentModel() {
	}

	public InstrumentModel(String category, String name, String description, double price, Date dateofavailability, double weight, int amount) {
		super();
		this.category = category;
		this.name = name;
		this.description = description;
		this.price = price;
		this.dateofavailability = dateofavailability;
		this.weight = weight;
		this.amount = amount;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDateofavailability() {
		return dateofavailability;
	}

	public void setDateofavailability(Date dateofavailability) {
		this.dateofavailability = dateofavailability;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public ManufacturerModel getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(ManufacturerModel manufacturer) {
		this.manufacturer = manufacturer;
	}
	
}
