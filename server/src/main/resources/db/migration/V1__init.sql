CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) CHECK (role IN ('ENGINEER', 'MANAGER', 'OBSERVER')) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Projects (
    project_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    manager_id INTEGER REFERENCES Users(user_id)
);

CREATE TABLE Defects (
    defect_id SERIAL PRIMARY KEY,
    project_id INTEGER REFERENCES Projects(project_id),
    title VARCHAR(100) NOT NULL,
    description TEXT,
    priority VARCHAR(20) CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH')),
    status VARCHAR(20) CHECK (status IN ('NEW', 'IN_PROGRESS', 'UNDER_REVIEW', 'CLOSED', 'CANCELED')),
    assignee_id INTEGER REFERENCES Users(user_id),
    created_by INTEGER REFERENCES Users(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deadline DATE,
    attachment_path VARCHAR(255),
    severity VARCHAR(20) CHECK (severity IN ('MINOR', 'MAJOR', 'CRITICAL')),
    building_section VARCHAR(100)
);

CREATE TABLE Reports (
    report_id SERIAL PRIMARY KEY,
    project_id INTEGER REFERENCES Projects(project_id),
    title VARCHAR(100) NOT NULL,
    type VARCHAR(20) CHECK (type IN ('CSV', 'EXCEL', 'ANALYTICS')),
    generated_by INTEGER REFERENCES Users(user_id),
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    file_path VARCHAR(255)
);

CREATE TABLE Comments (
    comment_id SERIAL PRIMARY KEY,
    defect_id INTEGER REFERENCES Defects(defect_id),
    user_id INTEGER REFERENCES Users(user_id),
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Project_Users (
    project_id INTEGER REFERENCES Projects(project_id),
    user_id INTEGER REFERENCES Users(user_id),
    role_in_project VARCHAR(50),
    PRIMARY KEY (project_id, user_id)
);

CREATE TABLE History (
    history_id SERIAL PRIMARY KEY,
    defect_id INTEGER REFERENCES Defects(defect_id),
    user_id INTEGER REFERENCES Users(user_id),
    action VARCHAR(100) NOT NULL,
    old_value TEXT,
    new_value TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);