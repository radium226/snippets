CREATE TABLE
  t_engines (
    engine_name VARCHAR2(100), 
    engine_size NUMBER, 
    engine_type VARCHAR2(100) -- GAZOLINE, DIESEL, ELECTRIC
  );

CREATE TABLE
  t_engine_tax_rules (
    engine_size NUMBER, 
    engine_type VARCHAR(100),
  	tax_eur NUMBER
  );

INSERT INTO t_engines VALUES ('Model 1', 250, 'DIESEL');
INSERT INTO t_engines VALUES ('Model 2', 250, 'GAZOLINE');
INSERT INTO t_engines VALUES ('Model 3', 250, 'ELECTRIC');
INSERT INTO t_engines VALUES ('Model 4', 500, 'DIESEL');
INSERT INTO t_engines VALUES ('Model 5', 500, 'GAZOLINE');
INSERT INTO t_engines VALUES ('Model 6', 500, 'ELECTRIC');
INSERT INTO t_engines VALUES ('Model 7', 750, 'DIESEL');

INSERT INTO t_engine_tax_rules VALUES (250, 'ELECTRIC', 10);
INSERT INTO t_engine_tax_rules VALUES (500, 'ELECTRIC', 10);
INSERT INTO t_engine_tax_rules VALUES (250, NULL, 100);
INSERT INTO t_engine_tax_rules VALUES (500, NULL, 200);
INSERT INTO t_engine_tax_rules VALUES (NULL, NULL, 500);

COMMIT;

SELECT
  v.engine_name, 
  v.engine_size, 
  v.engine_type, 
  v.tax_eur
FROM
  (
    SELECT
      e.engine_name, 
      
      e.engine_size, 
      DECODE(r.engine_size, NULL, 0, 1) engine_size_score, 
      MAX(DECODE(r.engine_size, NULL, 0, 1)) OVER (PARTITION BY e.engine_size, e.engine_type) engine_size_best_score, 
      
      e.engine_type, 
      DECODE(r.engine_type, NULL, 0, 1) engine_type_score, 
      MAX(DECODE(r.engine_type, NULL, 0, 1)) OVER (PARTITION BY e.engine_size, e.engine_type) engine_type_best_score, 
      
      r.tax_eur
    FROM
      t_engines e
    JOIN
      t_engine_tax_rules r
    ON
          COALESCE(r.engine_size, e.engine_size) = e.engine_size
      AND COALESCE(r.engine_type, e.engine_type) = e.engine_type
  ) v
WHERE
      v.engine_size_score = v.engine_size_best_score
  AND v.engine_type_score = v.engine_type_best_score
ORDER BY
  v.engine_name;

DROP TABLE t_engine_tax_rules;
DROP TABLE t_engines;
