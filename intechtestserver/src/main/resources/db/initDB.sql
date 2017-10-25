DROP TABLE IF EXISTS users_content;
DROP TABLE IF EXISTS transitions;
DROP TABLE IF EXISTS states;
DROP TABLE IF EXISTS contents;

CREATE TABLE states (
  id        SERIAL PRIMARY KEY,
  name      VARCHAR NOT NULL UNIQUE,
  message   VARCHAR NOT NULL,
  init_flag BOOLEAN NOT NULL
);

CREATE TABLE transitions (
  id        SERIAL PRIMARY KEY,
  event     VARCHAR NOT NULL,
  source_id INTEGER NOT NULL,
  target_id INTEGER NOT NULL,
  action    VARCHAR NOT NULL,
  CONSTRAINT event_source_idx UNIQUE (event, source_id),
  FOREIGN KEY (source_id) REFERENCES states (id),
  FOREIGN KEY (target_id) REFERENCES states (id)
);

CREATE TABLE contents (
  id       SERIAL PRIMARY KEY,
  title    VARCHAR NOT NULL UNIQUE,
  body     VARCHAR NOT NULL,
  category VARCHAR NOT NULL,
  duration INTEGER NOT NULL
);

CREATE TABLE users_content (
  user_id    INTEGER NOT NULL,
  content_id INTEGER NOT NULL,
  CONSTRAINT users_content_idx UNIQUE (user_id, content_id),
  FOREIGN KEY (content_id) REFERENCES contents (id) ON DELETE CASCADE
);