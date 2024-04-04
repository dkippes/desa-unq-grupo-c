package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import jakarta.persistence.*

@Entity
class Transaction (
    @OneToOne var intention: OperationIntent? = null,
    @OneToOne var seller: User? = null,
    @OneToOne var buyer: User? = null,
    var status: TransactionStatus = TransactionStatus.WAITING_ACTION
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}