CREATE DATABASE stub_requests;
CREATE USER stub_requests WITH PASSWORD 'stub_requests';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO stub_requests;