package com.bb.beckn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bb.beckn.search.model.ApiBuyerObj;



public interface ApiAuditbuyerRepository extends JpaRepository<ApiBuyerObj, Long> {

	Optional<ApiBuyerObj> findByAction(String storename);
 
	Boolean existsByDomain(String storename);
	
	@Query("SELECT k FROM ApiBuyerObj k where k.transaction_id = :transactionid  and k.message_id =:messageid and k.action =:action")
	List<ApiBuyerObj>  findByTransactionidandMessageid(@Param("transactionid") String transactionid , @Param("messageid") String messageid, @Param("action") String action);
    
	@Query("SELECT k FROM ApiBuyerObj k where k.transaction_id = :transactionid  and k.action =:action")
	ApiBuyerObj  findByTransactionidandaction(@Param("transactionid") String transactionid ,@Param("action") String action);
	
}
