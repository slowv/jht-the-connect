package com.vfin.theconnect.repository;
import com.vfin.theconnect.domain.AccountTC;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the AccountTC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountTCRepository extends MongoRepository<AccountTC, String> {

}
