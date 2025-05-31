-- Adding record_date column to time_records table
ALTER TABLE time_records ADD COLUMN IF NOT EXISTS record_date DATE NOT NULL DEFAULT CURRENT_DATE;

CREATE INDEX IF NOT EXISTS idx_time_records_record_date ON time_records(record_date);

ALTER TABLE registros_ponto ADD COLUMN data_registro DATE; 