-- Ajustes completos na tabela routes para alinhar com a entidade Route
ALTER TABLE routes
    ADD COLUMN IF NOT EXISTS estimated_duration INTERVAL,
    ADD COLUMN IF NOT EXISTS checkpoints_required BOOLEAN NOT NULL DEFAULT FALSE;

-- Ajuste do tipo de description para VARCHAR(500) se necessário
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'routes' AND column_name = 'description' AND data_type = 'text'
    ) THEN
        ALTER TABLE routes ALTER COLUMN description TYPE VARCHAR(500);
    END IF;
END $$;

-- Adding missing columns to routes table
ALTER TABLE routes ADD COLUMN IF NOT EXISTS start_point VARCHAR(255);
ALTER TABLE routes ADD COLUMN IF NOT EXISTS end_point VARCHAR(255);
ALTER TABLE routes ADD COLUMN IF NOT EXISTS distance DECIMAL(10,2);
ALTER TABLE routes ADD COLUMN IF NOT EXISTS estimated_duration BIGINT;
ALTER TABLE routes ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';
ALTER TABLE routes ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE routes ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

CREATE INDEX IF NOT EXISTS idx_routes_status ON routes(status);
CREATE INDEX IF NOT EXISTS idx_routes_created_at ON routes(created_at); 