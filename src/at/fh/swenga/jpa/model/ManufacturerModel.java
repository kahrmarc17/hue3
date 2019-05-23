package at.fh.swenga.jpa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

@Entity
public class ManufacturerModel {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
    @OneToMany(mappedBy="manufacturer",fetch=FetchType.LAZY)
    @OrderBy("name, category")
    private Set<InstrumentModel> instruments;

	@Version
	long version;
    
    
    public ManufacturerModel() {
		// TODO Auto-generated constructor stub
    }
    
	public ManufacturerModel(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<InstrumentModel> getInstruments() {
		return instruments;
	}

	public void setInstruments(Set<InstrumentModel> instruments) {
		this.instruments = instruments;
	}
	
	public void addInstrument(InstrumentModel instrument) {
		if (instruments==null) {
			instruments= new HashSet<InstrumentModel>();
		}
		instruments.add(instrument);
	}
	
    
}
