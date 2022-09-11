package com.myrental.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrental.web.dto.CreateStudentRequestBody;
import com.myrental.web.dto.UpdateStudentRequestBody;
import com.myrental.web.enumeration.AuditTrailActionEnum;
import com.myrental.web.error.BadRequestException;
import com.myrental.web.error.NotFoundException;
import com.myrental.web.kafkamessage.AuditTrailMessage;
import com.myrental.web.model.QStudent;
import com.myrental.web.model.Student;
import com.myrental.web.repository.StudentRepository;
import com.myrental.web.util.JpaEntityUtil;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private KafkaTemplate<Integer, AuditTrailMessage> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    public Student create(CreateStudentRequestBody body) throws Exception {
        Optional<Student> optionalStudent = studentRepository.findFirstByEmail(body.getEmail());

        if (optionalStudent.isPresent()) {
            throw new BadRequestException("email already used!");
        }

        Student newStudent = studentRepository.save(new Student(
                body.getName(),
                body.getEmail(),
                LocalDateTime.now(),
                LocalDateTime.now()
        ));

        Optional<Table> optTableAnnotation = JpaEntityUtil.getJpaTableAnnotation(newStudent.getClass());
        String tableName = optTableAnnotation.isPresent() ? optTableAnnotation.get().name() : "students";

        kafkaTemplate.send(topic, new AuditTrailMessage(
                tableName,
                String.valueOf(newStudent.getId()),
                AuditTrailActionEnum.CREATE,
                "",
                objectMapper.writeValueAsString(newStudent),
                LocalDateTime.now()
        ));

        return newStudent;
    }

    @Transactional
    public Student update(UpdateStudentRequestBody body, Long id) throws Exception {
        Student student = read(id);
        Student studentOldValue = (Student) student.clone();

        student.setName(body.getName());
        student.setEmail(body.getEmail());
        student.setUpdatedAt(LocalDateTime.now());
        student = studentRepository.save(student);

        Optional<Table> optTableAnnotation = JpaEntityUtil.getJpaTableAnnotation(student.getClass());
        String tableName = optTableAnnotation.isPresent() ? optTableAnnotation.get().name() : "students";

        kafkaTemplate.send(topic, new AuditTrailMessage(
                tableName,
                String.valueOf(student.getId()),
                AuditTrailActionEnum.UPDATE,
                objectMapper.writeValueAsString(studentOldValue),
                objectMapper.writeValueAsString(student),
                LocalDateTime.now()
        ));

        return student;
    }

    public Student delete(Long id) throws Exception {
        Student student = read(id);
        studentRepository.delete(student);

        Optional<Table> optTableAnnotation = JpaEntityUtil.getJpaTableAnnotation(student.getClass());
        String tableName = optTableAnnotation.isPresent() ? optTableAnnotation.get().name() : "students";

        kafkaTemplate.send(topic, new AuditTrailMessage(
                tableName,
                String.valueOf(student.getId()),
                AuditTrailActionEnum.DELETE,
                objectMapper.writeValueAsString(student),
                "",
                LocalDateTime.now()
        ));

        return student;
    }

    public Student read(Long id) throws NotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isEmpty()) {
            throw new NotFoundException("student not found!");
        }

        return optionalStudent.get();
    }

    public Student readWithQuerydsl(Long id) throws NotFoundException {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QStudent qStudent = QStudent.student;

        try {
            Student student = jpaQueryFactory.selectFrom(qStudent)
                    .where(qStudent.id.eq(id))
                    .fetchOne();

            if (student == null) {
                throw new NotFoundException("student not found!");
            }

            return student;
        } catch (NonUniqueResultException e) {
            throw new NotFoundException("student not found! " + e.getMessage());
        }
    }
}
