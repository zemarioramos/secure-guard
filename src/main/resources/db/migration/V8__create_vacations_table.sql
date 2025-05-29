CREATE TABLE IF NOT EXISTS vacations (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    days_taken INT NOT NULL,
    remaining_days INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    approved_by BIGINT,
    approval_date DATE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (approved_by) REFERENCES users(id)
); 