package com.hchoaf.bankapp.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {
    private val mockBankDataSource : MockBankDataSource = MockBankDataSource()
    @Test
    fun `should provide a collection of banks`() {
        // given

        // when
        val banks = mockBankDataSource.getBanks()

        // then
        assertThat(banks).isNotEmpty
    }

    @Test
    fun `should provide some bank mock data`() {
        val banks = mockBankDataSource.getBanks()
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).allMatch { it.transactionFee != 0 }
    }
}