package com.pucetec.reservations.controllers

import com.pucetec.reservations.constants.Routes
import com.pucetec.reservations.models.requests.SubjectRequest
import com.pucetec.reservations.models.responses.SubjectResponse
import com.pucetec.reservations.services.SubjectService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(Routes.BASE_PATH + Routes.SUBJECT)
class SubjectController(
    private val subjectService: SubjectService
) {

    @PostMapping
    fun createSubject(@RequestBody subject: SubjectRequest): SubjectResponse =
        subjectService.createSubject(subject)

    @PostMapping("/{subjectId}${Routes.ENROLL}/{studentId}")
    fun enrollStudent(
        @PathVariable subjectId: Long,
        @PathVariable studentId: Long
    ): SubjectResponse = subjectService.enrollStudent(subjectId, studentId)

    @GetMapping
    fun listSubjects(): List<SubjectResponse> = subjectService.listSubjects()

    @GetMapping("/{subjectId}")
    fun getSubjectById(@PathVariable subjectId: Long): SubjectResponse =
        subjectService.getSubjectById(subjectId)

    @DeleteMapping("/{subjectId}")
    fun deleteSubject(@PathVariable subjectId: Long): ResponseEntity<Void> {
        subjectService.deleteSubjectById(subjectId)
        return ResponseEntity.noContent().build()
    }
}