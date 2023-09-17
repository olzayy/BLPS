package com.example.blps.repo;

import com.example.blps.module.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    List<Company> findAllByUserEmail(String email);

    @Query("select c from Company c where c.acceptable = true and (c.org_name = :org_name or c.inn = :inn or c.ogrn = :ogrn)")
    List<Company> findAllByOrg_nameOrInnOrOgrn(@Param("org_name") Optional<String> org_name, @Param("inn") Optional<String> inn, @Param("ogrn") Optional<String> ogrn);

    @Query("select c from Company c where c.acceptable = false and c.user != (select u from User u where u.email = :email)")
    List<Company> findAllUnacceptable(@Param("email") String email);

    Company findCompanyByInn(String inn);

    @Modifying
    @Transactional
    @Query("update Company c set c.rating = :rate where c.inn = :inn")
    void updateCompanyByInn(@Param("inn") String inn, @Param("rate") Integer rate);
}
