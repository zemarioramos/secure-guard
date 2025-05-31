package com.z7design.secured_guard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<ValidCNPJ, String> {
    
    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return true; // Let @NotBlank handle empty validation
        }
        
        // Remove formatting
        cnpj = cnpj.replaceAll("[^\\d]", "");
        
        if (cnpj.length() != 14) {
            return false;
        }
        
        // Check for known invalid CNPJs
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        
        // Validate check digits
        return validateCNPJCheckDigits(cnpj);
    }
    
    private boolean validateCNPJCheckDigits(String cnpj) {
        // CNPJ validation algorithm
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        // First check digit
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
        }
        int remainder = sum % 11;
        int checkDigit1 = remainder < 2 ? 0 : 11 - remainder;
        
        if (Character.getNumericValue(cnpj.charAt(12)) != checkDigit1) {
            return false;
        }
        
        // Second check digit
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
        }
        remainder = sum % 11;
        int checkDigit2 = remainder < 2 ? 0 : 11 - remainder;
        
        return Character.getNumericValue(cnpj.charAt(13)) == checkDigit2;
    }
}