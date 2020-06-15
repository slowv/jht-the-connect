package com.vfin.theconnect.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.vfin.theconnect.domain.enumeration.Gender;

/**
 * The UserInfo entity.\n@author SlowV true hipster
 */
@Document(collection = "account_info")
public class AccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("dob")
    private Instant dob;

    @Field("gender")
    private Gender gender;

    @Field("age")
    private Integer age;

    @Field("introduce")
    private String introduce;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^0[0-9]{8}$")
    @Field("phone")
    private String phone;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @DBRef
    @Field("image")
    private Image image;

    @DBRef
    @Field("address")
    private Set<Address> addresses = new HashSet<>();

    @DBRef
    @Field("accountTC")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private AccountTC accountTC;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDob() {
        return dob;
    }

    public AccountInfo dob(Instant dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public AccountInfo gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public AccountInfo age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIntroduce() {
        return introduce;
    }

    public AccountInfo introduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPhone() {
        return phone;
    }

    public AccountInfo phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public AccountInfo firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AccountInfo lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Image getImage() {
        return image;
    }

    public AccountInfo image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public AccountInfo addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public AccountInfo addAddress(Address address) {
        this.addresses.add(address);
        address.setAccountInfo(this);
        return this;
    }

    public AccountInfo removeAddress(Address address) {
        this.addresses.remove(address);
        address.setAccountInfo(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public AccountTC getAccountTC() {
        return accountTC;
    }

    public AccountInfo accountTC(AccountTC accountTC) {
        this.accountTC = accountTC;
        return this;
    }

    public void setAccountTC(AccountTC accountTC) {
        this.accountTC = accountTC;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountInfo)) {
            return false;
        }
        return id != null && id.equals(((AccountInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
            "id=" + getId() +
            ", dob='" + getDob() + "'" +
            ", gender='" + getGender() + "'" +
            ", age=" + getAge() +
            ", introduce='" + getIntroduce() + "'" +
            ", phone='" + getPhone() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
