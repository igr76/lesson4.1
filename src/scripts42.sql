ALTER TABLE students

    AND CONSTRAINT unique_name UNIQUE (name),
    ADD CHECK ( age >=16 ),
    ALTER COLUMN name SET NOT NULL ,
    ALTER COLUMN age SET DEFAULT 20,


ALTER TABLE faculty
    ADD CONSTRAINT unique_name_and_color UNIQUE (name,color);

