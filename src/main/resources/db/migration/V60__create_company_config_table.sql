CREATE TABLE empresa_config (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    inscricao_estadual VARCHAR(20),
    endereco VARCHAR(200) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100) NOT NULL,
    website VARCHAR(200),
    logo_path VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger para atualizar updated_at automaticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_empresa_config_updated_at
    BEFORE UPDATE ON empresa_config
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Inserir configuração padrão
INSERT INTO empresa_config (nome, cnpj, endereco, email) 
VALUES ('Secure Guard Control', '00.000.000/0001-00', 'Endereço da Empresa', 'contato@secureguard.com');