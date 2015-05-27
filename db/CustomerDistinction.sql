select COUNT(*) as totalTrxs,SUM(mpesa_amt) as totalSales,COUNT(DISTINCT business_number ) totalMerchants,(SUM(mpesa_amt))/( COUNT(DISTINCT business_number )) as  merchantAverage ,COUNT(DISTINCT mpesa_msisdn) as totalCustomers,(SUM(mpesa_amt))/ (COUNT(DISTINCT mpesa_msisdn)) as customerAvg from LipaNaMpesaIPN where business_number IN ('893512')				

select mpesa_msisdn,COUNT(mpesa_msisdn)as count from LipaNaMpesaIPN where business_number IN ('893512') group by mpesa_msisdn having COUNT(mpesa_msisdn) > 1   --returning customers

select mpesa_msisdn,COUNT(mpesa_msisdn)as count from LipaNaMpesaIPN where business_number IN ('893512') group by mpesa_msisdn having COUNT(mpesa_msisdn) = 1 --new customers
