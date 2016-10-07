CREATE DATABASE Contabilidade;

USE Contabilidade;

CREATE TABLE TipoDespesa (
    cod INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome_Tipo VARCHAR(30)
);

CREATE OR REPLACE VIEW vwTipoDespesa AS
    SELECT 
        nome_Tipo
    FROM
        TipoDespesa
    ORDER BY nome_Tipo;

CREATE TABLE Despesa (
    cod INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    data_desp DATETIME,
    cod_TipoDespesa INT,
    nome VARCHAR(30),
    entrada DOUBLE(15 , 2 ),
    saida DOUBLE(15 , 2 ),
    total DOUBLE(15 , 2 )
);

CREATE OR REPLACE VIEW vwDespesas AS
    SELECT 
        Despesa.cod,
        data_desp,
        nome_Tipo,
        nome,
        entrada,
        saida,
        total
    FROM
        Despesa
            INNER JOIN
        TipoDespesa ON Despesa.cod_TipoDespesa = TipoDespesa.cod
    ORDER BY data_desp;
    
CREATE OR REPLACE VIEW vwTotalDespesas AS
    SELECT 
        ROUND(SUM(total), 2)
    FROM
        Despesa;


CREATE TABLE Pagamento (
    cod INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    data_pag DATETIME,
    lancamento VARCHAR(30),
    valor DOUBLE(15 , 2 ),
    descricao VARCHAR(200)
);

CREATE OR REPLACE VIEW vwPagamentos AS
    SELECT 
        cod, data_pag, lancamento, valor, descricao
    FROM
        Pagamento
    ORDER BY data_pag;

CREATE OR REPLACE VIEW vwTotalPagamentos AS
    SELECT 
        ROUND(SUM(valor), 2)
    FROM
        Pagamento;