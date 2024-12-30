package masterplanb.masterplanbbe.common.exception

import masterplanb.masterplanbbe.common.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(GlobalExceptions.NotFoundException::class)
    fun handleNotfoundException(
        e: GlobalExceptions.NotFoundException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.valueOf(e.errorCode.status)).body(
            ErrorResponse.of(e.errorCode)
        )
    }

    @ExceptionHandler(GlobalExceptions.InternalErrorException::class)
    fun handleInternalErrorException(
        e: GlobalExceptions.InternalErrorException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.valueOf(e.errorCode.status)).body(
            ErrorResponse.of(e.errorCode)
        )
    }

    @ExceptionHandler(GlobalExceptions.IllegalStateException::class)
    fun handleIllegalStateException(
        e: GlobalExceptions.IllegalStateException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.valueOf(e.errorCode.status)).body(
            ErrorResponse.of(e.errorCode)
        )
    }

    @ExceptionHandler(GlobalExceptions.BadRequestException::class)
    fun handleBadRequestException(
        e: GlobalExceptions.BadRequestException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.valueOf(e.errorCode.status)).body(
            ErrorResponse.of(e.errorCode)
        )
    }
}
