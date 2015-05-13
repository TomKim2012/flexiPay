select COUNT(*) from LipaNaMpesaIPN				--total number of transactions

select SUM(mpesa_amt) from LipaNaMpesaIPN		--total amount of sales

Select COUNT(DISTINCT mpesa_acc )from LipaNaMpesaIPN			--total number of merchants

select (SUM(mpesa_amt))/( COUNT(DISTINCT mpesa_acc ))from LipaNaMpesaIPN	--merchant average

select COUNT(DISTINCT mpesa_msisdn)from LipaNaMpesaIPN				--number of customers

select (SUM(mpesa_amt))/ (COUNT(DISTINCT mpesa_msisdn))from LipaNaMpesaIPN			--customer average
 
select * from LipaNaMpesaIPN

