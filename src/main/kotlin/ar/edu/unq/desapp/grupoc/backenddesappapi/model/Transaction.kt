package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "transactions")
class Transaction (
    @OneToOne(cascade = [CascadeType.ALL])
    var intention: OperationIntent? = null,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var seller: Account? = null,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var buyer: Account? = null,
    var status: TransactionStatus = TransactionStatus.WAITING_ACTION,
    var initiatedAt: LocalDateTime = LocalDateTime.now()
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun getPointsForFinish() : Int {
        val duration = ChronoUnit.MINUTES.between(initiatedAt, LocalDateTime.now())
        if(duration <= 30) return 10
        return 5
    }

    fun getPointsPenalizationForCancel() : Int {
        return 20
    }

    fun getAddress(): String {
        if(intention!!.operation == OPERATION.BUY) {
            return buyer!!.walletAddress
        }
        return seller!!.cvu
    }

    fun cancelBySystem() {
        this.status = TransactionStatus.CANCELED
        this.intention!!.status = OperationStatus.CLOSED
    }
}