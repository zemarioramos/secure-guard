-- V54__create_proposals_table.sql
CREATE TABLE proposals (
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    value DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    client_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

-- Índices para melhorar performance de consultas
CREATE INDEX idx_proposals_status ON proposals(status);
CREATE INDEX idx_proposals_client_id ON proposals(client_id);
CREATE INDEX idx_proposals_created_at ON proposals(created_at); 