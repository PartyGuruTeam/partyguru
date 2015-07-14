CREATE CACHED TABLE PUBLIC.PERSONEN(
    PERSID INT NOT NULL NULL_TO_DEFAULT,
    NAME VARCHAR(255),
    GESCHLECHT CHAR(1),
    EMAIL VARCHAR(255),
    HANDY VARCHAR(255),
    GEBURTSJAHR INT,
    VERFUEGBARKEIT INT
); 
ALTER TABLE PUBLIC.PERSONEN ADD CONSTRAINT PUBLIC.CONSTRAINT_49 PRIMARY KEY(PERSID);          
    
CREATE CACHED TABLE PUBLIC.GELEGENHEITEN(
    GELEGENID INT NOT NULL NULL_TO_DEFAULT,
    ORT VARCHAR(255),
    ART VARCHAR(255),
    ANZPLAETZE INT,
    PERSID INT NOT NULL,
    PID INT NOT NULL
);       
ALTER TABLE PUBLIC.GELEGENHEITEN ADD CONSTRAINT PUBLIC.CONSTRAINT_8 PRIMARY KEY(GELEGENID);   
      
CREATE CACHED TABLE PUBLIC.GAESTELISTE(
    PID INT NOT NULL,
    PERSID INT NOT NULL,
    KOMMT BOOLEAN
);               
ALTER TABLE PUBLIC.GAESTELISTE ADD CONSTRAINT PUBLIC.CONSTRAINT_CC2 PRIMARY KEY(PID, PERSID); 

CREATE CACHED TABLE PUBLIC.PARTY(
    PID INT NOT NULL NULL_TO_DEFAULT,
    NAME VARCHAR(255),
    ZEIT DATETIME,
    ORT VARCHAR(255),
    MOTTO VARCHAR(255)
);             
ALTER TABLE PUBLIC.PARTY ADD CONSTRAINT PUBLIC.CONSTRAINT_4 PRIMARY KEY(PID); 
         
CREATE CACHED TABLE PUBLIC.GELEGEN_NUTZUNG(
    GELEGENID INT NOT NULL,
    PERSID INT NOT NULL
);         
ALTER TABLE PUBLIC.GELEGEN_NUTZUNG ADD CONSTRAINT PUBLIC.CONSTRAINT_3F0 PRIMARY KEY(GELEGENID, PERSID);       

CREATE CACHED TABLE PUBLIC.MATERIALTEMPLATE(
    MTID INT NOT NULL NULL_TO_DEFAULT,
    ART VARCHAR(255),
    NAME VARCHAR(255)
);              
ALTER TABLE PUBLIC.MATERIALTEMPLATE ADD CONSTRAINT PUBLIC.CONSTRAINT_E PRIMARY KEY(MTID);     

CREATE CACHED TABLE PUBLIC.MATERIAL(
    MID INT NOT NULL NULL_TO_DEFAULT,
    MTID INT NOT NULL,
    PID INT NOT NULL,
    PERSID INT NOT NULL,
    ANZAHL INT,
    EINHEIT VARCHAR(255),
    NOTIZ VARCHAR(511)
);        
ALTER TABLE PUBLIC.MATERIAL ADD CONSTRAINT PUBLIC.CONSTRAINT_40 PRIMARY KEY(MID);             
    
CREATE CACHED TABLE PUBLIC.PUTZTEMPLATE(
    PTID INT NOT NULL NULL_TO_DEFAULT,
    ART VARCHAR(255)
);          
ALTER TABLE PUBLIC.PUTZTEMPLATE ADD CONSTRAINT PUBLIC.CONSTRAINT_F PRIMARY KEY(PTID);         
           
CREATE CACHED TABLE PUBLIC.PUTZ(
    RID INT NOT NULL NULL_TO_DEFAULT,
    PTID INT NOT NULL,
    PID INT NOT NULL,
    RAUM VARCHAR(255),
    DAUER INT,
    NOTIZ VARCHAR(511)
);          
ALTER TABLE PUBLIC.PUTZ ADD CONSTRAINT PUBLIC.CONSTRAINT_2 PRIMARY KEY(RID);  

CREATE CACHED TABLE PUBLIC.PUTZ_PERSONEN(
    RID INT NOT NULL,
    PERSID INT NOT NULL
); 
ALTER TABLE PUBLIC.PUTZ_PERSONEN ADD CONSTRAINT PUBLIC.CONSTRAINT_223F PRIMARY KEY(RID, PERSID);              

