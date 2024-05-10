package ar.edu.unq.desapp.grupoc.backenddesappapi.service

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseTransactionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseVolumeDTO
import java.time.LocalDate

interface TransactionService {
    fun getVolumeBetweenDates(from: LocalDate, to: LocalDate): ResponseVolumeDTO
    fun processTransaction(accountId: Long, transactionId: Long, action: TransactionStatus): ResponseTransactionDTO
    fun generateTransaction(accountId: Long, operationId: Long): ResponseTransactionDTO
}