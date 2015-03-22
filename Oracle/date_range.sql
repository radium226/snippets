WITH
  days
AS
  (
    SELECT
      dt, 
      DECODE(
        trim(to_char(dt, 'DAY', 'NLS_DATE_LANGUAGE=AMERICAN')), 
        'SUNDAY', 0, 
        'SATURDAY', 0,
        1
      ) working_day
    FROM
      (
        SELECT
          from_dt, 
          trunc(to_dt - LEVEL + 1, 'DD') AS dt, 
          to_dt
        FROM
          (
            SELECT
              :from_dt from_dt, 
              :to_dt to_dt
            FROM
              dual
          )
        WHERE
          to_dt >= from_dt
        CONNECT BY
          from_dt + LEVEL - 1 < to_dt + 1
      )
    ORDER BY
      dt
  ), 
  working_days
AS (
  SELECT
    dt
  FROM
    days
  WHERE
    working_day = 1
), 
  last_working_days
AS (
  SELECT
     dt, 
     DECODE(dt, MAX(dt) OVER(), 1, 0) of_range,
     DECODE(dt, MAX(dt) OVER (PARTITION BY TRUNC(dt, 'MM')), 1, 0) of_month
  FROM
     working_days
)
SELECT
  d.dt, 
  d.working_day, 
  COALESCE(lwd.of_month, 0) last_working_day_of_month, 
  COALESCE(lwd.of_range, 0) last_working_day_of_range
FROM
  days d
LEFT OUTER JOIN
  last_working_days lwd
ON
  lwd.dt = d.dt
ORDER BY
  d.dt ASC
