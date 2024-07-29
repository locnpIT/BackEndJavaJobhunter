package vn.phuocloc.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.phuocloc.jobhunter.domain.Company;
import vn.phuocloc.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company c){
        return this.companyRepository.save(c);
    }


}
