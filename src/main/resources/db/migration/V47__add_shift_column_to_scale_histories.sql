-- Create shifts table if not exists
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

-- Adding shift column to scale_histories table
ALTER TABLE scale_histories ADD COLUMN IF NOT EXISTS shift_id BIGINT;

CREATE INDEX IF NOT EXISTS idx_scale_histories_shift_id ON scale_histories(shift_id);

ALTER TABLE scale_histories ADD CONSTRAINT fk_scale_histories_shift FOREIGN KEY (shift_id) REFERENCES shifts(id); 