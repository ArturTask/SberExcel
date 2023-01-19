create table company(
    id INTEGER PRIMARY KEY,
    company_name text
);

create table position(
    id INTEGER PRIMARY KEY,
    company_name text,
    company_id INTEGER REFERENCES company
);


create table employee (
    id INTEGER PRIMARY KEY,
    name text NOT NULL,
    last_name text NOT NULL,
    birthday date,
    company_id INTEGER REFERENCES company,
    position_at_work INTEGER REFERENCES position,
    salary FLOAT
);

