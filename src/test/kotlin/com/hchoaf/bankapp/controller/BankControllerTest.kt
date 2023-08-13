package com.hchoaf.bankapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hchoaf.bankapp.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
        ){

    private val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank() {
        @Test
        fun `should return the bank with the given accountNumber`() {
            // given
            val accountNumber = "hchoaf"
            // when
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.accountNumber") { value("hchoaf") }
                    jsonPath("$.trust") { value(1.0) }
                    jsonPath("$.transactionFee") { value(10) }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                    }
                }
            //then
        }
        @Test
        fun `should return notFound if accountNumber does not exist`() {
            val accountNumber = "does_not_exist"
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks` () {
            // given
            // when, then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$[0].accountNumber") {
                        value("hchoaf")
                    }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                    }
                }
        }
    }


    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            val newBank = Bank("newacc123", 1.0, 12)

            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }
        }

        @Test
        fun `should return BAD REQUEST if given account number already exists`() {
            val invalidBank = Bank("hchoaf", 2.0, 20)

            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            performPost.andDo { print() }
                .andExpect { status {isBadRequest() } }

        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        fun `should update an existing bank`() {

            val updatedBank = Bank("hchoaf", 12.0, 12)

            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }
            performPatch.andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(updatedBank)) }
                }
        }

        @Test
        fun `should return BAD REQUEST if account does not exist` () {
            val invalidBank = Bank("does_not_exist", 12.0, 100)

            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }

        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DirtiesContext // TODO() There sould be some way not using this.
    inner class DeleteExistingBank {
        @Test
        fun `should delete existing bank with given account number` () {
            val accountNumber = "hchoaf"

            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return notFound if accountNumber does not exist`() {
            val accountNumber = "does_not_exist"
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

}