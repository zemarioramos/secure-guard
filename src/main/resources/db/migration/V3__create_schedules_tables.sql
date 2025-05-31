-- Creating locations table
CREATE TABLE IF NOT EXISTS locations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    address VARCHAR(255) NOT NULL,
    unit_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_locations_unit FOREIGN KEY (unit_id) REFERENCES units(id)
);

-- Creating routes table
CREATE TABLE IF NOT EXISTS routes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    unit_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_routes_unit FOREIGN KEY (unit_id) REFERENCES units(id),
    CONSTRAINT fk_routes_location FOREIGN KEY (location_id) REFERENCES locations(id)
);

-- Creating patrols table
CREATE TABLE IF NOT EXISTS patrols (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    route_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    observations TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_patrols_route FOREIGN KEY (route_id) REFERENCES routes(id),
    CONSTRAINT fk_patrols_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Creating shifts table
CREATE TABLE IF NOT EXISTS shifts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Creating schedules table
CREATE TABLE IF NOT EXISTS schedules (
    id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_schedules_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- Creating schedule_employees table
CREATE TABLE IF NOT EXISTS schedule_employees (
    id BIGSERIAL PRIMARY KEY,
    schedule_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,
    visual_order INTEGER NOT NULL,
    observations TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_schedule_employees_schedule FOREIGN KEY (schedule_id) REFERENCES schedules(id),
    CONSTRAINT fk_schedule_employees_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
    CONSTRAINT fk_schedule_employees_position FOREIGN KEY (position_id) REFERENCES positions(id)
);

-- Creating triggers for updating updated_at column
CREATE TRIGGER update_locations_updated_at
    BEFORE UPDATE ON locations
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_routes_updated_at
    BEFORE UPDATE ON routes
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_patrols_updated_at
    BEFORE UPDATE ON patrols
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_shifts_updated_at
    BEFORE UPDATE ON shifts
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_schedules_updated_at
    BEFORE UPDATE ON schedules
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_schedule_employees_updated_at
    BEFORE UPDATE ON schedule_employees
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Creating indexes
CREATE INDEX IF NOT EXISTS idx_locations_unit_id ON locations(unit_id);
CREATE INDEX IF NOT EXISTS idx_routes_unit_id ON routes(unit_id);
CREATE INDEX IF NOT EXISTS idx_routes_location_id ON routes(location_id);
CREATE INDEX IF NOT EXISTS idx_patrols_route_id ON patrols(route_id);
CREATE INDEX IF NOT EXISTS idx_patrols_employee_id ON patrols(employee_id);
CREATE INDEX IF NOT EXISTS idx_schedules_employee_id ON schedules(employee_id);
CREATE INDEX IF NOT EXISTS idx_schedules_status ON schedules(status);
CREATE INDEX IF NOT EXISTS idx_schedule_employees_schedule_id ON schedule_employees(schedule_id);
CREATE INDEX IF NOT EXISTS idx_schedule_employees_employee_id ON schedule_employees(employee_id);
CREATE INDEX IF NOT EXISTS idx_schedule_employees_position_id ON schedule_employees(position_id); 