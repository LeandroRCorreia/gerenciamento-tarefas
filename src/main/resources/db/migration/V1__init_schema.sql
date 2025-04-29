CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    departamento VARCHAR(255) NOT NULL
);

CREATE TABLE tarefa (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    prazo DATE NOT NULL,
    departamento VARCHAR(255) NOT NULL,
    duracao_horas INTEGER,
    finalizado BOOLEAN NOT NULL,
    pessoa_id INTEGER REFERENCES pessoa(id)
);
