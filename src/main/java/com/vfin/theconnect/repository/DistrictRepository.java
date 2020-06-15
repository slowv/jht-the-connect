package com.vfin.theconnect.repository;
import com.vfin.theconnect.domain.District;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the District entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictRepository extends MongoRepository<District, String> {

}
