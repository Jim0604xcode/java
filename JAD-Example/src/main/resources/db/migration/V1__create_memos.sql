CREATE TABLE memos(
    id SERIAL PRIMARY KEY,
    content text,
    created_at DATE default NOW()
);