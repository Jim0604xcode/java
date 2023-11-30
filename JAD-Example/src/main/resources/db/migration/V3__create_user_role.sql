CREATE TYPE enum_type AS ENUM ('member', 'admin', 'guest');
CREATE CAST (varchar AS enum_type) WITH INOUT AS IMPLICIT;
ALTER TABLE users ADD role enum_type;