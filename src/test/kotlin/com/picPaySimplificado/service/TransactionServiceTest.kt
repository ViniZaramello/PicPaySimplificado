package com.picPaySimplificado.service

import com.picPaySimplificado.client.ConfirmTransferApproval
import com.picPaySimplificado.client.PostConfirmationTransactionByEmail
import com.picPaySimplificado.exception.NotFoundException
import com.picPaySimplificado.helper.buildTransaction
import com.picPaySimplificado.repository.CustomerRepository
import com.picPaySimplificado.repository.TransactionRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import com.picPaySimplificado.helper.buildCustomer

import java.util.*


@ExtendWith(MockKExtension::class)
@SpringBootTest
class TransactionServiceTest {

    @MockK
    private lateinit var customerService: CustomerService

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var confirmTransferApproval: ConfirmTransferApproval

    @MockK
    private lateinit var postConfirmationTransactionByEmail: PostConfirmationTransactionByEmail

    @MockK
    private lateinit var repository: TransactionRepository

    @InjectMockKs
    lateinit var transactionService: TransactionService

    @Test
    fun `should return all transactions`() {
        val fakeTransaction = listOf(buildTransaction())

        every { repository.findAll() } returns fakeTransaction

        val transaction = transactionService.getAll()

        assertEquals(fakeTransaction, transaction)
        verify(exactly = 1) { repository.findAll() }
    }

    @Test
    fun `should return the specific transaction`() {
        val id = Random().nextInt()
        val fakeTransaction = buildTransaction(id = id)


        every { repository.findById(id) } returns Optional.of(fakeTransaction)

        val transaction = transactionService.getTransaction(id)

        assertEquals(fakeTransaction, transaction)
        verify(exactly = 1) { repository.findById(id) }
    }

    @Test
    fun `should return the specific exception transaction`() {
        val id = Random().nextInt()

        every { repository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> { transactionService.getTransaction(id) }

        assertEquals("Transação [${id}] não existe", error.message)
        assertEquals("TO-001", error.errorCode)

        verify(exactly = 1) { repository.findById(id) }
    }
    //Transference

    @Test
    fun `should make a transference`(){
        val senderId = Random().nextInt()
        val sender = buildCustomer(id = senderId)
        val getSenderData = sender

        val recipientId = Random().nextInt()
        val recipient = buildCustomer(id = recipientId)
        val getRecipientData = recipient

        val transaction = buildTransaction(envia = senderId, recebe = recipientId)


        //senderValidateMock
        every { customerService.senderValidate(transaction) } returns sender

        //recipientValidate
        every { customerService.recipientValidate(transaction, sender) } returns recipient

        //checkBalance
        every { customerService.checkBalance(transaction.valor, sender.saldo) } returns true

        every { repository.save(any()) } returns transaction

        every { postConfirmationTransactionByEmail.sendConfirmationForEmailApi(transaction) } returns true

        every { customerRepository.save(getSenderData) } returns getSenderData
        every { customerRepository.save(getRecipientData) } returns getRecipientData

        every { confirmTransferApproval.getValidateTransferApproval() } returns true


        transactionService.transference(transaction)

        assertEquals(450.0F, getSenderData.saldo)
        assertEquals(550.0F, getRecipientData.saldo)
        verify(exactly = 1) { customerService.senderValidate(transaction) }
        verify(exactly = 1) { customerService.recipientValidate(transaction, sender) }
        verify(exactly = 1) { repository.save(any()) }
        verify(exactly = 1) { postConfirmationTransactionByEmail.sendConfirmationForEmailApi(transaction) }
        verify(exactly = 1) { customerRepository.save(getSenderData) }
        verify(exactly = 1) { customerRepository.save(getRecipientData) }

    }



}