– liquibase formatted sql

– changeset jrembo:1
CREATE INDEX search_for_name ON students (name);
CREATE INDEX search_for_name_color ON faculty (name, color);