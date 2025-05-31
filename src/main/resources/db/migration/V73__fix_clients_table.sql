-- Adiciona colunas faltantes na tabela clients
ALTER TABLE clients ADD COLUMN IF NOT EXISTS company_name VARCHAR(100) NOT NULL;
ALTER TABLE clients ADD COLUMN IF NOT EXISTS phone VARCHAR(20);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS whatsapp VARCHAR(20);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS email VARCHAR(100);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS responsible_person VARCHAR(100);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS contact VARCHAR(100);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE clients ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;
ALTER TABLE clients ADD COLUMN IF NOT EXISTS created_by UUID REFERENCES users(id);
ALTER TABLE clients ADD COLUMN IF NOT EXISTS updated_by UUID REFERENCES users(id);

-- Adiciona índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_clients_company_name ON clients(company_name);
CREATE INDEX IF NOT EXISTS idx_clients_cnpj ON clients(cnpj);
CREATE INDEX IF NOT EXISTS idx_clients_email ON clients(email); 