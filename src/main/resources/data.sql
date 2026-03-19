-- Script de inicialização com dados de exemplo para o Leiloaria

-- ======================
-- CATEGORIAS
-- ======================
INSERT INTO categoria (nome) VALUES ('Eletrônicos');
INSERT INTO categoria (nome) VALUES ('Livros');
INSERT INTO categoria (nome) VALUES ('Roupas');
INSERT INTO categoria (nome) VALUES ('Móveis');
INSERT INTO categoria (nome) VALUES ('Arte');
INSERT INTO categoria (nome) VALUES ('Joias');
INSERT INTO categoria (nome) VALUES ('Decoração');

-- ======================
-- ENDEREÇOS
-- ======================
INSERT INTO endereco (id, rua, logradouro, bairro, cidade, cep, estado) VALUES 
(1, 'Rua das Flores', '123', 'Centro', 'São Paulo', '01234-567', 'SP');

INSERT INTO endereco (id, rua, logradouro, bairro, cidade, cep, estado) VALUES 
(2, 'Avenida Paulista', '1000', 'Bela Vista', 'São Paulo', '01311-100', 'SP');

INSERT INTO endereco (id, rua, logradouro, bairro, cidade, cep, estado) VALUES 
(3, 'Rua da Paz', '456', 'Vila Mariana', 'São Paulo', '04103-000', 'SP');

INSERT INTO endereco (id, rua, logradouro, bairro, cidade, cep, estado) VALUES 
(4, 'Rua do Comércio', '789', 'Consolação', 'São Paulo', '01302-001', 'SP');

-- ======================
-- USUÁRIOS
-- ======================
INSERT INTO usuario (nome, cpf, email, telefone, ativo, senha, data_nascimento, endereco_id, eh_admin) VALUES
('João Silva', '12345678900', 'joao@email.com', '{11987654321}', true, '$2a$10$dTQfxG0LTOx4Pu2nMriftOAv5qxNgd6CCA7V1LhGglxvVW9dJID.m', '1990-05-15', 1, FALSE);

INSERT INTO usuario (nome, cpf, email, telefone, ativo, senha, data_nascimento, endereco_id, eh_admin) VALUES
('Maria Santos', '98765432100', 'maria@email.com', '{11912345678}', true, '$2a$10$dTQfxG0LTOx4Pu2nMriftOAv5qxNgd6CCA7V1LhGglxvVW9dJID.m', '1985-03-20', 2, FALSE);

INSERT INTO usuario (nome, cpf, email, telefone, ativo, senha, data_nascimento, endereco_id, eh_admin) VALUES
('Pedro Oliveira', '55555555500', 'pedro@email.com', '{11998765432}', true, '$2a$10$dTQfxG0LTOx4Pu2nMriftOAv5qxNgd6CCA7V1LhGglxvVW9dJID.m', '1992-07-10', 3, FALSE);

INSERT INTO usuario (nome, cpf, email, telefone, ativo, senha, data_nascimento, endereco_id, eh_admin) VALUES
('Ana Costa', '11111111100', 'ana@email.com', '{11911111111}', true, '$2a$10$dTQfxG0LTOx4Pu2nMriftOAv5qxNgd6CCA7V1LhGglxvVW9dJID.m', '1988-11-25', 4, TRUE);

-- ======================
-- LOTES
-- ======================
INSERT INTO lote (nome, descricao, lance_minimo) VALUES
('Lote Tecnologia Premium', 'Notebook de alta performance para trabalho e games', 2500.00);

INSERT INTO lote (nome, descricao, lance_minimo) VALUES
('Lote Smartphones', 'Smartphone topo de linha com excelente câmera', 800.00);

INSERT INTO lote (nome, descricao, lance_minimo) VALUES
('Lote Literatura Clássica', 'Obra prima da literatura brasileira', 50.00);

INSERT INTO lote (nome, descricao, lance_minimo) VALUES
('Lote Mobiliário Conforto', 'Sofá retrátil em perfeito estado', 1200.00);

INSERT INTO lote (nome, descricao, lance_minimo) VALUES
('Lote Arte Contemporânea', 'Obra de arte moderna de qualidade', 300.00);

INSERT INTO lote (nome, descricao, lance_minimo) VALUES
('Lote Joalheria', 'Peça de ouro genuína com certificado', 1500.00);


-- ======================
-- ITENS
-- ======================
INSERT INTO item (nome, descricao, condicao, lote_id) VALUES
('Notebook Dell', 'Notebook Dell Inspiron 15, Intel i7, SSD 512GB, RAM 16GB', 'SEMI_NOVO', 1);

INSERT INTO item (nome, descricao, condicao, lote_id) VALUES
('Smartphone Samsung', 'Samsung Galaxy S21, 256GB, muito bem cuidado', 'SEMI_NOVO', 2);

INSERT INTO item (nome, descricao, condicao, lote_id) VALUES
('Livro Dom Casmurro', 'Livro clássico de Machado de Assis, edição de colecionador', 'NOVO', 3);

INSERT INTO item (nome, descricao, condicao, lote_id) VALUES
('Sofá Retrátil', 'Sofá de 3 lugares em couro cinza, pouco uso', 'SEMI_NOVO', 4);

INSERT INTO item (nome, descricao, condicao, lote_id) VALUES
('Aparelho de Som', 'JBL PartyBox 110, 160W RMS, como novo', 'NOVO', 1);

