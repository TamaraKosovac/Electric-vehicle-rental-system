package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.unibl.etf.ip.erent.dto.PromotionDTO;
import org.unibl.etf.ip.erent.model.Employee;
import org.unibl.etf.ip.erent.model.Promotion;
import org.unibl.etf.ip.erent.repository.EmployeeRepository;
import org.unibl.etf.ip.erent.repository.PromotionRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final EmployeeRepository employeeRepository;

    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    public Promotion findById(Long id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found"));
    }

    public Promotion createFromDTO(PromotionDTO dto) {
        Employee creator = employeeRepository.findById(dto.getCreatedByEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        Promotion promotion = new Promotion();
        promotion.setTitle(dto.getTitle());
        promotion.setContent(dto.getContent());
        promotion.setCreatedAt(LocalDate.now());
        promotion.setStartDate(dto.getStartDate());
        promotion.setEndDate(dto.getEndDate());
        promotion.setCreatedBy(creator);

        return promotionRepository.save(promotion);
    }

    public Promotion updateFromDTO(Long id, PromotionDTO dto) {
        Promotion promotion = findById(id);

        Employee creator = employeeRepository.findById(dto.getCreatedByEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        promotion.setTitle(dto.getTitle());
        promotion.setContent(dto.getContent());
        promotion.setStartDate(dto.getStartDate());
        promotion.setEndDate(dto.getEndDate());
        promotion.setCreatedBy(creator);

        return promotionRepository.save(promotion);
    }

    public void delete(Long id) {
        if (!promotionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotion not found");
        }
        promotionRepository.deleteById(id);
    }
}
