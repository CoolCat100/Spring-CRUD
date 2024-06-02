package com.example.SpringCRUD.service;

import com.example.SpringCRUD.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TaxService {
    public double countOldTax(double salary) {
        Map<Integer, Double> TAX_STEPS = new LinkedHashMap<>();
        {
            TAX_STEPS.put(416700, 0.15);
            TAX_STEPS.put(0, 0.13);
        }
        return countTax(salary, TAX_STEPS);
    }

    public double countNewTax(double salary) {
        Map<Integer, Double> TAX_STEPS = new LinkedHashMap<>();
        {
            TAX_STEPS.put(4170000, 0.22);
            TAX_STEPS.put(1670000, 0.20);
            TAX_STEPS.put(416700, 0.18);
            TAX_STEPS.put(200000, 0.15);
            TAX_STEPS.put(0, 0.13);
        }
        return countTax(salary, TAX_STEPS);
    }
    private double countTax(double salary, Map<Integer, Double> TAX_STEPS) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Зарплата должна быть больше нуля");
        }

        double tax = 0;

        for (Map.Entry<Integer, Double> step : TAX_STEPS.entrySet()) {
            if (salary > step.getKey()) {
                tax += (salary - step.getKey()) * step.getValue();
                salary = step.getKey();
            }
        }
        return tax;
    }
}
