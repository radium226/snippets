CREATE TABLE
  snippet (
  	text VARCHAR2(10)
  );

DECLARE
  v_cursor NUMBER;
  v_sql VARCHAR2(32767) := 'INSERT INTO $$TABLE$$(text) VALUES (:text)';
  v_table VARCHAR2(30) := 'SNIPPET';
  v_value VARCHAR2(10) := 'Hello! ';
  v_row_count NUMBER;
BEGIN
  v_sql := REPLACE(v_sql, '$$TABLE$$', v_table);
  
  v_cursor := dbms_sql.open_cursor;
  dbms_sql.parse(v_cursor, v_sql, dbms_sql.NATIVE);
  dbms_sql.bind_variable(v_cursor, ':text', v_value);
  v_row_count := DBMS_SQL.EXECUTE(v_Cursor);
  dbms_output.put_line('row_count = ' || v_row_count);
  dbms_sql.close_cursor(v_cursor);
END;
/

SELECT
  text
FROM
  snippet;
