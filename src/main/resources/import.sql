INSERT INTO lojas (razao_Social, nome_Fantasia, cnpj, inscricao_Estadual, email, celular, telefone, cep, endereco, numero, bairro, cidade, estado, pais) VALUES ('PROJETO EXE LTDA', 'PROJETO EXE', '20214754000151', '529971505286', 'projeto.exe123@gmail.com', '16 99723-4150', '16 9723-4150', '14804-072','Rua Benedito Barbosa', '101', 'Vale do Sol', 'Araraquara', 'SP', 'Brasil');

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

INSERT INTO classe (nome) VALUES ('LETRA')
INSERT INTO classe (nome) VALUES ('NUMERO')
INSERT INTO classe (nome) VALUES ('KILO')

INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'U')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'P')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'PP')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'M')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'G')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'GG')

INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '33')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '34')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '35')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '36')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '37')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '38')

INSERT INTO produto (nome, referencia, descricao_Curta, descricao, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Promocional, ativo, marca_id, classe_id) VALUES ('Geforce RTX 3060', 'RTX30', 'Placa de Video', '12GB de RAM 192 Bits 3 Display Port e 1 HDMI', '2023-05-25T18:21:47.741', 4, 0, 2900.00, 2400.00, 'S', 1, 1);
INSERT INTO produto (nome, referencia, descricao_Curta, descricao, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Promocional, ativo, marca_id, classe_id) VALUES ('Mouse Logitech G403', 'G400', 'Mouse', 'Sensor profissional', '2023-05-26T19:21:47.741', 3, 0, 280.00, 235.50, 'S', 2, 1);
INSERT INTO produto (nome, referencia, descricao_Curta, descricao, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Promocional, tempo_Garantia, msg_Garantia, ativo, marca_id) VALUES ('Placa Mãe ASUS', 'PLM', 'Placa Mãe', 'TUF GAMING XXX', '2023-05-27T20:21:47.741', 2, 0, 1100.00, 1000.00, '12 meses', 'Troca mediante a apresentação de Nota Fiscal', 'S', 3);
INSERT INTO produto (nome, descricao_Curta, descricao, data_Cadastro, qtd_vendida, ativo, marca_id) VALUES ('Memória RAM Kingston 16GB', 'Memória RAM', 'C16, 3666MHz', '2023-05-27T20:21:47.741', 0, 'S', 6);
INSERT INTO produto (nome, descricao_Curta, descricao, data_Cadastro, qtd_vendida, ativo, marca_id) VALUES ('SDD Kingston 256GB', 'SSD', 'SSD 500MB/s Leitura / 450MB/s Escrita', '2023-05-27T20:21:47.741', 0, 'S', 6);
INSERT INTO produto (nome, descricao_Curta, descricao, data_Cadastro, qtd_vendida, ativo, marca_id) VALUES ('Monitor ', 'Monitor Gamer', '144hz 1MS', '2023-05-27T20:21:47.741', 0, 'N', 7);

INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (1, 'U', 7897897897899, 2900.00, 2400.00, 1);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (1, 'U2', 7895553397999, 3000.00, 2500.00, 3);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (2, 'U', 7897897897888, 280.00, 235.50, 3);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (3, 'U', 7897897897888, 1100.00, 1000.00, 2);

INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (1,1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (2,2);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (3,1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (4,3);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (4,5);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (5,1);
INSERT INTO produto_categoria (produto_id, categoria_id) VALUES (6,1);

INSERT INTO produto_imagem (produto_id, img_Url, titulo) VALUES (1, 'http:://www.site.com.br/teste1.jpg', 'Imagem do Produto tamanho U')
INSERT INTO produto_imagem (produto_id, img_Url, titulo) VALUES (1, 'http:://www.site.com.br/teste2.jpg', 'Imagem do Produto tamanho U2')

INSERT INTO cliente (nome_cliente, data_nascimento, cpf, celular, total_pedidos) VALUES ('Gustavo', '2001-10-04', '000.000.000-00', '16 99723 4150', 0)
INSERT INTO endereco (cep, endereco, numero, complemento, bairro, cidade, estado, pais, cliente_id) VALUES ('14800111', 'Rua Teste', '100', 'Teste', 'Bairro Teste', 'Arapica', 'SP', 'BRA', 1)