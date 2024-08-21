package vn.phuocloc.jobhunter.response;

import java.time.Instant;

import vn.phuocloc.jobhunter.domain.Company;
import vn.phuocloc.jobhunter.util.constant.GenderEnum;

public class ResUserDTO {

    private long id;
    private String email;
    private String name;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant updatedAt;
    private Instant createdAt;
    private CompanyUser company;

    public static class CompanyUser {
        private long id;
        private String name;

        public CompanyUser() {

        }

        public CompanyUser(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public ResUserDTO(long id, String email, String name, GenderEnum gender, String address, int age, Instant updatedAt,
            Instant createdAt, CompanyUser company) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.age = age;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.company = company;
    }

    public ResUserDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CompanyUser getCompany() {
        return company;
    }

    public void setCompany(CompanyUser company) {
        this.company = company;
    }

}
