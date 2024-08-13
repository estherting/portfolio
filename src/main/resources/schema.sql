DROP TABLE IF EXISTS app_user;

DROP SEQUENCE IF EXISTS app_user_id_seq;
CREATE SEQUENCE app_user_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE app_user (
    "id" bigint DEFAULT nextval('app_user_id_seq') NOT NULL,
    "name" text,
    "email" text,
    CONSTRAINT "app_user_pkey" PRIMARY KEY ("id")
);