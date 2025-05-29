-- Tabela de cargos
CREATE TABLE positions (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    unit_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (unit_id) REFERENCES units(id)
);

-- Tabela de unidades
CREATE TABLE units (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    parent_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES units(id)
);

-- Tabela de funcionários
CREATE TABLE employees (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    rg VARCHAR(20),
    birth_date DATE NOT NULL,
    gender VARCHAR(10),
    marital_status VARCHAR(20),
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    position_id UUID NOT NULL,
    unit_id UUID NOT NULL,
    admission_date DATE NOT NULL,
    resignation_date DATE,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (position_id) REFERENCES positions(id),
    FOREIGN KEY (unit_id) REFERENCES units(id)
);

-- Tabela de documentos
CREATE TABLE documents (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    number VARCHAR(50) NOT NULL,
    issue_date DATE NOT NULL,
    expiration_date DATE,
    file_url VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Tabela de benefícios
CREATE TABLE benefits (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE,
    value DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (position_id) REFERENCES positions(id)
);

-- Tabela de histórico de escalas
CREATE TABLE scale_histories (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    scale VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Tabela de ocorrências
CREATE TABLE occurrences (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    occurrence_date DATE NOT NULL,
    document_url VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Tabela de folha de pagamento
CREATE TABLE payrolls (
    id UUID PRIMARY KEY,
    reference_month VARCHAR(7) NOT NULL,
    unit_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (unit_id) REFERENCES units(id)
);

-- Tabela de EPIs
CREATE TABLE epis (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    issue_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (position_id) REFERENCES positions(id)
);

-- Tabela de itens da folha de pagamento
CREATE TABLE payroll_items (
    id UUID PRIMARY KEY,
    payroll_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (payroll_id) REFERENCES payrolls(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Função para atualizar o updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers para atualizar o updated_at
CREATE TRIGGER update_positions_updated_at
    BEFORE UPDATE ON positions
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_units_updated_at
    BEFORE UPDATE ON units
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_employees_updated_at
    BEFORE UPDATE ON employees
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_documents_updated_at
    BEFORE UPDATE ON documents
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_benefits_updated_at
    BEFORE UPDATE ON benefits
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_scale_histories_updated_at
    BEFORE UPDATE ON scale_histories
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_occurrences_updated_at
    BEFORE UPDATE ON occurrences
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_payrolls_updated_at
    BEFORE UPDATE ON payrolls
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_epis_updated_at
    BEFORE UPDATE ON epis
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column(); 