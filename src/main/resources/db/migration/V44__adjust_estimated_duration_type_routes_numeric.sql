-- Adjusting estimated_duration column type to numeric in routes table
ALTER TABLE routes ALTER COLUMN estimated_duration TYPE NUMERIC(10,2); 