package com.vfin.theconnect.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.vfin.theconnect.domain.enumeration.FriendType;

/**
 * A DTO for the {@link com.vfin.theconnect.domain.Friend} entity.
 */
@ApiModel(description = "The Friend entity.\n@author A true hipster")
public class FriendDTO implements Serializable {

    private String id;

    private Instant dateIsFriend;

    @NotNull
    private FriendType type;


    private String senderId;

    private String receiverId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDateIsFriend() {
        return dateIsFriend;
    }

    public void setDateIsFriend(Instant dateIsFriend) {
        this.dateIsFriend = dateIsFriend;
    }

    public FriendType getType() {
        return type;
    }

    public void setType(FriendType type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String accountTCId) {
        this.senderId = accountTCId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String accountTCId) {
        this.receiverId = accountTCId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FriendDTO friendDTO = (FriendDTO) o;
        if (friendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), friendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FriendDTO{" +
            "id=" + getId() +
            ", dateIsFriend='" + getDateIsFriend() + "'" +
            ", type='" + getType() + "'" +
            ", sender=" + getSenderId() +
            ", receiver=" + getReceiverId() +
            "}";
    }
}
