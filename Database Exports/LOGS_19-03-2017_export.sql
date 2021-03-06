--------------------------------------------------------
--  File created - Sunday-March-19-2017   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table IT_STOCK_LOGS
--------------------------------------------------------

  CREATE TABLE "C##SUPER"."IT_STOCK_LOGS" 
   (	"ID" NUMBER, 
	"DESCRIPTION" VARCHAR2(255 BYTE), 
	"IP" VARCHAR2(20 BYTE), 
	"DATETIME" TIMESTAMP (6), 
	"USERNAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Sequence IT_STOCK_LOGS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "C##SUPER"."IT_STOCK_LOGS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 4761 CACHE 20 NOORDER  NOCYCLE  NOPARTITION ;
REM INSERTING into C##SUPER.IT_STOCK_LOGS
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Index IT_STOCK_LOGS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##SUPER"."IT_STOCK_LOGS_PK" ON "C##SUPER"."IT_STOCK_LOGS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Trigger IT_STOCK_LOGS_TRG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "C##SUPER"."IT_STOCK_LOGS_TRG" 
BEFORE INSERT ON IT_STOCK_LOGS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.ID IS NULL THEN
      SELECT IT_STOCK_LOGS_SEQ.NEXTVAL INTO :NEW.ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/
ALTER TRIGGER "C##SUPER"."IT_STOCK_LOGS_TRG" ENABLE;
--------------------------------------------------------
--  Constraints for Table IT_STOCK_LOGS
--------------------------------------------------------

  ALTER TABLE "C##SUPER"."IT_STOCK_LOGS" ADD CONSTRAINT "IT_STOCK_LOGS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##SUPER"."IT_STOCK_LOGS" MODIFY ("ID" NOT NULL ENABLE);
