CREATE TABLE error_logs (
    id UUID PRIMARY KEY,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
); 