-- V54__create_proposals_table.sql
CREATE TABLE proposals (
    id UUID PRIMARY KEY,
    proposal_number VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    value DECIMAL(10,2) NOT NULL,
    description TEXT,
    validity TIMESTAMP NOT NULL,
    contract_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Índices para melhorar performance de consultas
CREATE INDEX idx_proposals_status ON proposals(status);
CREATE INDEX idx_proposals_contract_id ON proposals(contract_id);
CREATE INDEX idx_proposals_created_at ON proposals(created_at);
CREATE INDEX idx_proposals_proposal_number ON proposals(proposal_number);
CREATE INDEX idx_proposals_validity ON proposals(validity);