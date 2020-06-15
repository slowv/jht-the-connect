package com.vfin.theconnect.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vfin.theconnect.domain.District} entity.
 */
@ApiModel(description = "The District entity.\n@author SlowV true hipster")
public class DistrictDTO implements Serializable {

    private String id;

    /**
     * name
     */
    @NotNull
    @ApiModelProperty(value = "name", required = true)
    private String name;


    private String cityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DistrictDTO districtDTO = (DistrictDTO) o;
        if (districtDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), districtDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DistrictDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", city=" + getCityId() +
            "}";
    }
}
