package vn.phuocloc.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.phuocloc.jobhunter.domain.Company;
import vn.phuocloc.jobhunter.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company c) {
        return this.companyRepository.save(c);
    }

    public List<Company> handleGetCompany() {
        return this.companyRepository.findAll();
    }

    public Company handleUpdateCompany(Company c) {
        Optional<Company> optionalCompany = this.companyRepository.findById(c.getId());
        if (optionalCompany.isPresent()) {
            Company currentCompany = optionalCompany.get();
            currentCompany.setAddress(c.getAddress());
            currentCompany.setLogo(c.getLogo());
            currentCompany.setName(c.getName());
            currentCompany.setDescription(c.getDescription());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }

    public void handleDeleteCompany(Long id) {
        this.companyRepository.deleteById(id);
    }

}
