-- Adiciona colunas faltantes na tabela proposals
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS proposal_number VARCHAR(50) NOT NULL DEFAULT 'P' || TO_CHAR(NOW(), 'YYYYMMDDHH24MISS');
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'PENDING';
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;
ALTER TABLE proposals ADD COLUMN IF NOT EXISTS description TEXT;

-- Adiciona colunas faltantes na tabela job_vacancies
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS deadline DATE NOT NULL DEFAULT CURRENT_DATE + INTERVAL '30 days';
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS department VARCHAR(100) NOT NULL DEFAULT 'RH';
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS position VARCHAR(100) NOT NULL DEFAULT 'A definir';
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS quantity INTEGER NOT NULL DEFAULT 1;
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS requirements TEXT;
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS benefits TEXT;
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE job_vacancies ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- Adiciona índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_proposals_proposal_number ON proposals(proposal_number);
CREATE INDEX IF NOT EXISTS idx_proposals_status ON proposals(status);
CREATE INDEX IF NOT EXISTS idx_job_vacancies_deadline ON job_vacancies(deadline);
CREATE INDEX IF NOT EXISTS idx_job_vacancies_department ON job_vacancies(department);
CREATE INDEX IF NOT EXISTS idx_job_vacancies_position ON job_vacancies(position); 