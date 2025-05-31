-- Add missing fields to time_records table
DO $$
BEGIN
    -- Check if time_records table exists
    IF EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'time_records') THEN
        -- Add missing columns if they do not exist
        BEGIN
            ALTER TABLE time_records ADD COLUMN employee_id UUID;
        EXCEPTION WHEN duplicate_column THEN END;
        
        BEGIN
            ALTER TABLE time_records ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'PENDING';
        EXCEPTION WHEN duplicate_column THEN END;
        
        BEGIN
            ALTER TABLE time_records ADD COLUMN lunch_entry_time TIME;
        EXCEPTION WHEN duplicate_column THEN END;
        
        BEGIN
            ALTER TABLE time_records ADD COLUMN lunch_exit_time TIME;
        EXCEPTION WHEN duplicate_column THEN END;
        
        BEGIN
            ALTER TABLE time_records ADD COLUMN justification VARCHAR(255);
        EXCEPTION WHEN duplicate_column THEN END;
        
        BEGIN
            ALTER TABLE time_records ADD CONSTRAINT fk_time_records_employee FOREIGN KEY (employee_id) REFERENCES employees(id);
        EXCEPTION WHEN duplicate_object THEN END;

        -- Drop old columns if they exist
        BEGIN
            ALTER TABLE time_records DROP COLUMN user_id;
        EXCEPTION WHEN undefined_column THEN END;
        
        BEGIN
            ALTER TABLE time_records DROP COLUMN data_hora;
        EXCEPTION WHEN undefined_column THEN END;
        
        BEGIN
            ALTER TABLE time_records DROP COLUMN tipo;
        EXCEPTION WHEN undefined_column THEN END;
        
        BEGIN
            ALTER TABLE time_records DROP COLUMN observacao;
        EXCEPTION WHEN undefined_column THEN END;

        -- Migrate data from registros_ponto to time_records
        INSERT INTO time_records (
            id,
            employee_id,
            record_date,
            entry_time,
            exit_time,
            lunch_entry_time,
            lunch_exit_time,
            status,
            justification,
            created_at,
            updated_at
        )
        SELECT 
            gen_random_uuid(),
            e.id,
            CURRENT_DATE,
            hora_entrada::TIME,
            hora_saida::TIME,
            hora_entrada_almoco::TIME,
            hora_saida_almoco::TIME,
            'PENDING',
            rp.observacao,
            rp.created_at,
            COALESCE(rp.updated_at, rp.created_at)
        FROM registros_ponto rp
        JOIN users u ON rp.user_id = u.id
        JOIN employees e ON e.user_id = u.id;

        -- Drop the old table
        DROP TABLE IF EXISTS registros_ponto;
    ELSE
        -- If time_records doesn't exist, rename registros_ponto
        ALTER TABLE registros_ponto RENAME TO time_records;

        -- Add new columns
        ALTER TABLE time_records ADD COLUMN employee_id UUID NOT NULL;
        ALTER TABLE time_records ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'PENDING';
        ALTER TABLE time_records ADD COLUMN lunch_entry_time TIME;
        ALTER TABLE time_records ADD COLUMN lunch_exit_time TIME;
        ALTER TABLE time_records ADD COLUMN justification VARCHAR(255);
        ALTER TABLE time_records ADD CONSTRAINT fk_time_records_employee FOREIGN KEY (employee_id) REFERENCES employees(id);

        -- Update employee_id based on user_id
        UPDATE time_records tr
        SET employee_id = e.id
        FROM users u
        JOIN employees e ON e.user_id = u.id
        WHERE tr.user_id = u.id;

        -- Rename columns
        ALTER TABLE time_records RENAME COLUMN hora_entrada TO entry_time;
        ALTER TABLE time_records RENAME COLUMN hora_saida TO exit_time;
        ALTER TABLE time_records RENAME COLUMN hora_entrada_almoco TO lunch_entry_time;
        ALTER TABLE time_records RENAME COLUMN hora_saida_almoco TO lunch_exit_time;
        ALTER TABLE time_records RENAME COLUMN observacao TO justification;

        -- Convert time columns to proper TIME type
        ALTER TABLE time_records ALTER COLUMN entry_time TYPE TIME USING entry_time::TIME;
        ALTER TABLE time_records ALTER COLUMN exit_time TYPE TIME USING exit_time::TIME;
        ALTER TABLE time_records ALTER COLUMN lunch_entry_time TYPE TIME USING lunch_entry_time::TIME;
        ALTER TABLE time_records ALTER COLUMN lunch_exit_time TYPE TIME USING lunch_exit_time::TIME;

        -- Drop old columns that are no longer needed
        ALTER TABLE time_records DROP COLUMN user_id;
        ALTER TABLE time_records DROP COLUMN data_hora;
        ALTER TABLE time_records DROP COLUMN tipo;
    END IF;

    -- Adding missing fields to time_records table
    ALTER TABLE time_records
        ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
        ADD COLUMN IF NOT EXISTS justification TEXT,
        ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

    CREATE INDEX IF NOT EXISTS idx_time_records_status ON time_records(status);
    CREATE INDEX IF NOT EXISTS idx_time_records_created_at ON time_records(created_at);
END $$; 