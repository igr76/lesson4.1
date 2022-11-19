– liquibase formatted sql

– changeset jrembo:2
CREATE INDEX search_for_name ON students (name);
CREATE INDEX search_for_name_color ON faculty (name, color);