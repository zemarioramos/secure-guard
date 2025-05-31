-- Adding lunch_exit_time column to time_records table
ALTER TABLE time_records ADD COLUMN IF NOT EXISTS lunch_exit_time TIME;

CREATE INDEX IF NOT EXISTS idx_time_records_lunch_exit_time ON time_records(lunch_exit_time); 