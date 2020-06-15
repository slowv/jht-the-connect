package com.vfin.theconnect.repository;
import com.vfin.theconnect.domain.Friend;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Friend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendRepository extends MongoRepository<Friend, String> {

}
