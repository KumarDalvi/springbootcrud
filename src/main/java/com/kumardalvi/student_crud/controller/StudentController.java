package com.kumardalvi.student_crud.controller;

import com.kumardalvi.student_crud.model.Student;
import com.kumardalvi.student_crud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    //post

    @PostMapping("/save")
//    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> saveStudent(@RequestBody Student student) {
    studentRepository.save(student);
    return ResponseEntity.status(HttpStatus.OK).body("Student saved Successfully");
    }
    //get all
    @GetMapping("/getStudent")
//    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> getStudent() {
        Optional <List<Student>> studentList= Optional.of(studentRepository.findAll()) ;
        if (studentList.isPresent() && studentList.get().size()>0) {
            return ResponseEntity.status(HttpStatus.OK).body(studentList.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");

        }
    }

    // get by ID
    @GetMapping("/getStudent/{id}")
//    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> getStudent(@PathVariable int id) {
        Optional<Student> student= studentRepository.findById(id);
        return student.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found"));
    }


    //update by ID
    @PutMapping("/update/{id}")
//    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable int id) {
        Optional<Student> stud = studentRepository.findById(id);
        if (stud.isPresent()) {
            student.setId(stud.get().getId());
            studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.OK).body("Student updated Successfully for id"+ id);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found for id"+ id);
        }
    }

    //delete All
@DeleteMapping("/deleteAll")
//@CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> deleteAllStudent() {
        studentRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All Student deleted successfully");
}

    // delete By ID
    @DeleteMapping("/delete/{id}")
//    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        Optional<Student> stud = studentRepository.findById(id);
        if (stud.isPresent()) {
            studentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Student deleted Successfully"+id);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not deleted Successfully"+id);
        }

    }


}
