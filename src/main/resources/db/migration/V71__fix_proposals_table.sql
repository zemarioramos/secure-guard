-- Adiciona colunas faltantes na tabela proposals
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS proposal_number VARCHAR(50) NOT NULL DEFAULT 'P' || TO_CHAR(NOW(), 'YYYYMMDDHH24MISS');
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'PENDING';
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS created_by UUID REFERENCES users(id);
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS updated_by UUID REFERENCES users(id);

-- Adiciona índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_proposals_proposal_number ON proposals(proposal_number);
CREATE INDEX IF NOT EXISTS idx_proposals_status ON proposals(status);
CREATE INDEX IF NOT EXISTS idx_proposals_created_at ON proposals(created_at); 