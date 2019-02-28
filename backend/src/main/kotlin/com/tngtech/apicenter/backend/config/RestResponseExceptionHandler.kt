package com.tngtech.apicenter.backend.config

import com.tngtech.apicenter.backend.domain.exceptions.SpecificationNotFoundException
import com.tngtech.apicenter.backend.domain.exceptions.SpecificationParseException
import com.tngtech.apicenter.backend.domain.exceptions.VersionAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant
import java.time.format.DateTimeFormatter
import mu.KotlinLogging

private val logger = KotlinLogging.logger {  }

data class ErrorMessage(
        val httpReasonPhrase: String,
        val userMessage: String,
        val timestamp: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
)

fun responseBuilder(userMessage: String, status: HttpStatus): ResponseEntity<ErrorMessage> {
    return ResponseEntity(
        ErrorMessage(status.reasonPhrase, userMessage),
        null,
        status
    )
}

@ControllerAdvice
class RestResponseExceptionHandler {

    @ExceptionHandler(SpecificationNotFoundException::class)
    fun handleNotFound(exc: SpecificationNotFoundException): ResponseEntity<ErrorMessage> {
        val detailedMessage = "Specification ${exc.specificationId} ${exc.version} not found"
        logger.info(detailedMessage, exc)
        return responseBuilder("Specification not found", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(SpecificationParseException::class)
    fun handleBadRequest(exc: SpecificationParseException) =
            responseBuilder(exc.userMessage, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(VersionAlreadyExistsException::class)
    fun handleVersionAlreadyExists(exc: VersionAlreadyExistsException) =
            responseBuilder("A specification with the same version already exists for ${exc.specificationTitle}", HttpStatus.CONFLICT)
}

