package com.vfin.theconnect.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.vfin.theconnect.domain.enumeration.Gender;

/**
 * A DTO for the {@link com.vfin.theconnect.domain.UserInfo} entity.
 */
@ApiModel(description = "The UserInfo entity.\n@author SlowV true hipster")
public class UserInfoDTO implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserInfoDTO userInfoDTO = (UserInfoDTO) o;
        if (userInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
            "id=" + getId() +
            ", dob='" + getDob() + "'" +
            ", gender='" + getGender() + "'" +
            ", age=" + getAge() +
            ", introduce='" + getIntroduce() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
