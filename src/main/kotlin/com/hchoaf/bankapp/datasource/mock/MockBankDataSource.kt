package com.hchoaf.bankapp.datasource.mock

import com.hchoaf.bankapp.datasource.BankDataSource
import com.hchoaf.bankapp.model.Bank
import org.springframework.stereotype.Repository

@Repository("mock")
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(Bank("hchoaf", 1.0, 10))
    override fun getBanks() : Collection<Bank> = banks

    override fun getBank(accountNumber : String): Bank {
        return banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("could not find account with account number $accountNumber")
    }

    override fun createBank(bank: Bank): Bank {
        if (banks.any{ it.accountNumber == bank.accountNumber} ) {
            throw IllegalArgumentException("Bank with accountNumber ${bank.accountNumber} already exists.")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull() { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("could not find account with account number ${bank.accountNumber}")
        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("could not find account with account number $accountNumber")
        banks.remove(currentBank)

    }
}