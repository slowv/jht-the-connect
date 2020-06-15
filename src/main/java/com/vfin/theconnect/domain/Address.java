package com.vfin.theconnect.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * The Address entity.\n@author SlowV true hipster
 */
@Document(collection = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * location
     */
    @NotNull
    @Field("location")
    private String location;

    @DBRef
    @Field("district")
    @JsonIgnoreProperties("addresses")
    private District district;

    @DBRef
    @Field("accountInfo")
    @JsonIgnoreProperties("addresses")
    private AccountInfo accountInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public Address location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public District getDistrict() {
        return district;
    }

    public Address district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public Address accountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        return this;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
