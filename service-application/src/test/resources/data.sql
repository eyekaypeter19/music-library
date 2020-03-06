DROP TABLE IF EXISTS Song;

CREATE TABLE Song (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  song_title VARCHAR(250) NOT NULL,
  song_author VARCHAR(250) NOT NULL,
  date_created DATE NOT NULL
);