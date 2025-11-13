-- Add soft delete columns to checklistitems table
ALTER TABLE checklistitems 
ADD COLUMN is_active BOOLEAN DEFAULT TRUE,
ADD COLUMN deleted_at TIMESTAMP NULL;

-- Update existing records to be active
UPDATE checklistitems 
SET is_active = TRUE 
WHERE is_active IS NULL;

-- Add index for better query performance
CREATE INDEX idx_checklistitems_is_active ON checklistitems(is_active);
