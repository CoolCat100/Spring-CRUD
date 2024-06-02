package com.example.SpringCRUD.service;

import com.example.SpringCRUD.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class TaxService {
    public double countOldTax(double salary) {
        double tax = 0;
        int delimiterTax = 416700;
        double firstStepTax = 0.13;
        double secondStepTax = 0.15;
        if (salary > delimiterTax) {
            double salaryForTax = salary - delimiterTax;
            tax = salaryForTax * secondStepTax;
            salary = salary - salaryForTax;
        }
        tax = tax + (salary * firstStepTax);
        return tax;
    }

    public double countNewTax(double salary) {
        double tax = 0;
        int firstTaxDelimiter = 200000;
        int secondTaxDelimiter = 416700;
        int thirdTaxDelimiter = 1670000;
        int forthTaxDelimiter = 4170000;
        double firstStepTax = 0.13;
        double secondStepTax = 0.15;
        double thirdStepTax = 0.18;
        double forthStepTax = 0.2;
        double fifthStepTax = 0.22;
        if (salary > forthTaxDelimiter) {
            double salaryForTax = salary - forthTaxDelimiter;
            tax = tax + salaryForTax * fifthStepTax;
            salary = salary - salaryForTax;
        }
        if (salary > thirdTaxDelimiter) {
            double salaryForTax = salary - thirdTaxDelimiter;
            tax = tax + salaryForTax * forthStepTax;
            salary = salary - salaryForTax;
        }
        if (salary > secondTaxDelimiter) {
            double salaryForTax = salary - secondTaxDelimiter;
            tax = tax + salaryForTax * thirdStepTax;
            salary = salary - salaryForTax;
        }
        if (salary > firstTaxDelimiter) {
            double salaryForTax = salary - firstTaxDelimiter;
            tax = tax + salaryForTax * secondStepTax;
            salary = salary - salaryForTax;
        }
        tax = tax + (salary * firstStepTax);
        return tax;
    }
}
