package com.vfin.theconnect.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.vfin.theconnect.domain.enumeration.Gender;

/**
 * The UserInfo entity.\n@author SlowV true hipster
 */
@Document(collection = "user_info")
public class UserInfo implements Serializable {

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

    public UserInfo dob(Instant dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public UserInfo gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public UserInfo age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIntroduce() {
        return introduce;
    }

    public UserInfo introduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPhone() {
        return phone;
    }

    public UserInfo phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return id != null && id.equals(((UserInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", dob='" + getDob() + "'" +
            ", gender='" + getGender() + "'" +
            ", age=" + getAge() +
            ", introduce='" + getIntroduce() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
