package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
class Transaction (
    @OneToOne var intention: OperationIntent? = null,
    @OneToOne var seller: User? = null,
    @OneToOne var buyer: User? = null,
    var status: TransactionStatus = TransactionStatus.WAITING_ACTION,
    var initiatedAt: LocalDateTime = LocalDateTime.now()
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Suppress("squid:S2699")
    var id: Long? = null

    fun getPointsForFinish() : Int {
        val duration = ChronoUnit.MINUTES.between(initiatedAt, LocalDateTime.now())
        if(duration <= 30) return 10
        return 5
    }

    fun getPointsPenalizationForCancel() : Int {
        return 20
    }
}