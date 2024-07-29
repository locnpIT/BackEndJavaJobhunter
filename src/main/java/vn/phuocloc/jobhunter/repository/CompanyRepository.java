package vn.phuocloc.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.phuocloc.jobhunter.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    public Company save(Company c);

}
