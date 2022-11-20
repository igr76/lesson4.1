CREATE TABLE student (
    id REAL,
    name TEXT PRIMARY KEY ,
    faculty_id TEXT REFERENCES faculty (id),
    age INTEGER CHECK ( age >=16 ),
    name TEXT NOT NULL ,
    age DEFAULT 20,

)
CREATE TABLE faculty(
    id REAL,
    name TEXT PRIMARY KEY ,
    color TEXT TEXT PRIMARY KEY ,
    color,name TEXT UNIQUE ,
)
