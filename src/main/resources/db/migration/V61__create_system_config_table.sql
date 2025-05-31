CREATE TABLE system_config (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT NOT NULL,
    description VARCHAR(500),
    data_type VARCHAR(20) NOT NULL DEFAULT 'STRING',
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER update_system_config_updated_at
    BEFORE UPDATE ON system_config
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Inserir configurações padrão do sistema
INSERT INTO system_config (config_key, config_value, description, data_type, is_public) VALUES
('app.name', 'Secure Guard Control', 'Nome da aplicação', 'STRING', true),
('app.version', '1.0.0', 'Versão da aplicação', 'STRING', true),
('app.maintenance_mode', 'false', 'Modo de manutenção', 'BOOLEAN', false),
('notification.email_enabled', 'true', 'Notificações por email habilitadas', 'BOOLEAN', false),
('notification.sms_enabled', 'false', 'Notificações por SMS habilitadas', 'BOOLEAN', false),
('backup.auto_enabled', 'true', 'Backup automático habilitado', 'BOOLEAN', false),
('backup.frequency_hours', '24', 'Frequência de backup em horas', 'INTEGER', false);