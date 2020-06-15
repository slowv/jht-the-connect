package com.vfin.theconnect.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.vfin.theconnect.domain.enumeration.Gender;

/**
 * A DTO for the {@link com.vfin.theconnect.domain.AccountInfo} entity.
 */
@ApiModel(description = "The UserInfo entity.\n@author SlowV true hipster")
public class AccountInfoDTO implements Serializable {

    private String id;

    @NotNull
    private Instant dob;

    private Gender gender;

    private Integer age;

    private String introduce;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^0[0-9]{8}$")
    private String phone;

    private String firstName;

    private String lastName;


    private String imageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountInfoDTO accountInfoDTO = (AccountInfoDTO) o;
        if (accountInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountInfoDTO{" +
            "id=" + getId() +
            ", dob='" + getDob() + "'" +
            ", gender='" + getGender() + "'" +
            ", age=" + getAge() +
            ", introduce='" + getIntroduce() + "'" +
            ", phone='" + getPhone() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", image=" + getImageId() +
            "}";
    }
}
