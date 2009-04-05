
 create table U_TPL_TEMPLATE(
        UUID VARCHAR(36) not null,
       PID VARCHAR(36),
       NODENAME VARCHAR(128),
       NODECODE VARCHAR(128),
       TABLENAME VARCHAR(128),
       ALLOWVIEW VARCHAR(128),
       NODEDESC VARCHAR(512),
       NODETYPE VARCHAR(128),
       NODELEVEL VARCHAR(32),
       STATE VARCHAR(32),
        primary key (UUID)
    );


create table U_TPL_TEMPLATEDATA(
        UUID VARCHAR(36) not null,
       CID VARCHAR(36),
       DATANAME VARCHAR(128),
       DATADCODE VARCHAR(128),
       DATATYPE VARCHAR(128),
       DATALENGTH VARCHAR(32),
       ISINDEX VARCHAR(16),
       ISNOTNULL VARCHAR(16),
       ISUNIQUE VARCHAR(16),
       ISPK VARCHAR(16),
       GENERATOR VARCHAR(128),
       NODEDESC VARCHAR(512),
       STATE VARCHAR(32),
        primary key (UUID)
    );
    
 create table U_TPL_TEMPLATEVIEW(
        UUID VARCHAR(36) not null,
       CID VARCHAR(36),
       CPID VARCHAR(36),
       FIELDNAME VARCHAR(128),
       FIELDCODE VARCHAR(128),
       FIELDTYPE VARCHAR(128),
       FIELDORDER VARCHAR(128),
       DEFAULTVALUE VARCHAR(256),
       REFERENCE VARCHAR(128),
       VALIDATOR VARCHAR(256),
       FIELDLENGTH VARCHAR(32),
       FIELDDESC VARCHAR(512),
       STATE VARCHAR(32),
       primary key (UUID)
    );



