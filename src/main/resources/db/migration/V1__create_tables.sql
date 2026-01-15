CREATE TABLE IF NOT EXISTS
    addresses
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    street   VARCHAR(255)          NOT NULL,

    city     VARCHAR(255)          NOT NULL,

    zip_code VARCHAR(20)           NOT NULL,

    state    VARCHAR(100)          NOT NULL,

    user_id  BIGINT
                                   NOT NULL,
    CONSTRAINT pk_addresses
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS
    categories
(
    id   TINYINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(100)           NOT NULL,

    CONSTRAINT pk_categories
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS
    order_items
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    order_id   BIGINT                NOT NULL,
    product_id BIGINT                NOT NULL,
    quantity   INT                   NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS
    orders
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    order_number   VARCHAR(100)          NOT NULL,

    status         VARCHAR(50)           NOT NULL,

    payment_method VARCHAR(50)           NOT NULL,

    user_id        BIGINT
                                         NOT NULL,
    CONSTRAINT pk_orders
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS
    products
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NOT NULL,
    `description`
                TEXT                  NULL,

    price       DECIMAL(10, 2)        NOT NULL,

    quantity    INT
                                      NOT NULL
        DEFAULT 0,
    category_id TINYINT
                                      NULL,
    CONSTRAINT pk_products
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS
    rides
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    user_id          BIGINT                NOT NULL,
    driver_id        BIGINT                NULL,
    pickup_location  VARCHAR(255)          NOT NULL,

    dropoff_location VARCHAR(255)          NOT NULL,

    fare             DECIMAL(10, 2)        NOT NULL,

    status           VARCHAR(50)           NOT NULL,

    CONSTRAINT pk_rides
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS
    roles
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50)           NOT NULL,

    CONSTRAINT pk_roles
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS
    user_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE IF NOT EXISTS
    users
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NOT NULL,

    email    VARCHAR(255)          NOT NULL,

    password VARCHAR(255)          NOT NULL,

    CONSTRAINT pk_users
        PRIMARY KEY (id)
);

# Unique Constraints
ALTER TABLE
    categories
    ADD
        CONSTRAINT uc_categories_name
            UNIQUE (name);

ALTER TABLE
    orders
    ADD
        CONSTRAINT uc_orders_order_number
            UNIQUE (order_number);

ALTER TABLE
    roles
    ADD
        CONSTRAINT uc_roles_name
            UNIQUE (name);

ALTER TABLE
    users
    ADD
        CONSTRAINT uc_users_email
            UNIQUE (email);

# Foreign Keys
ALTER TABLE
    addresses
    ADD
        CONSTRAINT FK_ADDRESSES_ON_USER
            FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE
    orders
    ADD
        CONSTRAINT FK_ORDERS_ON_USER
            FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE
    order_items
    ADD
        CONSTRAINT FK_ORDER_ITEMS_ON_ORDER
            FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE
    order_items
    ADD
        CONSTRAINT FK_ORDER_ITEMS_ON_PRODUCT
            FOREIGN KEY (product_id) REFERENCES products (id);
ALTER TABLE
    products
    ADD
        CONSTRAINT FK_PRODUCTS_ON_CATEGORY
            FOREIGN KEY (category_id) REFERENCES categories (id);
ALTER TABLE
    rides
    ADD
        CONSTRAINT FK_RIDES_ON_DRIVER
            FOREIGN KEY (driver_id) REFERENCES users (id);
ALTER TABLE
    rides
    ADD
        CONSTRAINT FK_RIDES_ON_USER
            FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE
    user_roles
    ADD
        CONSTRAINT fk_userol_on_role
            FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE
    user_roles
    ADD
        CONSTRAINT fk_userol_on_user
            FOREIGN KEY (user_id) REFERENCES users (id);


