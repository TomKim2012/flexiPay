
CREATE VIEW [TillGrades] AS
select business_number,
	   dbo.fn_getTillAverage(business_number,'2015-04-01','2015-04-30') as tillAverage,
	   dbo.fn_GetTillRank(business_number,'2015-04-01','2015-04-30') as grade
from TillModel 



select   dbo.fn_getTillAverage('893512','2015-04-01','2015-04-30') as tillAverage
select dbo.fn_GetTillRank('893512','2015-04-01','2015-04-30') as grade

select  SUM(mpesa_amt) from LipaNaMpesaIPN where 
			tstamp>='2015-04-01' and tstamp<='2015-04-30'
			and business_number='893512'
			

select * from [TillGrades]
