package com.hchoaf.bankapp.datasource.network

import com.hchoaf.bankapp.datasource.BankDataSource
import com.hchoaf.bankapp.model.Bank
import org.springframework.stereotype.Repository

@Repository
class NetworkDataSource : BankDataSource {
    override fun getBanks(): Collection<Bank> {
        TODO("Not yet implemented")
    }

    override fun getBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}