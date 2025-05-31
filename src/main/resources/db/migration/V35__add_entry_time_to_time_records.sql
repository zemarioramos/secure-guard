-- Adding entry_time column to time_records table
ALTER TABLE time_records ADD COLUMN IF NOT EXISTS entry_time TIME NOT NULL;

CREATE INDEX IF NOT EXISTS idx_time_records_entry_time ON time_records(entry_time);

ALTER TABLE registros_ponto ADD COLUMN hora_entrada TIMESTAMP; 