package com.vfin.theconnect.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.vfin.theconnect.domain.enumeration.FriendType;

/**
 * The Friend entity.\n@author A true hipster
 */
@Document(collection = "friend")
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date_is_friend")
    private Instant dateIsFriend;

    @NotNull
    @Field("type")
    private FriendType type;

    @DBRef
    @Field("sender")
    private AccountTC sender;

    @DBRef
    @Field("receiver")
    private AccountTC receiver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDateIsFriend() {
        return dateIsFriend;
    }

    public Friend dateIsFriend(Instant dateIsFriend) {
        this.dateIsFriend = dateIsFriend;
        return this;
    }

    public void setDateIsFriend(Instant dateIsFriend) {
        this.dateIsFriend = dateIsFriend;
    }

    public FriendType getType() {
        return type;
    }

    public Friend type(FriendType type) {
        this.type = type;
        return this;
    }

    public void setType(FriendType type) {
        this.type = type;
    }

    public AccountTC getSender() {
        return sender;
    }

    public Friend sender(AccountTC accountTC) {
        this.sender = accountTC;
        return this;
    }

    public void setSender(AccountTC accountTC) {
        this.sender = accountTC;
    }

    public AccountTC getReceiver() {
        return receiver;
    }

    public Friend receiver(AccountTC accountTC) {
        this.receiver = accountTC;
        return this;
    }

    public void setReceiver(AccountTC accountTC) {
        this.receiver = accountTC;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Friend)) {
            return false;
        }
        return id != null && id.equals(((Friend) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Friend{" +
            "id=" + getId() +
            ", dateIsFriend='" + getDateIsFriend() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
