--DROP TABLE
--  t_periods;

CREATE TABLE
  t_periods (
    label VARCHAR2(100), 
    type VARCHAR2(100), 
    start_date DATE, 
    end_date DATE
  )
--PARALLEL 8
--PCTFREE 0
--COMPRESS FOR QUERY HIGH
/

INSERT INTO t_periods VALUES ('A', '1', to_date('201401', 'YYYYMM'), to_date('201403', 'YYYYMM'));
INSERT INTO t_periods VALUES ('B', '1', to_date('201402', 'YYYYMM'), to_date('201405', 'YYYYMM'));
INSERT INTO t_periods VALUES ('C', '1', to_date('201404', 'YYYYMM'), to_date('201407', 'YYYYMM'));
INSERT INTO t_periods VALUES ('D', '1', to_date('201405', 'YYYYMM'), to_date('201406', 'YYYYMM'));
INSERT INTO t_periods VALUES ('E', '1', to_date('201410', 'YYYYMM'), to_date('201412', 'YYYYMM'));

INSERT INTO t_periods VALUES ('F', '2', to_date('201401', 'YYYYMM'), to_date('201403', 'YYYYMM'));
INSERT INTO t_periods VALUES ('G', '2', to_date('201402', 'YYYYMM'), to_date('201405', 'YYYYMM'));
INSERT INTO t_periods VALUES ('H', '2', to_date('201404', 'YYYYMM'), to_date('201407', 'YYYYMM'));
INSERT INTO t_periods VALUES ('I', '2', to_date('201405', 'YYYYMM'), to_date('201406', 'YYYYMM'));
INSERT INTO t_periods VALUES ('J', '2', to_date('201410', 'YYYYMM'), to_date('201412', 'YYYYMM'));
INSERT INTO t_periods VALUES ('K', '2', to_date('201401', 'YYYYMM'), to_date('201512', 'YYYYMM'));

COMMIT;

SELECT 
  type,
  MIN(start_date) start_date,
  MAX(end_date) end_date
FROM
  (
    SELECT
      type,
      start_date,
      end_date,
      MAX(group_1) OVER(PARTITION BY type ORDER BY start_date ASC) group_2
    FROM
      (
        SELECT 
          type,
          start_date,
          COALESCE(end_date, to_date('99991230', 'YYYYMMDD')) end_date,
          CASE
            WHEN 
                  (start_date NOT BETWEEN MIN(start_date) OVER(PARTITION BY type ORDER BY start_date ASC ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING) AND COALESCE(MAX(end_date) OVER(PARTITION BY type ORDER BY start_date ASC ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING), to_date('99991230', 'YYYYMMDD')))  
               OR LAG(start_date) OVER(PARTITION BY type ORDER BY start_date ASC) IS NULL 
            THEN
              row_number() OVER(PARTITION BY type ORDER BY start_date ASC)
          END group_1
        FROM
          t_periods
      )
  )
GROUP BY
  type,
  group_2;

SELECT DISTINCT
  p1.type, 
  GREATEST(p1.start_date, p2.start_date) start_date, 
  LEAST(p1.end_date, p2.end_date) end_date
FROM
  t_periods p1
JOIN
  t_periods p2
ON
      p1.type = p2.type
WHERE
     p2.start_date < p1.start_date AND p1.start_date < p2.end_date 
  OR p2.start_date < p1.end_date AND p1.end_date < p2.end_date;
