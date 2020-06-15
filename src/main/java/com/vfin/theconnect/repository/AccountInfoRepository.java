package com.vfin.theconnect.repository;
import com.vfin.theconnect.domain.AccountInfo;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the AccountInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountInfoRepository extends MongoRepository<AccountInfo, String> {

}
