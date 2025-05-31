CREATE TABLE payslips (
    id UUID PRIMARY KEY,
    employee_name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    month VARCHAR(20) NOT NULL,
    page_number INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Índices para melhorar performance de consultas
CREATE INDEX idx_payslips_cpf ON payslips(cpf);
CREATE INDEX idx_payslips_month ON payslips(month);
CREATE INDEX idx_payslips_created_at ON payslips(created_at); 