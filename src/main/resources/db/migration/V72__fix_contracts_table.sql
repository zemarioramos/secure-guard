-- Adiciona colunas faltantes na tabela contracts
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS contract_number VARCHAR(50) NOT NULL DEFAULT 'C' || TO_CHAR(NOW(), 'YYYYMMDDHH24MISS');
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS type VARCHAR(50) NOT NULL DEFAULT 'SERVICO';
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS start_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS end_date TIMESTAMP;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS created_by UUID REFERENCES users(id);
ALTER TABLE contracts ADD COLUMN IF NOT EXISTS updated_by UUID REFERENCES users(id);

-- Adiciona índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_contracts_contract_number ON contracts(contract_number);
CREATE INDEX IF NOT EXISTS idx_contracts_status ON contracts(status);
CREATE INDEX IF NOT EXISTS idx_contracts_start_date ON contracts(start_date);
CREATE INDEX IF NOT EXISTS idx_contracts_end_date ON contracts(end_date); 