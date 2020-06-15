package com.vfin.theconnect.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vfin.theconnect.domain.Address} entity.
 */
@ApiModel(description = "The Address entity.\n@author SlowV true hipster")
public class AddressDTO implements Serializable {

    private String id;

    /**
     * location
     */
    @NotNull
    @ApiModelProperty(value = "location", required = true)
    private String location;


    private String districtId;

    private String accountInfoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getAccountInfoId() {
        return accountInfoId;
    }

    public void setAccountInfoId(String accountInfoId) {
        this.accountInfoId = accountInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if (addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", district=" + getDistrictId() +
            ", accountInfo=" + getAccountInfoId() +
            "}";
    }
}
