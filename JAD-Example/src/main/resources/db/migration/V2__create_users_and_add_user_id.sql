CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    username text NOT NULL,
    password text NOT NULL,
    created_at DATE default NOW()
);

ALTER TABLE memos ADD user_id integer ;
ALTER TABLE memos ADD CONSTRAINT fk_memos_users FOREIGN KEY (user_id) references users(id);