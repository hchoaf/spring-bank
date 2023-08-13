package com.hchoaf.bankapp.datasource

import com.hchoaf.bankapp.model.Bank

interface BankDataSource {
    fun getBanks() : Collection<Bank>
    fun getBank(accountNumber : String) : Bank
    fun createBank(bank : Bank) : Bank
    fun updateBank(bank: Bank) : Bank
    fun deleteBank(accountNumber: String)
}