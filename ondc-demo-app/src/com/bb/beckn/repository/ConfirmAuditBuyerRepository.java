package com.bb.beckn.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bb.beckn.search.model.ConfirmAuditBuyerObj;


public interface ConfirmAuditBuyerRepository extends JpaRepository<ConfirmAuditBuyerObj, Long> {

	Optional<ConfirmAuditBuyerObj> findBytransactionid(String Transactionid);
	
	@Modifying
    @Transactional
    @Query(value = "insert into ConfirmAuditSellerObj (Transactionid, Orderid, sellerorderstate,logisticorderstate, Paymenttype, Paymentdoneby, Amountatconfirmation,Logisticprovidername,"
    		+ "Logisticproviderid, Logisticdeliverycharge, Logisticdeliverytype, Refundtype, Refundamount, Refundby, Refundbearyby, Refundreason, Canceltype,"
    		+ "Cancelamount, Camncelby, cancelamountbearbybuyer, cancelamountbearbyseller,cancelamountbearbylogistic, Cancelreason, Creationdate, Updationdate) VALUES (?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?,?)", nativeQuery = true)
	
	ConfirmAuditBuyerObj save(String Transactionid, String Orderid,String sellerorderstate,String logisticorderstate, String Paymenttype,String Paymentdoneby,String Amountatconfirmation,String Logisticprovidername,
			String Logisticproviderid, String Logisticdeliverycharge,String Logisticdeliverytype,String Refundtype,String Refundamount,String Refundby,String Refundbearyby,
			String Refundreason,String Canceltype,String Cancelamount,String Camncelby, String cancelamountbearbybuyer,String cancelamountbearbyseller,String cancelamountbearbylogistic,String Cancelreason, String Creationdate,String Updationdate);
    
	
	@Query("SELECT k FROM ConfirmAuditBuyerObj k where k.orderid= :orderid  and k.sellerorderstate= :sellerorderstate ")
	ConfirmAuditBuyerObj findByorderidandorderstate( String orderid, String sellerorderstate);
	
	@Query("SELECT k FROM ConfirmAuditBuyerObj k where k.orderid= :orderid  and k.transactionid= :transactionid ")
	List<ConfirmAuditBuyerObj> findByorderidandtransactionid( String orderid, String transactionid);
	
	@Query("SELECT k FROM ConfirmAuditBuyerObj k where k.orderid= :orderid ")
	List<ConfirmAuditBuyerObj> findByorderid( String orderid);
	
	
}
