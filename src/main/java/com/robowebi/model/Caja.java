package com.robowebi.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import com.robowebi.model.Cosa;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Caja implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String label;

	@OneToMany
	private Set<Cosa> cosas = new HashSet<>();

	@Column
	private String location;

	@Column
	private Float weight;

	@Column
	private Double siWeight;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Caja)) {
			return false;
		}
		Caja other = (Caja) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<Cosa> getCosas() {
		return this.cosas;
	}

	public void setCosas(final Set<Cosa> cosas) {
		this.cosas = cosas;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Double getSiWeight() {
		return siWeight;
	}

	public void setSiWeight(Double siWeight) {
		this.siWeight = siWeight;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (label != null && !label.trim().isEmpty())
			result += "label: " + label;
		if (location != null && !location.trim().isEmpty())
			result += ", location: " + location;
		if (weight != null)
			result += ", weight: " + weight;
		if (siWeight != null)
			result += ", siWeight: " + siWeight;
		return result;
	}
}