package com.mustache.bbshospital.service;

import com.mustache.bbshospital.domain.dto.HospitalResponse;
import com.mustache.bbshospital.domain.entity.Hospital;
import com.mustache.bbshospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    HospitalRepository hr;

    @Transactional
    public Page<Hospital> getHospitalList(Pageable pageable) {
        return hr.findAll(pageable);
    }

    public HospitalResponse getHospital(Integer id) {
        Optional<Hospital> optHospital = hr.findById(id); // Entity
        Hospital hospital = optHospital.get();
        HospitalResponse hospitalResponse = Hospital.of(hospital); // DTO
        if (hospital.getBusinessStatusCode() == 13) {
            hospitalResponse.setBusinessStatusName("영업중");
        } else if (hospital.getBusinessStatusCode() == 3) {
            hospitalResponse.setBusinessStatusName("폐업");
        } else {
            hospitalResponse.setBusinessStatusName(String.valueOf(hospital.getBusinessStatusCode()));
        }
        return hospitalResponse;
    }
}

