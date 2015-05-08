
--CREATE
ALTER
 FUNCTION GetTrends(@startDate varchar(50),@endDate varchar(50))
	RETURNS @trendTable TABLE (
		MonthId Int,
		totalTransactions numeric(35,0) default 0,
		totalAmount numeric(35,0) default 0,
		uniqueMerchants numeric(35,0) default 0,
		uniqueCustomers numeric(35,0) default 0,
		CustomerAverage numeric(35,0) default 0,	
		merchantAverage numeric(35,0) default 0,	
		startDate datetime,
		endDate dateTime
	) AS 
	BEGIN
	DECLARE @months_between int
	DECLARE @tempStartDate varchar(50)
	DECLARE @tempEndDate varchar(50)
	DECLARE @tempSum numeric(35,0)
	DECLARE @totalTransactions numeric(35,0)
	DECLARE @uniqueCustomers numeric(35,0)
	DECLARE @uniqueMerchants numeric(35,0)
	DECLARE @merchantAverage numeric(35,0)
	DECLARE @customerAverage numeric(35,0)

	DECLARE @counter int 
	SET @counter= 0

	SET @months_between = dbo.FullMonthsSeparation(@startDate,@endDate) + 1
	--print @months_between

	while(@counter!=@months_between)
		 BEGIN
			--print Cast(@startDate as varchar)
			
			SELECT @tempStartDate = DATEADD(month, DATEDIFF(month, 0,@startDate ), 0)
			
			SELECT @tempEndDate = DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,@startDate)+1,0))
			
			--print cast(@tempstartDate as varchar)+' to '+cast(@tempendDate as varchar)
			
			SELECT @tempSum = SUM(mpesa_amt), @totalTransactions = COUNT(mpesa_amt) from LipaNaMpesaIPN 
			where tstamp>=@tempStartDate and tstamp<=@tempEndDate
			--print 'Total Sum'+ CAST (@tempSum as varchar)
			
			SELECT @uniqueCustomers = count(distinct mpesa_msisdn) from LipaNaMpesaIPN where tstamp>=@tempStartDate
			and tstamp<=@tempEndDate
			--print 'Unique Customers'+ CAST (@uniqueCustomers as varchar)
			
			SELECT @uniqueMerchants = count(distinct business_number) from LipaNaMpesaIPN where tstamp>=@tempStartDate
			and tstamp<=@tempEndDate
			--print 'Unique Merchants'+ CAST(@uniqueMerchants as varchar)
			
			SELECT @merchantAverage = @tempSum / @uniqueMerchants
			SELECT @customerAverage = @tempSum / @uniqueCustomers
			
			--print CAST(@counter as Varchar) +'>>>'+CAST(@tempStartDate as varchar)+'>>'+CAST (@tempSum as varchar)
			INSERT @trendTable
				select @counter,@totalTransactions,@tempSum,@uniqueMerchants,@uniqueCustomers,
				@merchantAverage,@customerAverage,@tempStartDate,@tempEndDate 
				
			SET @startDate = DATEADD(MM, +1,@startDate)
			select @counter=@counter+1
		END
	RETURN
END