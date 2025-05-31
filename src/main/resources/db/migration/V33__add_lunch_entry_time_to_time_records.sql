-- Adding lunch_entry_time column to time_records table
ALTER TABLE time_records ADD COLUMN IF NOT EXISTS lunch_entry_time TIME;

CREATE INDEX IF NOT EXISTS idx_time_records_lunch_entry_time ON time_records(lunch_entry_time); 