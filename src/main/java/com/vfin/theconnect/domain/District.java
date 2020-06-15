package com.vfin.theconnect.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The District entity.\n@author SlowV true hipster
 */
@Document(collection = "district")
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * name
     */
    @NotNull
    @Field("name")
    private String name;

    @DBRef
    @Field("address")
    private Set<Address> addresses = new HashSet<>();

    @DBRef
    @Field("city")
    @JsonIgnoreProperties("districts")
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public District name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public District addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public District addAddress(Address address) {
        this.addresses.add(address);
        address.setDistrict(this);
        return this;
    }

    public District removeAddress(Address address) {
        this.addresses.remove(address);
        address.setDistrict(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public City getCity() {
        return city;
    }

    public District city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof District)) {
            return false;
        }
        return id != null && id.equals(((District) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "District{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
