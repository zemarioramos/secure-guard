-- Adding foreign key to proposals table
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS contract_id BIGINT;
ALTER TABLE proposals ADD CONSTRAINT fk_proposals_contract FOREIGN KEY (contract_id) REFERENCES contracts(id); 