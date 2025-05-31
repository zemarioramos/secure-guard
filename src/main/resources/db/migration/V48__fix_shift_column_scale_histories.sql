-- Migrating from shift (VARCHAR) to shift_id (BIGINT)
-- First, ensure we have the shifts table
CREATE TABLE IF NOT EXISTS shifts (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insert default shifts if they don't exist
INSERT INTO shifts (id, name, start_time, end_time)
SELECT 1, 'MORNING', '06:00:00', '14:00:00'
WHERE NOT EXISTS (SELECT 1 FROM shifts WHERE name = 'MORNING');

INSERT INTO shifts (id, name, start_time, end_time)
SELECT 2, 'AFTERNOON', '14:00:00', '22:00:00'
WHERE NOT EXISTS (SELECT 1 FROM shifts WHERE name = 'AFTERNOON');

INSERT INTO shifts (id, name, start_time, end_time)
SELECT 3, 'NIGHT', '22:00:00', '06:00:00'
WHERE NOT EXISTS (SELECT 1 FROM shifts WHERE name = 'NIGHT');

-- Add shift_id column if it doesn't exist
ALTER TABLE scale_histories ADD COLUMN IF NOT EXISTS shift_id BIGINT;

-- Update shift_id based on shift value
UPDATE scale_histories 
SET shift_id = CASE 
    WHEN shift = 'MORNING' THEN 1
    WHEN shift = 'AFTERNOON' THEN 2
    WHEN shift = 'NIGHT' THEN 3
    ELSE 1 -- Default to MORNING if unknown
END
WHERE shift_id IS NULL;

-- Create index and foreign key
CREATE INDEX IF NOT EXISTS idx_scale_histories_shift_id ON scale_histories(shift_id);
ALTER TABLE scale_histories ADD CONSTRAINT fk_scale_histories_shift FOREIGN KEY (shift_id) REFERENCES shifts(id);

-- Drop the old shift column
ALTER TABLE scale_histories DROP COLUMN IF EXISTS shift; 