INSERT INTO lojas (razao_Social, nome_Fantasia, cnpj, inscricao_Estadual, email, celular, telefone, cep, endereco, numero, bairro, cidade, estado, pais) VALUES ('PROJETO EXE LTDA', 'PROJETO EXE', '20214754000151', '529971505286', 'projeto.exe123@gmail.com', '16 99723-4150', '16 9723-4150', '14804-072','Rua Benedito Barbosa', '101', 'Vale do Sol', 'Araraquara', 'SP', 'Brasil');

INSERT INTO cliente (nome, cpf, rg, celular, telefone, email, cep, endereco, numero, bairro, cidade, estado, pais) VALUES ('Gustavo', '483.366.048-20', '55.040.121-0', '16 99723-4150', '16 9723-4150', 'gustavoventura@gmail.com', '14804-072', 'Rua Benedito Barbosa', '101', 'Vale do Sol', 'Araraquara', 'SP', 'Brasil');
INSERT INTO cliente (nome, cpf, rg, celular, telefone, email, cep, endereco, numero, bairro, cidade, estado, pais) VALUES ('Aparecido', '936.189.728-49', '09.656.718-1', '16 98100-7730', '16 8100-7730', 'aparecido854@gmail.com', '14804-072', 'Rua Benedito Barbosa', '101', 'Vale do Sol', 'Araraquara', 'SP', 'Brasil');

INSERT INTO categoria (nome) VALUES ('Hardware');
INSERT INTO categoria (nome) VALUES ('Perifericos');
INSERT INTO categoria (nome) VALUES ('Computadores');
INSERT INTO categoria (nome) VALUES ('Games');
INSERT INTO categoria (nome) VALUES ('Monitor');

INSERT INTO marca (nome) VALUES ('GALAX');
INSERT INTO marca (nome) VALUES ('LOGITECH');
INSERT INTO marca (nome) VALUES ('ASUS');
INSERT INTO marca (nome) VALUES ('GIGABYTE');
INSERT INTO marca (nome) VALUES ('ZOTAC');
INSERT INTO marca (nome) VALUES ('KINGSTON');
INSERT INTO marca (nome) VALUES ('MSI');

INSERT INTO produto (nome, referencia, descricao_Curta, descricao_Completa, data_Cadastro, ativo, marca_id) VALUES ('Geforce RTX 3060', 'RTX30', 'Placa de Video', '12GB de RAM 192 Bits 3 Display Port e 1 HDMI', '2023-05-25T18:21:47.741', 'S', 1);
INSERT INTO produto (nome, referencia, descricao_Curta, descricao_Completa, data_Cadastro, ativo, marca_id) VALUES ('Mouse Logitech G403', 'G400', 'Mouse', 'Sensor profissional', '2023-05-26T19:21:47.741', 'S', 2);
INSERT INTO produto (nome, descricao_Curta, descricao_Completa, data_Cadastro, ativo, marca_id) VALUES ('Placa Mãe ASUS', 'Placa Mãe', 'TUF GAMING XXX', '2023-05-27T20:21:47.741', 'S', 3);
INSERT INTO produto (nome, descricao_Curta, descricao_Completa, data_Cadastro, ativo, marca_id) VALUES ('Memória RAM Kingston 16GB', 'Memória RAM', 'C16, 3666MHz', '2023-05-27T20:21:47.741', 'S', 6);
INSERT INTO produto (nome, descricao_Curta, descricao_Completa, data_Cadastro, ativo, marca_id) VALUES ('SDD Kingston 256GB', 'SSD', 'SSD 500MB/s Leitura / 450MB/s Escrita', '2023-05-27T20:21:47.741', 'S', 6);
INSERT INTO produto (nome, descricao_Curta, descricao_Completa, data_Cadastro, ativo, marca_id) VALUES ('Monitor ', 'Placa Mãe', 'TUF GAMING XXX', '2023-05-27T20:21:47.741', 'N', 7);

INSERT INTO produto_grade (produto_id, tamanho, codigo_de_barra, preco_vista, preco_prazo) VALUES (1, 'U', '7897897897899', 2400.00, 2900.00);
INSERT INTO produto_grade (produto_id, tamanho, codigo_de_barra, preco_vista, preco_prazo) VALUES (1, 'U2', '7897897897999', 2500.00, 3000.00);
INSERT INTO produto_grade (produto_id, tamanho, codigo_de_barra, preco_vista, preco_prazo) VALUES (2, 'U', '7897897897888', 235.50, 280.00);
INSERT INTO produto_grade (produto_id, tamanho, codigo_de_barra, preco_vista, preco_prazo) VALUES (3, 'U', '7897897897888', 1000.00, 1100.00);

INSERT INTO produto_categoria (produto_id, categoria_id) values (1,1);
INSERT INTO produto_categoria (produto_id, categoria_id) values (2,2);
INSERT INTO produto_categoria (produto_id, categoria_id) values (3,1);
INSERT INTO produto_categoria (produto_id, categoria_id) values (4,3);
INSERT INTO produto_categoria (produto_id, categoria_id) values (4,5);
INSERT INTO produto_categoria (produto_id, categoria_id) values (5,1);
INSERT INTO produto_categoria (produto_id, categoria_id) values (6,1);

INSERT INTO venda (data_Venda, venda_Status, cliente_id, frete, desconto, sub_total, total) VALUES ('2023-05-05T20:50:15Z', 1, 1, 10.00, 5.00, 2625.50, 2630.50);
INSERT INTO venda_itens (venda_id, produto_id, quantidade, preco, desconto, sub_total, total) VALUES (1, 1, 1, 2400.00, 0.00, 2400.00, 2400.00);
INSERT INTO venda_itens (venda_id, produto_id, quantidade, preco, desconto, sub_total, total) VALUES (1, 2, 1, 235.50, 10.00, 225.50, 235.50);

INSERT INTO venda (data_Venda, venda_Status, cliente_id, frete, desconto, sub_total, total) VALUES ('2023-06-03T15:01:15-03:00', 2, 2, 0.00, 50.00, 1000.00, 950.00);
INSERT INTO venda_itens (venda_id, produto_id, quantidade, preco, desconto, sub_total, total) VALUES (2, 3, 1, 1000.00, 0.00, 1000.00, 1000.00);

INSERT INTO pagamento (venda_id, data_Pagamento) VALUES (2, '2023-06-03T15:01:27Z')
