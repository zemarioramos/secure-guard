-- V56__create_job_vacancies_table.sql
CREATE TABLE job_vacancies (
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    requirements TEXT NOT NULL,
    salary_range VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Índices para melhorar performance de consultas
CREATE INDEX idx_job_vacancies_status ON job_vacancies(status);
CREATE INDEX idx_job_vacancies_location ON job_vacancies(location);
CREATE INDEX idx_job_vacancies_created_at ON job_vacancies(created_at); 