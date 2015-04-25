select DISTINCT(transactions.business_number),tills.ownerId,users.phone from LipaNaMpesaIPN as transactions
Inner Join TillModel as tills ON (transactions.business_number=tills.business_number)
Inner Join BUser as users ON (tills.ownerId=users.userId)
where 
DATEPART(YYYY,tstamp)='2015' and 
DATEPART(MM,tstamp)='01' and 
DATEPART(DD,tstamp)='02'


select DISTINCT(transactions.business_number),SUM(mpesa_amt),tills.ownerId,users.phone,users.firstName, 
clientdoc.clientcode 
from LipaNaMpesaIPN as transactions Inner Join TillModel as tills
 ON (transactions.business_number=tills.business_number) 
 Inner Join BUser as users ON (tills.ownerId=users.userId)
 Inner Join MergeFinals.dbo.clientdoc as clientdoc ON(tills.business_number=clientdoc.docnum) 
 
 where DATEPART(YYYY,tstamp)='2015' and DATEPART(MM,tstamp)='01' and DATEPART(DD,tstamp)='02'

