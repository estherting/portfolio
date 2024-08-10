DROP TABLE IF EXISTS "users";

CREATE TABLE "users" (
    "id" bigint DEFAULT nextval('users_id_seq') NOt NULL,
    "name" text,
    "email" text,
    CONSTRAINT "users_pkey" PRIMARY KEY ("id")
);