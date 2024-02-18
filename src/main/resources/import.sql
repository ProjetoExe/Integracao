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

INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'PP')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'P')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'M')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'G')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (1, 'GG')

INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '33')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '34')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '35')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '36')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '37')
INSERT INTO classe_grade (classe_id, tamanho) VALUES (2, '38')

INSERT INTO produto (nome, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Prom, ativo, marca_id) VALUES ('Geforce RTX 3060', 7897897897899, 'RTX30', 'Placa de Video', '12GB de RAM 192 Bits 3 Display Port e 1 HDMI', '2023-05-25T18:21:47.741', 4, 0, 2900.00, 2400.00, 'S', 1);
INSERT INTO produto (nome, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Prom, ativo, marca_id) VALUES ('Mouse Logitech G403', 7897897897888, 'G400', 'Mouse', 'Sensor profissional', '2023-05-26T19:21:47.741', 3, 0, 280.00, 235.50, 'S', 2);
INSERT INTO produto (nome, ean, referencia, desc_Curta, desc_Longa, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Prom, tempo_Garantia, msg_Garantia, ativo, marca_id) VALUES ('Placa Mãe ASUS', 7897897897887, 'PLM', 'Placa Mãe', 'TUF GAMING XXX', '2023-05-27T20:21:47.741', 2, 0, 1100.00, 1000.00, '12 meses', 'Troca mediante a apresentação de Nota Fiscal', 'S', 3);
INSERT INTO produto (nome, referencia, desc_Curta, desc_Longa, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Prom, ativo, marca_id) VALUES ('Calça Moletom Preta', 'CMP24', 'Calça', 'Calça Moletom Preta de Algodão', '2023-05-27T20:21:47.741', 2, 0, 249.99, 249.99, 'S', 6);
INSERT INTO produto (nome, referencia, desc_Curta, desc_Longa, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Prom, ativo, marca_id) VALUES ('Camiseta Algodão Branca', 'CA24', 'Camiseta', 'Camiseta de Algodão Branca Verão', '2023-05-27T20:21:47.741', 4, 0, 169.99, 169.99, 'S', 6);
INSERT INTO produto (nome, referencia, desc_Curta, desc_Longa, data_Cadastro, estoque_Total, qtd_vendida, preco, preco_Prom, ativo, marca_id) VALUES ('Bermuda Jean', 'BJ24', 'Bermuda', 'Bermuda Jean Verão', '2023-05-27T20:21:47.741', 4, 0, 139.99, 119.99, 'N', 7);

INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (4, 'P', 7897897890001, 249.99, 249.99, 1);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (4, 'M', 7897897890002, 249.99, 249.99, 1);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (5, 'PP', 7897897890003, 169.99, 169.99, 1);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (5, 'G', 7897897890004, 169.99, 169.99, 3);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (6, 'P', 7897897890005, 139.99, 119.99, 2);
INSERT INTO produto_grade (produto_id, tamanho, ean, preco, preco_Promocional, quantidade_estoque) VALUES (6, 'GG', 7897897890006, 139.99, 139.99, 2);

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