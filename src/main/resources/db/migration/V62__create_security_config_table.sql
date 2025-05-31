CREATE TABLE security_config (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    password_min_length INTEGER NOT NULL DEFAULT 8,
    require_uppercase BOOLEAN NOT NULL DEFAULT TRUE,
    require_lowercase BOOLEAN NOT NULL DEFAULT TRUE,
    require_numbers BOOLEAN NOT NULL DEFAULT TRUE,
    require_special_chars BOOLEAN NOT NULL DEFAULT TRUE,
    session_timeout_minutes INTEGER NOT NULL DEFAULT 30,
    max_login_attempts INTEGER NOT NULL DEFAULT 5,
    lockout_duration_minutes INTEGER NOT NULL DEFAULT 15,
    enable_two_factor BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER update_security_config_updated_at
    BEFORE UPDATE ON security_config
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Inserir configuração de segurança padrão
INSERT INTO security_config (password_min_length, require_uppercase, require_lowercase, require_numbers, require_special_chars) 
VALUES (8, TRUE, TRUE, TRUE, TRUE);