CREATE CACHED TABLE PUBLIC.TASKKATEGORIE(
    KID INT NOT NULL NULL_TO_DEFAULT,
    KATEGORIE VARCHAR(255)
);    
ALTER TABLE PUBLIC.TASKKATEGORIE ADD CONSTRAINT PUBLIC.CONSTRAINT_B PRIMARY KEY(KID);         

CREATE CACHED TABLE PUBLIC.TASKS(
    TID INT NOT NULL NULL_TO_DEFAULT,
    NAME VARCHAR(255),
    STATUS INT,
    PID INT NOT NULL,
    KID INT NOT NULL
);  
ALTER TABLE PUBLIC.TASKS ADD CONSTRAINT PUBLIC.CONSTRAINT_4B PRIMARY KEY(TID);

ALTER TABLE PUBLIC.PUTZ ADD CONSTRAINT PUBLIC.CONSTRAINT_25A FOREIGN KEY(PID) REFERENCES PUBLIC.PARTY(PID) NOCHECK;           
ALTER TABLE PUBLIC.GELEGENHEITEN ADD CONSTRAINT PUBLIC.CONSTRAINT_86 FOREIGN KEY(PERSID) REFERENCES PUBLIC.PERSONEN(PERSID) NOCHECK;          
ALTER TABLE PUBLIC.GELEGEN_NUTZUNG ADD CONSTRAINT PUBLIC.CONSTRAINT_3 FOREIGN KEY(GELEGENID) REFERENCES PUBLIC.GELEGENHEITEN(GELEGENID) NOCHECK;              
ALTER TABLE PUBLIC.PUTZ_PERSONEN ADD CONSTRAINT PUBLIC.CONSTRAINT_22 FOREIGN KEY(RID) REFERENCES PUBLIC.PUTZ(RID) NOCHECK;    
ALTER TABLE PUBLIC.PUTZ ADD CONSTRAINT PUBLIC.CONSTRAINT_25 FOREIGN KEY(PTID) REFERENCES PUBLIC.PUTZTEMPLATE(PTID) NOCHECK;   
ALTER TABLE PUBLIC.MATERIAL ADD CONSTRAINT PUBLIC.CONSTRAINT_4079 FOREIGN KEY(PID) REFERENCES PUBLIC.PARTY(PID) NOCHECK;      
ALTER TABLE PUBLIC.TASKS ADD CONSTRAINT PUBLIC.CONSTRAINT_4BE8 FOREIGN KEY(KID) REFERENCES PUBLIC.TASKKATEGORIE(KID) NOCHECK; 
ALTER TABLE PUBLIC.GELEGENHEITEN ADD CONSTRAINT PUBLIC.CONSTRAINT_869 FOREIGN KEY(PID) REFERENCES PUBLIC.PARTY(PID) NOCHECK;  
ALTER TABLE PUBLIC.MATERIAL ADD CONSTRAINT PUBLIC.CONSTRAINT_407 FOREIGN KEY(MTID) REFERENCES PUBLIC.MATERIALTEMPLATE(MTID) NOCHECK;          
ALTER TABLE PUBLIC.MATERIAL ADD CONSTRAINT PUBLIC.CONSTRAINT_40795 FOREIGN KEY(PERSID) REFERENCES PUBLIC.PERSONEN(PERSID) NOCHECK;            
ALTER TABLE PUBLIC.TASKS ADD CONSTRAINT PUBLIC.CONSTRAINT_4BE FOREIGN KEY(PID) REFERENCES PUBLIC.PARTY(PID) NOCHECK;          
ALTER TABLE PUBLIC.GAESTELISTE ADD CONSTRAINT PUBLIC.CONSTRAINT_CC FOREIGN KEY(PERSID) REFERENCES PUBLIC.PERSONEN(PERSID) NOCHECK;            
ALTER TABLE PUBLIC.PUTZ_PERSONEN ADD CONSTRAINT PUBLIC.CONSTRAINT_223 FOREIGN KEY(PERSID) REFERENCES PUBLIC.PERSONEN(PERSID) NOCHECK;         
ALTER TABLE PUBLIC.GAESTELISTE ADD CONSTRAINT PUBLIC.CONSTRAINT_C FOREIGN KEY(PID) REFERENCES PUBLIC.PARTY(PID) NOCHECK;      
ALTER TABLE PUBLIC.GELEGEN_NUTZUNG ADD CONSTRAINT PUBLIC.CONSTRAINT_3F FOREIGN KEY(PERSID) REFERENCES PUBLIC.PERSONEN(PERSID) NOCHECK;        
