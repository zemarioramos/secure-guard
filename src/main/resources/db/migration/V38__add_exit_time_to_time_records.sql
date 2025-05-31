-- Adding exit_time column to time_records table
ALTER TABLE time_records ADD COLUMN IF NOT EXISTS exit_time TIME;

CREATE INDEX IF NOT EXISTS idx_time_records_exit_time ON time_records(exit_time); 