insert into usuario (id, login, senha, nome, email, tipo, tentativas_login_invalido) values (1, 'admin','c6zZpZchMLdQZsgllaH64w==', 'Administrador', 'admin@email.com', 'ADMIN', 0);
insert into usuario (id, login, senha, nome, email, tipo, tentativas_login_invalido) values (2, 'usuario','QiVgyySWSwSDtwOkK811AA==', 'Usuario', 'usuario@email.com', 'USER', 0);
insert into usuario (id, login, senha, nome, email, tipo, tentativas_login_invalido) values (3, 'codigoalvo','hdBNkyS7WX79G4AK2Kdulw==', 'codigoalvo', 'codigoalvo@gmail.com', 'ADMIN', 0);
insert into usuario (id, login, senha, nome, email, tipo, tentativas_login_invalido) values (4, 'teste','maKdyBBf0vo52M3ARzOTjQ==', 'Teste', 'teste@email.com', 'USER', 0);
/*alter sequence SEQ_USUARIO_ID restart with 5;*/
insert into categoria(id, nome) values (1, 'Diversos');
insert into categoria(id, nome) values (2, 'Moradia');
insert into categoria(id, nome) values (3, 'Alimentação');
insert into categoria(id, nome) values (4, 'Transporte');
insert into categoria(id, nome) values (5, 'Saúde');
insert into categoria(id, nome) values (6, 'Investimentos');
insert into categoria(id, nome) values (7, 'Hobbies');
insert into categoria(id, nome) values (8, 'Lazer');
/*alter sequence SEQ_CATEGORIA_ID restart with 9;*/
insert into pagamento(id, codigo, nome, tipo) values (1, 'DINHEIRO', 'Dinheiro', 'D');
insert into pagamento(id, codigo, nome, tipo) values (2, 'DEBITO', 'Débito Banco', 'B');
insert into pagamento(id, codigo, nome, tipo, dia_fechamento, dia_pagamento) values (3, 'CCMCBCO', 'Cartão Master Banco', 'C', 26, 5);
insert into pagamento(id, codigo, nome, tipo, dia_fechamento, dia_pagamento) values (4, 'CCVILJ', 'Cartão Visa Loja', 'C', 24, 3);