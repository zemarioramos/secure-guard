-- Criação da tabela de logs de atividades
CREATE TABLE IF NOT EXISTS user_activity_logs (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    action VARCHAR(255) NOT NULL,
    details TEXT,
    timestamp TIMESTAMP NOT NULL
);

-- Criação da tabela de logs de erros
CREATE TABLE IF NOT EXISTS error_logs (
    id BIGSERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    endpoint VARCHAR(255),
    stack_trace TEXT,
    timestamp TIMESTAMP NOT NULL
);

-- Índices para melhor performance
CREATE INDEX IF NOT EXISTS idx_user_activity_username ON user_activity_logs(username);
CREATE INDEX IF NOT EXISTS idx_user_activity_action ON user_activity_logs(action);
CREATE INDEX IF NOT EXISTS idx_user_activity_timestamp ON user_activity_logs(timestamp);
CREATE INDEX IF NOT EXISTS idx_error_endpoint ON error_logs(endpoint);
CREATE INDEX IF NOT EXISTS idx_error_timestamp ON error_logs(timestamp); 