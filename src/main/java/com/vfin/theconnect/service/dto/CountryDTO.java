package com.vfin.theconnect.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vfin.theconnect.domain.Country} entity.
 */
@ApiModel(description = "The Country entity.\n@author SlowV true hipster")
public class CountryDTO implements Serializable {

    private String id;

    /**
     * name
     */
    @NotNull
    @ApiModelProperty(value = "name", required = true)
    private String name;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;
        if (countryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
