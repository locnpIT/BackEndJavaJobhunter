package vn.phuocloc.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.phuocloc.jobhunter.domain.Company;
import vn.phuocloc.jobhunter.service.CompanyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody Company reqCompany) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(reqCompany));
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getCompany() {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleGetCompany());
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleUpdateCompany(company));
    }

    @DeleteMapping("/companies/{id}")
    public void deleteCompany(@PathVariable long id) {
        this.companyService.handleDeleteCompany(id);
    }

}
