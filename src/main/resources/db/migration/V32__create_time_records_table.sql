-- Creating time_records table
CREATE TABLE IF NOT EXISTS time_records (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    entry_time TIME NOT NULL,
    lunch_entry_time TIME,
    lunch_exit_time TIME,
    exit_time TIME,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    justification TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_time_records_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Creating indexes
CREATE INDEX IF NOT EXISTS idx_time_records_employee_id ON time_records(employee_id);
CREATE INDEX IF NOT EXISTS idx_time_records_status ON time_records(status);
CREATE INDEX IF NOT EXISTS idx_time_records_created_at ON time_records(created_at); 