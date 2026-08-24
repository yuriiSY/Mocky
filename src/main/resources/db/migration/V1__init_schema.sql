CREATE TABLE mock_endpoints (
    id UUID DEFAULT random_uuid() PRIMARY KEY,
    path VARCHAR(255) NOT NULL,
    method VARCHAR(10) NOT NULL,
    response_status INT NOT NULL DEFAULT 200,
    response_body TEXT,
    delay_ms BIGINT DEFAULT 0,
    failure_rate INT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_path_method UNIQUE (path, method)
);

CREATE INDEX idx_mock_path_method ON mock_endpoints(path, method);