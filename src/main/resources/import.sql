INSERT INTO lojas (razao_Social, nome_Fantasia, cnpj, inscricao_Estadual, email, celular, telefone, cep, endereco, numero, bairro, cidade, estado, pais) VALUES ('PROJETO EXE LTDA', 'PROJETO EXE', '20214754000151', '529971505286', 'projeto.exe123@gmail.com', '16 99723-4150', '16 9723-4150', '14804-072','Rua Benedito Barbosa', '101', 'Vale do Sol', 'Araraquara', 'SP', 'Brasil');

INSERT INTO cliente (nome, cpf, rg, celular, telefone, email, cep, endereco, numero, bairro, cidade, estado, pais) VALUES ('Gustavo', '483.366.048-20', '55.040.121-0', '16 99723-4150', '16 9723-4150', 'gustavoventura@gmail.com', '14804-072', 'Rua Benedito Barbosa', '101', 'Vale do Sol', 'Araraquara', 'SP', 'Brasil');

INSERT INTO categoria (nome) VALUES ('Placa de Video');
INSERT INTO categoria (nome) VALUES ('Perifericos');

INSERT INTO produto (nome, descricao_Curta, descricao_Completa, img_Url, ativo) VALUES ('Geforce RTX 3060', 'Placa de Video', '12GB de RAM 192 Bits 3 Display Port e 1 HDMI', '', 'S');
INSERT INTO produto (nome, descricao_Curta, descricao_Completa, img_Url, ativo) VALUES ('Mouse Logitech G403', 'Mouse', 'Sensor profissional', '', 'S');

INSERT INTO produto_grade (produto_id, tamanho, referencia, codigo_de_barra, preco_vista, preco_prazo) VALUES (1, 'U', 'RTX30', '7897897897899', 2400.00, 2900.00)
INSERT INTO produto_grade (produto_id, tamanho, referencia, codigo_de_barra, preco_vista, preco_prazo) VALUES (1, 'U2', 'RTX30', '7897897897999', 2500.00, 3000.00)
INSERT INTO produto_grade (produto_id, tamanho, referencia, codigo_de_barra, preco_vista, preco_prazo) VALUES (2, 'U', '', '7897897897888', 235.50, 280.00)

INSERT INTO produto_categoria (produto_id, categoria_id) values (1,1);
INSERT INTO produto_categoria (produto_id, categoria_id) values (2,2);

INSERT INTO venda (data_Venda, venda_Status, cliente_id, frete, desconto, total) VALUES ('2023-05-05T20:50:15Z', 1, 1, 10.00, 5.00, 2405.00);

INSERT INTO venda_itens (venda_id, produto_id, quantidade, preco, desconto, sub_total) VALUES (1, 1, 1, 2400.00, 0.00, 2400.00);
INSERT INTO venda_itens (venda_id, produto_id, quantidade, preco, desconto, sub_total) VALUES (1, 2, 1, 235.50, 10.00, 235.50);