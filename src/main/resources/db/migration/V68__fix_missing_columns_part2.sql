-- Adiciona colunas faltantes na tabela contracts
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS contract_number VARCHAR(50) NOT NULL DEFAULT 'C' || TO_CHAR(NOW(), 'YYYYMMDDHH24MISS');
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS type VARCHAR(50) NOT NULL DEFAULT 'SERVICO';
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS start_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS end_date TIMESTAMP;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- Adiciona colunas faltantes na tabela clients
ALTER TABLE clients ADD COLUMN IF NOT EXISTS company_name VARCHAR(100) NOT NULL;
ALTER TABLE clients ADD COLUMN IF NOT EXISTS phone VARCHAR(20);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS whatsapp VARCHAR(20);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS email VARCHAR(100);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS responsible_person VARCHAR(100);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS contact VARCHAR(100);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE clients ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- Adiciona índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_contracts_contract_number ON contracts(contract_number);
CREATE INDEX IF NOT EXISTS idx_contracts_status ON contracts(status);
CREATE INDEX IF NOT EXISTS idx_clients_company_name ON clients(company_name);
CREATE INDEX IF NOT EXISTS idx_clients_cnpj ON clients(cnpj); 