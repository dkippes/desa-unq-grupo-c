package ar.edu.unq.desapp.grupoc.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.CryptoStockDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseVolumeDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long>  {

    @Query(
        value = "SELECT SUM((i.nominal_price * i.nominal_quantity)) as totalOperated, SUM(i.local_price * i.nominal_quantity) as localTotalOperated" +
                " FROM (transactions AS t " +
                " JOIN operation_intents as i ON t.intention_id = i.id)" +
                "WHERE t.status = ?1 AND t.initiated_at BETWEEN ?2 AND ?3",
        nativeQuery = true
    )
    fun findAllByStatusAndInitiatedAtBetween(status: TransactionStatus, from: LocalDateTime, to: LocalDateTime): List<Array<Any>>

    @Query(
        value = "SELECT i.symbol as symbol, i.nominal_price as price, i.nominal_quantity as quantity, i.local_price as localPrice" +
                " FROM (transactions AS t " +
                " JOIN operation_intents as i ON t.intention_id = i.id)" +
                "WHERE t.status = ?1 AND t.initiated_at BETWEEN ?2 AND ?3",
        nativeQuery = true
    )
    fun findCryptosByStatusAndInitiatedAtBetween(status: TransactionStatus, from: LocalDateTime, to: LocalDateTime): List<Array<Any>>

}