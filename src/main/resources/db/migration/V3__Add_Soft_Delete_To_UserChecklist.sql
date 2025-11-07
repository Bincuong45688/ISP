-- Add soft delete columns to user_checklists table
ALTER TABLE user_checklists 
ADD COLUMN is_active BOOLEAN DEFAULT TRUE,
ADD COLUMN deleted_at TIMESTAMP NULL;

-- Update existing records to be active
UPDATE user_checklists 
SET is_active = TRUE 
WHERE is_active IS NULL;

-- Add index for better query performance
CREATE INDEX idx_user_checklists_is_active ON user_checklists(is_active);
CREATE INDEX idx_user_checklists_user_active ON user_checklists(user_id, is_active);
