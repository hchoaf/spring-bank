package com.hchoaf.bankapp.service

import com.hchoaf.bankapp.datasource.BankDataSource
import com.hchoaf.bankapp.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService (@Qualifier("mock") private val bankDataSource: BankDataSource){
    fun getBanks() : Collection<Bank> {
        return bankDataSource.getBanks()
    }

    fun getBank(accountNumber : String) : Bank {
        return bankDataSource.getBank(accountNumber)
    }

    fun addBank(bank : Bank) : Bank {
        return bankDataSource.createBank(bank)
    }

    fun updateBank(bank: Bank) : Bank {
        return bankDataSource.updateBank(bank)
    }

    fun deleteBank(accountNumber: String) {
        return bankDataSource.deleteBank(accountNumber)
    }
}