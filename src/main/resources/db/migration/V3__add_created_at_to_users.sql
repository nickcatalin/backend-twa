-- Add the created_at column if it doesn't exist
ALTER TABLE app_user ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;

-- Update existing records with current timestamp
UPDATE app_user SET created_at = CURRENT_TIMESTAMP WHERE created_at IS NULL; 