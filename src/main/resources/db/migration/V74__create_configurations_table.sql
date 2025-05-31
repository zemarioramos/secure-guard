-- Criação da tabela configurations
CREATE TABLE IF NOT EXISTS configurations (
    id BIGSERIAL PRIMARY KEY,
    key VARCHAR(100) NOT NULL,
    value TEXT NOT NULL,
    description TEXT,
    type VARCHAR(50),
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT uk_configurations_key UNIQUE (key)
);

-- Índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_configurations_key ON configurations(key);
CREATE INDEX IF NOT EXISTS idx_configurations_type ON configurations(type);
CREATE INDEX IF NOT EXISTS idx_configurations_active ON configurations(active);

-- Inserir configurações padrão
INSERT INTO configurations (key, value, description, type) VALUES
('company.name', 'Secure Guard', 'Company name', 'COMPANY'),
('company.cnpj', '', 'Company CNPJ', 'COMPANY'),
('company.address', '', 'Company address', 'COMPANY'),
('company.phone', '', 'Company phone', 'COMPANY'),
('company.email', '', 'Company email', 'COMPANY'),
('company.website', '', 'Company website', 'COMPANY'),
('company.description', '', 'Company description', 'COMPANY'),
('security.jwt.secret', 'your-secret-key', 'JWT secret key', 'SECURITY'),
('security.jwt.expiration', '86400000', 'JWT expiration time in milliseconds', 'SECURITY'),
('security.cors.enabled', 'true', 'Enable CORS', 'SECURITY'),
('security.cors.allowed-origins', '*', 'Allowed origins for CORS', 'SECURITY'),
('security.csrf.enabled', 'true', 'Enable CSRF', 'SECURITY'),
('websocket.enabled', 'true', 'Enable WebSocket', 'SYSTEM'),
('websocket.endpoint', '/ws', 'WebSocket endpoint', 'SYSTEM'),
('websocket.allowed-origins', '*', 'Allowed origins for WebSocket', 'SYSTEM')
ON CONFLICT (key) DO NOTHING; 