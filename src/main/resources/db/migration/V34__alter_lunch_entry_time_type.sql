-- Altering lunch_entry_time column type in time_records table
ALTER TABLE time_records ALTER COLUMN lunch_entry_time TYPE TIME USING lunch_entry_time::TIME; 