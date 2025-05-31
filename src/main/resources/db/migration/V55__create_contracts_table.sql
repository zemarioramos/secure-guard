CREATE TABLE contracts (
    id UUID PRIMARY KEY,
    service_type VARCHAR(50) NOT NULL,
    value DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    validity DATE NOT NULL,
    client_id UUID NOT NULL,
    unit_id UUID NOT NULL,
    proposal_id UUID,
    infrastructure_demand TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (unit_id) REFERENCES units(id),
    FOREIGN KEY (proposal_id) REFERENCES proposals(id)
);

-- Índices para melhorar performance de consultas
CREATE INDEX idx_contracts_status ON contracts(status);
CREATE INDEX idx_contracts_client_id ON contracts(client_id);
CREATE INDEX idx_contracts_unit_id ON contracts(unit_id);
CREATE INDEX idx_contracts_validity ON contracts(validity); 