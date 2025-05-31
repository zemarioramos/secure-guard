-- Altering entry_time column type in time_records table
ALTER TABLE time_records ALTER COLUMN entry_time TYPE TIME USING entry_time::TIME; 