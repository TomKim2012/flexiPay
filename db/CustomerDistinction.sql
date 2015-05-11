select distinct mpesa_msisdn from LipaNaMpesaIPN  --listing of all the customers


select distinct mpesa_msisdn from LipaNaMpesaIPN where business_number = '815918'


select mpesa_msisdn,COUNT(mpesa_msisdn)as count from LipaNaMpesaIPN group by mpesa_msisdn having COUNT(mpesa_msisdn) > 1 --returning customers


select lipa2.mpesa_sender,lipa1.mpesa_msisdn, COUNT(lipa1.mpesa_msisdn)as count, SUM(lipa1.mpesa_amt) as amount from LipaNaMpesaIPN Lipa1 INNER JOIN LipaNaMpesaIPN Lipa2 ON (Lipa1.mpesa_msisdn = Lipa2.mpesa_msisdn)
where lipa1.business_number IN ('893512') group by lipa1.mpesa_msisdn having COUNT(lipa1.mpesa_msisdn) = 1 --new customers

