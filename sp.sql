create or replace PROCEDURE CREATEAGREEMENTS
(
    V_CONTRACT_ID   IN VARCHAR2:=NULL,
    V_TITLE      IN VARCHAR2:=NULL,
    V_DESCRIPTION      IN VARCHAR2:=NULL
) AS 

BEGIN

    INSERT INTO CONTRACT
   (contract_id, title, contract_desc)
    VALUES (V_CONTRACT_ID, V_TITLE, V_DESCRIPTION);  
    
 COMMIT;
  
END CREATEAGREEMENTS;


create or replace PROCEDURE GETAGREEMENTS (c1 OUT SYS_REFCURSOR)
AS
BEGIN
  open c1 for
  SELECT contract_id as contractId, title, contract_desc as contractDesc
  FROM AGREEMENTS.contract;
END;
