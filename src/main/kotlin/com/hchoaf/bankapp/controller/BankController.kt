package com.hchoaf.bankapp.controller

import com.hchoaf.bankapp.model.Bank
import com.hchoaf.bankapp.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")
class BankController (private val bankService: BankService) {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e : NoSuchElementException) : ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler (IllegalArgumentException::class)
    fun handleBadRequest(e : IllegalArgumentException) : ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }


    @GetMapping
    fun getBanks() : Collection<Bank> = bankService.getBanks()

    @GetMapping("{accountNumber}")
    fun getBank(@PathVariable accountNumber : String) = bankService.getBank(accountNumber)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: Bank) : Bank {
        return bankService.addBank(bank)
    }

    @PatchMapping
    fun updateBank(@RequestBody bank : Bank) : Bank {
        return bankService.updateBank(bank)
    }

    @DeleteMapping("{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable accountNumber: String) {
        return bankService.deleteBank(accountNumber)
    }
}