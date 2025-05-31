-- Adding justification column to time_records table
ALTER TABLE time_records ADD COLUMN IF NOT EXISTS justification TEXT;

CREATE INDEX IF NOT EXISTS idx_time_records_justification ON time_records(justification); 