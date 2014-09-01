CREATE OR REPLACE PACKAGE date_util_pkg AS

  TYPE day_typ IS RECORD(
    day_date    DATE,
    working_day INTEGER);

  TYPE day_tab_typ IS TABLE OF day_typ;

  TYPE hh24_mi_typ IS RECORD(
    hh24_mi DATE);

  TYPE hh24_mi_tab_typ IS TABLE OF hh24_mi_typ;

  FUNCTION all_days_between(in_first_date IN DATE,
                            in_last_date  IN DATE) RETURN day_tab_typ
    PIPELINED;

  FUNCTION all_minutes_of(in_day_date IN DATE) RETURN hh24_mi_tab_typ
    PIPELINED;

END;
/
CREATE OR REPLACE PACKAGE BODY date_util_pkg AS

  FUNCTION all_days_between(in_first_date IN DATE,
                            in_last_date  IN DATE) RETURN day_tab_typ
    PIPELINED IS
    v_day day_typ;
    CURSOR v_cursor IS
      WITH days AS
       (SELECT day_date,
               decode(TRIM(to_char(day_date, 'DAY', 'NLS_DATE_LANGUAGE=AMERICAN')), 'SUNDAY', 0, 'SATURDAY', 0, 1) working_day
        FROM   (SELECT first_date,
                       trunc(last_date - LEVEL + 1, 'DD') AS day_date,
                       last_date
                FROM   (SELECT in_first_date first_date,
                               in_last_date  last_date
                        FROM   dual)
                WHERE  last_date >= first_date
                CONNECT BY first_date + LEVEL - 1 < last_date + 1)
        ORDER  BY day_date),
      working_days AS
       (SELECT day_date FROM days WHERE working_day = 1),
      last_working_days AS
       (SELECT day_date,
               decode(day_date, MAX(day_date) over(), 1, 0) of_range,
               decode(day_date, MAX(day_date)
                       over(PARTITION BY trunc(day_date, 'MM')), 1, 0) of_month
        FROM   working_days)
      SELECT d.day_date, d.working_day --,
      --coalesce(lwd.of_month, 0) last_working_day_of_month,
      --coalesce(lwd.of_range, 0) last_working_day_of_range
      FROM   days d
      LEFT   OUTER JOIN last_working_days lwd
      ON     lwd.day_date = d.day_date
      ORDER  BY d.day_date ASC;
  BEGIN
    OPEN v_cursor;
    LOOP
      FETCH v_cursor
        INTO v_day;
      EXIT WHEN v_cursor%NOTFOUND;
      PIPE ROW(v_day);
    END LOOP;
    CLOSE v_cursor;
  EXCEPTION
    WHEN OTHERS THEN
      IF v_cursor%ISOPEN THEN
        CLOSE v_cursor;
      END IF;
      RAISE;
  END;

  FUNCTION all_minutes_of(in_day_date IN DATE) RETURN hh24_mi_tab_typ
    PIPELINED IS
    v_hh24_mi hh24_mi_typ;
    CURSOR v_cursor IS
      SELECT day_date + 1 - LEVEL / (24 * 60) hh24_mi
      FROM   (SELECT trunc(in_day_date, 'DD') day_date FROM dual)
      WHERE  day_date + 1 >= day_date
      CONNECT BY day_date + (LEVEL) / (24 * 60) <
                 day_date + 1 + 1 / (24 * 60)
      ORDER  BY hh24_mi ASC;
  BEGIN
    OPEN v_cursor;
    LOOP
      FETCH v_cursor
        INTO v_hh24_mi;
      EXIT WHEN v_cursor%NOTFOUND;
      PIPE ROW(v_hh24_mi);
    END LOOP;
    CLOSE v_cursor;
  EXCEPTION
    WHEN OTHERS THEN
      IF v_cursor%ISOPEN THEN
        CLOSE v_cursor;
      END IF;
      RAISE;
  END;

END;
/
