package com.pucetec.reservations.services

import com.pucetec.reservations.exceptions.ProfessorNotFoundException
import com.pucetec.reservations.exceptions.StudentAlreadyEnrolledException
import com.pucetec.reservations.exceptions.StudentNotFoundException
import com.pucetec.reservations.exceptions.SubjectNotFoundException
import com.pucetec.reservations.exceptions.SubjectNotFoundExceptionDelete
import com.pucetec.reservations.mappers.SubjectMapper
import com.pucetec.reservations.models.entities.Subject
import com.pucetec.reservations.models.requests.SubjectRequest
import com.pucetec.reservations.models.responses.SubjectResponse
import com.pucetec.reservations.repositories.ProfessorRepository
import com.pucetec.reservations.repositories.StudentRepository
import com.pucetec.reservations.repositories.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val professorRepository: ProfessorRepository,
    private val studentRepository: StudentRepository,
    private val subjectMapper: SubjectMapper,
) {
    fun createSubject(request: SubjectRequest): SubjectResponse {
        // Step 1: Find the professor by ID
        val professor = professorRepository.findById(request.professorId)
            .orElseThrow { ProfessorNotFoundException("No se encontró el profesor con ID: ${request.professorId}") }

        // Step 2: Create a new Subject entity
        val subject = Subject(
            name = request.name,
            semester = request.semester,
            professor = professor
        )

        // Step 3: Save the subject to the repository
        val savedSubject = subjectRepository.save(subject)

        // Step 4: Return the created subject response
        return subjectMapper.toResponse(savedSubject)
    }

    fun enrollStudent(subjectId: Long, studentId: Long): SubjectResponse {
        // Step 1: Find the subject by ID
        val subject = subjectRepository.findById(subjectId)
            .orElseThrow { SubjectNotFoundException("No se encontró la materia con ID: $subjectId") }

        // Step 2: Find the student by ID
        val student = studentRepository.findById(studentId)
            .orElseThrow { StudentNotFoundException("No se encontró el estudiante con ID: $studentId") }

        // Step 3: Check if the student is already enrolled in the subject
        if (subject.students.contains(student)) {
            throw StudentAlreadyEnrolledException("El estudiante con ID $studentId ya está inscrito en la materia $subjectId")
        }

        // Step 4: If not, enroll the student in the subject
        subject.students.add(student)

        // Step 5: Save the updated subject to the repository
        val updatedSubject = subjectRepository.save(subject)

        // Step 6: Return the updated subject response
        return subjectMapper.toResponse(updatedSubject)
    }

    fun getSubjectById(subjectId: Long): SubjectResponse {
        // Step 1: Find the subject by ID
        val subject = subjectRepository.findById(subjectId)
            .orElseThrow { SubjectNotFoundException("No se encontró la materia con ID: $subjectId") }

        // Step 2: Return the subject response
        return subjectMapper.toResponse(subject)
    }

    fun deleteSubjectById(subjectId: Long) {
        // Step 1: Find the subject by ID
        val subject = subjectRepository.findById(subjectId)
            .orElseThrow { SubjectNotFoundExceptionDelete("No se encontró la materia con ID: $subjectId") }

        // Step 2: Delete the subject
        subjectRepository.delete(subject)
    }

    fun listSubjects(): List<SubjectResponse> =
        subjectMapper.toResponseList(subjectRepository.findAll())
}