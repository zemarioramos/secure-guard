CREATE TABLE overtime (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    overtime_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    total_hours DOUBLE PRECISION NOT NULL,
    type VARCHAR(20) NOT NULL,
    reason VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL,
    approved_by BIGINT,
    approval_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (approved_by) REFERENCES users(id)
); 