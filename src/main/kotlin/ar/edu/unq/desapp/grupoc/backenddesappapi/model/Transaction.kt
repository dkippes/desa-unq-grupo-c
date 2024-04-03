package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import jakarta.persistence.*

@Entity
class Transaction (
    @OneToOne val intention: OperationIntent? = null,
    @OneToOne val user: User? = null,
    val status: TransactionStatus = TransactionStatus.WAITING_ACTION
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}