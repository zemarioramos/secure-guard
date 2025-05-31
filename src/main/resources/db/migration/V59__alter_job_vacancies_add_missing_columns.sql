-- Renaming to V59__alter_job_vacancies_add_missing_columns.sql
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS company_id BIGINT REFERENCES clients(id);
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS contract_id BIGINT REFERENCES contracts(id);
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

CREATE INDEX IF NOT EXISTS idx_job_vacancies_company_id ON job_vacancies(company_id);
CREATE INDEX IF NOT EXISTS idx_job_vacancies_contract_id ON job_vacancies(contract_id); 