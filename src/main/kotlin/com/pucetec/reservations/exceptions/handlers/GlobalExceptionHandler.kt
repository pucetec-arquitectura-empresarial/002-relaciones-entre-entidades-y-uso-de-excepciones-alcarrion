package com.pucetec.reservations.exceptions.handlers

import com.pucetec.reservations.exceptions.ProfessorNotFoundException
import com.pucetec.reservations.exceptions.StudentAlreadyEnrolledException
import com.pucetec.reservations.exceptions.SubjectNotFoundException
import com.pucetec.reservations.exceptions.SubjectNotFoundExceptionDelete
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(SubjectNotFoundException::class)
    fun handleSubjectNotFound(ex: SubjectNotFoundException): ResponseEntity<Map<String, String>> =
        ResponseEntity(mapOf("error" to ex.message.orEmpty()), HttpStatus.NOT_FOUND)

    @ExceptionHandler(ProfessorNotFoundException::class)
    fun handleProfessorNotFound(ex: ProfessorNotFoundException): ResponseEntity<Map<String, String>> =
        ResponseEntity(mapOf("error" to ex.message.orEmpty()), HttpStatus.NOT_FOUND)

    @ExceptionHandler(StudentAlreadyEnrolledException::class)
    fun handleStudentAlreadyEnrolled(ex: StudentAlreadyEnrolledException): ResponseEntity<Map<String, String>> =
        ResponseEntity(mapOf("error" to ex.message.orEmpty()), HttpStatus.CONFLICT)

    @ExceptionHandler(SubjectNotFoundExceptionDelete::class)
    fun handleSubjectNotFoundForDelete(ex: SubjectNotFoundExceptionDelete): ResponseEntity<Map<String, String>> =
        ResponseEntity(mapOf("error" to ex.message.orEmpty()), HttpStatus.NOT_FOUND)
}