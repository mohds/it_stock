--------------------------------------------------------
--  File created - Tuesday-January-17-2017   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ACCESS_GROUPS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."ACCESS_GROUPS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ACCESS_GROUP_METHODS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."ACCESS_GROUP_METHODS" 
   (	"ID" NUMBER, 
	"ACCESS_GROUP_ID" NUMBER, 
	"METHOD_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ADMINS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."ADMINS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(50 BYTE), 
	"PASSWORD_HASH" VARCHAR2(100 BYTE), 
	"ACCESS_GROUP_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table BRANDS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."BRANDS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table CLIENTS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."CLIENTS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ITEMS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."ITEMS" 
   (	"ID" NUMBER, 
	"LABEL" VARCHAR2(255 BYTE), 
	"LOCATION_ID" NUMBER, 
	"BRAND_ID" NUMBER, 
	"SERIAL_NUMBER" VARCHAR2(255 BYTE), 
	"CONDITION" VARCHAR2(100 BYTE), 
	"NOTES" VARCHAR2(255 BYTE), 
	"AVAILABILITY" VARCHAR2(100 BYTE), 
	"TYPE_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table ITEMSPECVALUES
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."ITEMSPECVALUES" 
   (	"ID" NUMBER, 
	"VALUE" VARCHAR2(50 BYTE), 
	"ITEM_ID" NUMBER, 
	"SPEC_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table LOCATIONS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."LOCATIONS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table METHODS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."METHODS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table RECEIPTS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."RECEIPTS" 
   (	"ID" NUMBER, 
	"CLIENT_ID" NUMBER, 
	"NOTES" VARCHAR2(255 BYTE), 
	"STATUS" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table RECORDS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."RECORDS" 
   (	"ID" NUMBER, 
	"RETURN_TIME" VARCHAR2(50 BYTE), 
	"RETURN_DATE" VARCHAR2(50 BYTE), 
	"BORROW_TIME" VARCHAR2(50 BYTE), 
	"BORROW_DATE" VARCHAR2(50 BYTE), 
	"ADMIN_CHECKER_ID" NUMBER, 
	"ADMIN_RECEIVER_ID" NUMBER, 
	"CLIENT_BORROWER_ID" NUMBER, 
	"CLIENT_RETURNER_ID" NUMBER, 
	"RECEIPT_ID" NUMBER, 
	"ITEM_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SPECS
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."SPECS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(100 BYTE), 
	"UNIT" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table SPECS_TYPES
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."SPECS_TYPES" 
   (	"ID" NUMBER, 
	"SPEC_ID" NUMBER, 
	"TYPE_ID" NUMBER
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table TYPES
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."TYPES" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table TYPES_TYPES
--------------------------------------------------------

  CREATE TABLE "C##ITSTOCK"."TYPES_TYPES" 
   (	"ID" NUMBER, 
	"TYPE_ID_1" NUMBER, 
	"TYPE_ID_2" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "USERS" ;
REM INSERTING into C##ITSTOCK.ACCESS_GROUPS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.ACCESS_GROUP_METHODS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.ADMINS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.BRANDS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.CLIENTS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.ITEMS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.ITEMSPECVALUES
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.LOCATIONS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.METHODS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.RECEIPTS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.RECORDS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.SPECS
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.SPECS_TYPES
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.TYPES
SET DEFINE OFF;
REM INSERTING into C##ITSTOCK.TYPES_TYPES
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Index SPECS_TYPES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."SPECS_TYPES_PK" ON "C##ITSTOCK"."SPECS_TYPES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index RECEIPTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."RECEIPTS_PK" ON "C##ITSTOCK"."RECEIPTS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index LOCATIONS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."LOCATIONS_PK" ON "C##ITSTOCK"."LOCATIONS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index ACCESS_GROUPS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."ACCESS_GROUPS_PK" ON "C##ITSTOCK"."ACCESS_GROUPS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index ITEMS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."ITEMS_PK" ON "C##ITSTOCK"."ITEMS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index TYPES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."TYPES_PK" ON "C##ITSTOCK"."TYPES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index TABLE1_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."TABLE1_PK" ON "C##ITSTOCK"."ADMINS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index BRANDS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."BRANDS_PK" ON "C##ITSTOCK"."BRANDS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index ITEMSPECVALUES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."ITEMSPECVALUES_PK" ON "C##ITSTOCK"."ITEMSPECVALUES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index TYPES_TYPES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."TYPES_TYPES_PK" ON "C##ITSTOCK"."TYPES_TYPES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index METHODS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."METHODS_PK" ON "C##ITSTOCK"."METHODS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index CLIENTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."CLIENTS_PK" ON "C##ITSTOCK"."CLIENTS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SPECS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."SPECS_PK" ON "C##ITSTOCK"."SPECS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index TABLE2_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."TABLE2_PK" ON "C##ITSTOCK"."RECORDS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index ACCESS_GROUP_METHODS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C##ITSTOCK"."ACCESS_GROUP_METHODS_PK" ON "C##ITSTOCK"."ACCESS_GROUP_METHODS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table ADMINS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ADMINS" ADD CONSTRAINT "TABLE1_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."ADMINS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOCATIONS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."LOCATIONS" ADD CONSTRAINT "LOCATIONS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."LOCATIONS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACCESS_GROUPS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ACCESS_GROUPS" ADD CONSTRAINT "ACCESS_GROUPS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."ACCESS_GROUPS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RECEIPTS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."RECEIPTS" ADD CONSTRAINT "RECEIPTS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."RECEIPTS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CLIENTS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."CLIENTS" ADD CONSTRAINT "CLIENTS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."CLIENTS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TYPES
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."TYPES" ADD CONSTRAINT "TYPES_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."TYPES" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SPECS_TYPES
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."SPECS_TYPES" ADD CONSTRAINT "SPECS_TYPES_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."SPECS_TYPES" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BRANDS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."BRANDS" ADD CONSTRAINT "BRANDS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."BRANDS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ITEMSPECVALUES
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ITEMSPECVALUES" ADD CONSTRAINT "ITEMSPECVALUES_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."ITEMSPECVALUES" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RECORDS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."RECORDS" ADD CONSTRAINT "TABLE2_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."RECORDS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ITEMS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ITEMS" ADD CONSTRAINT "ITEMS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."ITEMS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table METHODS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."METHODS" ADD CONSTRAINT "METHODS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."METHODS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACCESS_GROUP_METHODS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ACCESS_GROUP_METHODS" ADD CONSTRAINT "ACCESS_GROUP_METHODS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."ACCESS_GROUP_METHODS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SPECS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."SPECS" ADD CONSTRAINT "SPECS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."SPECS" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TYPES_TYPES
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."TYPES_TYPES" ADD CONSTRAINT "TYPES_TYPES_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "USERS"  ENABLE;
  ALTER TABLE "C##ITSTOCK"."TYPES_TYPES" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ACCESS_GROUP_METHODS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ACCESS_GROUP_METHODS" ADD CONSTRAINT "ACCESS_GROUP_FK" FOREIGN KEY ("ACCESS_GROUP_ID")
	  REFERENCES "C##ITSTOCK"."ACCESS_GROUPS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."ACCESS_GROUP_METHODS" ADD CONSTRAINT "METHOD_FK" FOREIGN KEY ("METHOD_ID")
	  REFERENCES "C##ITSTOCK"."METHODS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ADMINS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ADMINS" ADD CONSTRAINT "USER_ACCESS_GROUP_FK" FOREIGN KEY ("ACCESS_GROUP_ID")
	  REFERENCES "C##ITSTOCK"."ACCESS_GROUPS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ITEMS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ITEMS" ADD CONSTRAINT "ITEM_BRAND_FK" FOREIGN KEY ("BRAND_ID")
	  REFERENCES "C##ITSTOCK"."BRANDS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."ITEMS" ADD CONSTRAINT "ITEM_LOCATION_FK" FOREIGN KEY ("LOCATION_ID")
	  REFERENCES "C##ITSTOCK"."LOCATIONS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."ITEMS" ADD CONSTRAINT "ITEM_TYPE_FK" FOREIGN KEY ("TYPE_ID")
	  REFERENCES "C##ITSTOCK"."TYPES" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ITEMSPECVALUES
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."ITEMSPECVALUES" ADD CONSTRAINT "ITEMSPECVALUE_ITEM_FK" FOREIGN KEY ("ITEM_ID")
	  REFERENCES "C##ITSTOCK"."ITEMS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."ITEMSPECVALUES" ADD CONSTRAINT "ITEMSPECVALUE_SPEC_FK" FOREIGN KEY ("SPEC_ID")
	  REFERENCES "C##ITSTOCK"."SPECS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RECEIPTS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."RECEIPTS" ADD CONSTRAINT "RECEIPT_CLIENT_FK" FOREIGN KEY ("CLIENT_ID")
	  REFERENCES "C##ITSTOCK"."CLIENTS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RECORDS
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."RECORDS" ADD CONSTRAINT "RECORD_BORROWER_FK" FOREIGN KEY ("CLIENT_BORROWER_ID")
	  REFERENCES "C##ITSTOCK"."CLIENTS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."RECORDS" ADD CONSTRAINT "RECORD_CHECKER_FK" FOREIGN KEY ("ADMIN_CHECKER_ID")
	  REFERENCES "C##ITSTOCK"."ADMINS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."RECORDS" ADD CONSTRAINT "RECORD_ITEM_FK" FOREIGN KEY ("ITEM_ID")
	  REFERENCES "C##ITSTOCK"."ITEMS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."RECORDS" ADD CONSTRAINT "RECORD_RECEIPT_FK" FOREIGN KEY ("RECEIPT_ID")
	  REFERENCES "C##ITSTOCK"."RECEIPTS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."RECORDS" ADD CONSTRAINT "RECORD_RECEIVER_FK" FOREIGN KEY ("ADMIN_RECEIVER_ID")
	  REFERENCES "C##ITSTOCK"."ADMINS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."RECORDS" ADD CONSTRAINT "RECORD_RETURNER_FK" FOREIGN KEY ("CLIENT_RETURNER_ID")
	  REFERENCES "C##ITSTOCK"."CLIENTS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SPECS_TYPES
--------------------------------------------------------

  ALTER TABLE "C##ITSTOCK"."SPECS_TYPES" ADD CONSTRAINT "SPEC_FK" FOREIGN KEY ("SPEC_ID")
	  REFERENCES "C##ITSTOCK"."SPECS" ("ID") ENABLE;
  ALTER TABLE "C##ITSTOCK"."SPECS_TYPES" ADD CONSTRAINT "TYPE_FK" FOREIGN KEY ("TYPE_ID")
	  REFERENCES "C##ITSTOCK"."TYPES" ("ID") ENABLE;