INSERT INTO item (nome, descricao, condicao, lote_id) VALUES
('Quadro de Arte', 'Quadro abstrato assinado by artista local', 'AVARIADO', 5);

INSERT INTO item (nome, descricao, condicao, lote_id) VALUES
('Anel de Ouro', 'Anel de ouro 18K com diamante, com certificado', 'USADO', 6);

-- ======================
-- RELACIONAMENTO ITEM-CATEGORIA
-- ======================
INSERT INTO item_categoria (item_id, categoria_id) VALUES (1, 1); -- Notebook -> Eletrônicos
INSERT INTO item_categoria (item_id, categoria_id) VALUES (2, 1); -- Smartphone -> Eletrônicos
INSERT INTO item_categoria (item_id, categoria_id) VALUES (3, 2); -- Livro -> Livros
INSERT INTO item_categoria (item_id, categoria_id) VALUES (4, 4); -- Sofá -> Móveis
INSERT INTO item_categoria (item_id, categoria_id) VALUES (5, 1); -- Som -> Eletrônicos
INSERT INTO item_categoria (item_id, categoria_id) VALUES (6, 5); -- Quadro -> Arte
INSERT INTO item_categoria (item_id, categoria_id) VALUES (7, 6); -- Anel -> Joias

-- ======================
-- LEILÕES
-- ======================
-- Leilão pendente (ainda não começou)
INSERT INTO leilao (status, inicio, fim, prazo_pagamento, usuario_id, lote_id) VALUES
(0, '2026-03-20 10:00:00', '2026-03-29 22:00:00', '2026-04-05 23:59:59', 2, 5);

-- Leilão aberto (em andamento)
INSERT INTO leilao (status, inicio, fim, prazo_pagamento, usuario_id, lote_id) VALUES
(1, '2026-03-15 10:00:00', '2026-03-29 22:00:00', '2026-04-05 23:59:59', 1, 1);

INSERT INTO leilao (status, inicio, fim, prazo_pagamento, usuario_id, lote_id) VALUES
(1, '2026-03-16 14:00:00', '2026-03-29 18:00:00', '2026-04-05 23:59:59', 2, 2);

-- Leilão aguardando pagamento (finalizado mas sem pagamento confirmado)
INSERT INTO leilao (status, inicio, fim, prazo_pagamento, usuario_id, lote_id) VALUES
(2, '2026-03-15 10:00:00', '2026-03-15 14:00:00', '2026-04-05 23:59:59', 1, 3);

-- Leilão finalizado
INSERT INTO leilao (status, inicio, fim, prazo_pagamento, usuario_id, lote_id) VALUES
(3, '2026-03-15 10:00:00', '2026-03-15 15:00:00', '2026-04-05 23:59:59', 3, 4);

INSERT INTO leilao (status, inicio, fim, prazo_pagamento, usuario_id, lote_id) VALUES
(4, '2026-03-15 11:00:00', '2026-03-29 19:00:00', '2026-04-05 23:59:59', 3, 6);

-- ======================
-- LANCES (ofertas em leilões)
-- ======================
-- Lances no leilão 2 (Notebook)
INSERT INTO lance (timestamp, valor, lote_id, usuario_id) VALUES
('2026-03-15 12:45:00', 2750.00, 1, 3);

INSERT INTO lance (timestamp, valor, lote_id, usuario_id) VALUES
('2026-03-15 10:15:00', 3000.00, 1, 4);

-- Lances no leilão 3 (Smartphone)
INSERT INTO lance (timestamp, valor, lote_id, usuario_id) VALUES
('2026-03-16 15:00:00', 850.00, 2, 3);

INSERT INTO lance (timestamp, valor, lote_id, usuario_id) VALUES
('2026-03-17 16:30:00', 950.00, 2, 4);

-- Lances no leilão 4 (Livro)
INSERT INTO lance (timestamp, valor, lote_id, usuario_id) VALUES
('2026-03-15 13:00:00', 75.00, 3, 3);

INSERT INTO lance (timestamp, valor, lote_id, usuario_id) VALUES
('2026-03-18 13:30:00', 120.00, 3, 4);

-- Lances no leilão 5 (Sofá)
INSERT INTO lance (timestamp, valor, lote_id, usuario_id) VALUES
('2026-03-15 14:30:00', 12001.00, 4, 1);
--[TODO] ADICIONAR VENDA E PAGAMENTO

-- ======================
-- PAGAMENTOS
-- ======================

-- Pagamento cartão para venda do sofá
INSERT INTO pagamento (id, status, dtype, numero_cartao, nome_titular, bandeira, dia_vencimento, ano_vencimento)
VALUES (1, 1, 'PagamentoCartaoCredito', '4111111111111111', 'João Silva', 1, 12, 2028);

-- ======================
-- VENDAS
-- ======================

-- Venda do Sofá (leilão 5)
INSERT INTO venda (id, valor, metodo_pagamento_id, created_at, updated_at)
VALUES (1, 1201.00, 1, NOW(), NOW());

-- ======================
-- ATUALIZAÇÃO DOS LANCES COM VENDA
-- ======================

-- Associar venda ao lance vencedor do sofá
UPDATE lance
SET venda_id = 1
WHERE timestamp = '2026-03-15 14:30:00'
AND lote_id = 4;