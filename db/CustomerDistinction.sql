<<<<<<< HEAD
select COUNT(*) as totalTrxs,SUM(mpesa_amt) as totalSales,COUNT(DISTINCT business_number ) totalMerchants,(SUM(mpesa_amt))/( COUNT(DISTINCT business_number )) as  merchantAverage ,COUNT(DISTINCT mpesa_msisdn) as totalCustomers,(SUM(mpesa_amt))/ (COUNT(DISTINCT mpesa_msisdn)) as customerAvg from LipaNaMpesaIPN where business_number IN ('893512')				

select mpesa_msisdn,COUNT(mpesa_msisdn)as count from LipaNaMpesaIPN where business_number IN ('893512') group by mpesa_msisdn having COUNT(mpesa_msisdn) > 1   --returning customers

select mpesa_msisdn,COUNT(mpesa_msisdn)as count from LipaNaMpesaIPN where business_number IN ('893512') group by mpesa_msisdn having COUNT(mpesa_msisdn) = 1 --new customers
=======
select distinct mpesa_msisdn from LipaNaMpesaIPN  --listing of all the customers


select distinct mpesa_msisdn from LipaNaMpesaIPN where business_number = '815918'


select mpesa_msisdn,COUNT(mpesa_msisdn)as count from LipaNaMpesaIPN group by mpesa_msisdn having COUNT(mpesa_msisdn) > 1 --returning customers


select lipa2.mpesa_sender,lipa1.mpesa_msisdn, COUNT(lipa1.mpesa_msisdn)as count, SUM(lipa1.mpesa_amt) as amount from LipaNaMpesaIPN Lipa1 INNER JOIN LipaNaMpesaIPN Lipa2 ON (Lipa1.mpesa_msisdn = Lipa2.mpesa_msisdn)
where lipa1.business_number IN ('893512') group by lipa1.mpesa_msisdn having COUNT(lipa1.mpesa_msisdn) = 1 --new customers

>>>>>>> bb16d67c2f6522009cc7a3cfe4c3fa2e7dd80774
