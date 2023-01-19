create table company(
                        id INTEGER PRIMARY KEY,
                        company_name text NOT NULL
);

create table position(
                         id INTEGER PRIMARY KEY,
                         company_name text NOT NULL,
                         company_id INTEGER REFERENCES company NOT NULL
);


create table employee (
                          id INTEGER PRIMARY KEY,
                          name text NOT NULL,
                          last_name text NOT NULL,
                          birthday date,
                          position_at_work INTEGER REFERENCES position,
                          salary FLOAT
);

