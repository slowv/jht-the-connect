package com.vfin.theconnect.repository;
import com.vfin.theconnect.domain.UserInfo;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the UserInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

}
