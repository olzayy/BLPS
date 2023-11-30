package com.example.blps.controller;

import com.example.blps.module.entity.Company;
import com.example.blps.module.request.CompanyRequestDTO;
import com.example.blps.module.response.CompanyResponseDTO;
import com.example.blps.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "${cors.urls}")
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

	private final CompanyService companyService;

	@GetMapping(value = "/my")
	public ResponseEntity<?> getUserCompanies() {

		List<Company> companyList = companyService.findAllCompaniesByEmail(getCurrentEmail());

		if (companyList.isEmpty()) {
			return ResponseEntity.ok("Вы не размещали данные о компаниях.");
		}

		List<CompanyResponseDTO> companyResponseDtoList = new ArrayList<>();
		getCompanyResponseDTOList(companyList, companyResponseDtoList);

		return ResponseEntity.ok(companyResponseDtoList);
	}

	@PutMapping("/add")
	public ResponseEntity<?> addNewCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) {

		Company company = new Company();

		company.setOrg_name(companyRequestDTO.getOrgName());
		company.setInn(companyRequestDTO.getInn());
		company.setOgrn(companyRequestDTO.getOgrn());
		company.setPhone(companyRequestDTO.getPhone());

		if (companyRequestDTO.getWebsite() != null) {
			company.setWebsite(companyRequestDTO.getWebsite());
		}
		if (companyRequestDTO.getDescription() != null) {
			company.setDescription(companyRequestDTO.getDescription());
		}

		company.setAcceptable(false);
		company.setBelief("MID");

		companyService.addNewCompanyWithEmail(company);

		return ResponseEntity.ok("Ваша заявка на добавление компании успешно отправлена на модерацию");
	}

	@GetMapping("/unacceptable")
	public ResponseEntity<?> getUnacceptableCompanies() {
		List<Company> companyList = companyService.findAllUnacceptable(getCurrentEmail());
		List<CompanyResponseDTO> companyResponseDTOList = new ArrayList<>();
		getCompanyResponseDTOList(companyList, companyResponseDTOList);

		return ResponseEntity.ok(companyResponseDTOList);
	}

	@GetMapping(value = "/get")
	public ResponseEntity<?> searchAllCompanies(@RequestParam String org_name,
												@RequestParam String inn,
												@RequestParam String ogrn) {
		List<Company> companyList = companyService.findAllbyParams(org_name, inn, ogrn);
		List<CompanyResponseDTO> companyResponseDTOList = new ArrayList<>();
		getCompanyResponseDTOList(companyList, companyResponseDTOList);

		return ResponseEntity.ok(companyResponseDTOList);
	}

	@PutMapping("/rate/{inn}")
	public ResponseEntity<?> rateCompany(@PathVariable String inn,
										 @RequestParam("rating") Integer rating) {
		Company company = companyService.findCompanyByInn(inn);
		if (Objects.equals(company.getUser().getEmail(), getCurrentEmail())) {
			return ResponseEntity.ok("Голосовать за свои компании воспрещается");
		}
		companyService.updateCompanyByInn(inn, rating + company.getRating());
		return ResponseEntity.ok("Ваш голос засчитан");
	}

	private void getCompanyResponseDTOList(List<Company> trueCompanyList,
										   List<CompanyResponseDTO> companyResponseDtoList) {
		CompanyResponseDTO companyResponseDTO;
		for (Company company : trueCompanyList) {
			companyResponseDTO = new CompanyResponseDTO();
			companyResponseDTO.setOrgName(company.getOrg_name());
			companyResponseDTO.setInn(company.getInn());
			companyResponseDTO.setOgrn(company.getOgrn());
			companyResponseDTO.setPhone(company.getPhone());
			companyResponseDTO.setWebsite(company.getWebsite());
			companyResponseDTO.setDescription(company.getDescription());
			companyResponseDTO.setBelief(company.getBelief());
			companyResponseDtoList.add(companyResponseDTO);
		}
	}

	private String getCurrentEmail() {
		return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
}
