CREATE TABLE users (
	id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    account_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE user_authority (
	user_id BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    
    PRIMARY KEY(user_id, authority_id),
    
    CONSTRAINT FK_USER
    FOREIGN KEY (user_id)
    REFERENCES users (id)
	ON DELETE CASCADE,
    
	CONSTRAINT FK_AUTHORITY
    FOREIGN KEY (authority_id)
    REFERENCES authorities (id)
);

CREATE TABLE recipe_categories (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE recipes (
	id BIGSERIAL PRIMARY KEY,
	author_id BIGINT NOT NULL,
	category_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    image_url VARCHAR(1000) NOT NULL,
    content VARCHAR(5000) NOT NULL,
    created_date DATE NOT NULL,
    
    CONSTRAINT FK_AUTHOR
    FOREIGN KEY (author_id)
    REFERENCES users (id)
	ON DELETE CASCADE,
	
	CONSTRAINT FK_CATEGORY
    FOREIGN KEY (category_id)
    REFERENCES recipe_categories (id)
	ON DELETE CASCADE
);

CREATE TABLE comments (
	id BIGSERIAL PRIMARY KEY,
	author_id BIGINT NOT NULL,
    recipe_id BIGINT NOT NULL,
    content VARCHAR(5000) NOT NULL,
    created_date DATE NOT NULL,
    
    CONSTRAINT FK_AUTHOR_COMMENT
    FOREIGN KEY (author_id)
    REFERENCES users (id)
	ON DELETE CASCADE,
	
    CONSTRAINT FK_RECIPE
    FOREIGN KEY (recipe_id)
    REFERENCES recipes (id)
	ON DELETE CASCADE
);

CREATE TABLE verification_tokens (
	id BIGSERIAL PRIMARY KEY,
	token VARCHAR(255) NOT NULL,
	user_id BIGINT NOT NULL,
	expiry_date DATE NOT NULL,
	
	CONSTRAINT FK_USER
	FOREIGN KEY (user_id)
	REFERENCES users (id)
	ON DELETE CASCADE
);

INSERT INTO users (username, password, email, account_non_expired, account_non_locked, credentials_non_expired, enabled) 
VALUES ('user', '$2a$10$Uh3z07B1pbDpBL./XI6BE.0jU3jDO0wIjBDrIexaJEvB9maKfFIG6', 'user@gmail.com', TRUE, TRUE, TRUE, TRUE);
INSERT INTO users (username, password, email, account_non_expired, account_non_locked, credentials_non_expired, enabled) 
VALUES ('admin', '$2a$10$RLKdpum8GJtDPqtEWTd7cOLf.CaBETWWXEcNyUKNqI6uxQ2pOC9Ry', 'admin@gmail.com', TRUE, TRUE, TRUE, TRUE);

INSERT INTO authorities (name) VALUES ('ROLE_USER');
INSERT INTO authorities (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1,1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2,2);

INSERT INTO recipe_categories (name) VALUES ('Ciasto');
INSERT INTO recipe_categories (name) VALUES ('Potrawa mięsna');
INSERT INTO recipe_categories (name) VALUES ('Sałatka');

INSERT INTO recipes (author_id, category_id, title, image_url, content, created_date) 
VALUES (1, 1, 'Szarlotka', 'https://f.wszelkieprzepisy.pl/images/gqk48zbj/szarlotka-domowa-details.png', 'Cudowny przepis na cudowną szarlotkę', '2021-02-24');
INSERT INTO recipes (author_id, category_id, title, image_url, content, created_date) 
VALUES (1, 3, 'Sałatka z pomidorem', 'https://static.gotujmy.pl/ZDJECIE_PRZEPISU_ETAP/salatka-z-rzodkiewka-salata-pomidorem-kukurydza-506760.jpg', 'Cudowny przepis na cudowną sałatkę z pomidorami', '2021-02-21');
INSERT INTO recipes (author_id, category_id, title, image_url, content, created_date) 
VALUES (1, 2, 'Kotelciki z kurczaka', 'https://kulinarnapolska.org/wp-content/uploads/2020/05/przepis-na-kotlety-warzywne-z-kurczakiem-5-1.jpg', 'Cudowny przepis na cudowne koteliciki z kurczaka', '2021-02-26');
INSERT INTO recipes (author_id, category_id, title, image_url, content, created_date) 
VALUES (1, 3, 'Sałatka z kukurydzą', 'https://www.magazynkuchenny.com/wp-content/uploads/2015/09/salatka-z-kukurydzy-z-kolendra.jpg', 'Cudowny przepis na cudowną sałatkę z kukurydzą', '2021-02-24');
INSERT INTO recipes (author_id, category_id, title, image_url, content, created_date) 
VALUES (1, 1, 'Nugetsy', 'https://bi.im-g.pl/im/26/52/17/z24456998AMP,Nuggetsy-z-kurczaka-to-typowe-danie-fast-food-uwie.jpg', 'Cudowny przepis na cudowną nugetsy', '2021-02-25');

INSERT INTO comments (author_id, recipe_id, content, created_date)
VALUES (1, 1, 'Super przepisik', '2021-02-25');

INSERT INTO comments (author_id, recipe_id, content, created_date)
VALUES (1, 1, 'Muszę spróbować koniecznie!', '2021-02-25');
