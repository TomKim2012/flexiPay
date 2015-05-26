--CREATE
CREATE
 PROCEDURE sp_GetTrends 
	@startDate varchar(50),@endDate varchar(50),@businessNos varchar(MAX), @userId varchar(50),
	@displayType varchar(50)
 AS 
	BEGIN
	DECLARE @months_between int
	DECLARE @tempStartDate varchar(50)
	DECLARE @tempEndDate varchar(50)
	DECLARE @SQLString nvarchar(MAX)
	DECLARE @ParmDefinition nvarchar(MAX)
	DECLARE @tempSum numeric(35,0)
	DECLARE @uniqueCustomers numeric(35,0)
	DECLARE @uniqueMerchants numeric(35,0)
	DECLARE @merchantAverage numeric(35,0)
	DECLARE @customerAverage numeric(35,0)
	DECLARE @totalTransactions numeric(35,0)
	DECLARE @counter int 
	
	SET @counter= 0

	--SET @months_between = dbo.FullMonthsSeparation(@startDate,@endDate) + 1
	if(@displayType='DAY')
		SET @months_between = DATEDIFF(DAY,@startDate,@endDate) + 1
	ELSE IF(@displayType='WEEK')
		SET @months_between = DATEDIFF(WEEK,@startDate,@endDate) + 1
	ELSE IF(@displayType='MONTH')
		SET @months_between = DATEDIFF(MONTH,@startDate,@endDate) + 1
	ELSE IF(@displayType='YEAR')
		SET @months_between = DATEDIFF(YEAR,@startDate,@endDate) + 1
	--print @months_between
	
	--delete previous trends
	DELETE FROM TrendView where userId=@userId

	while(@counter!=@months_between)
		 BEGIN
			--print Cast(@startDate as varchar)
			
			SELECT @tempStartDate = DATEADD(month, DATEDIFF(month, 0,@startDate ), 0)
			
			SELECT @tempEndDate = DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,@startDate)+1,0))
			
			
			--print cast(@tempstartDate as varchar)+' to '+cast(@tempendDate as varchar)
			
			
			SET @SQLString = N'SELECT @tempSum = SUM(mpesa_amt),@totalTransactions = COUNT(mpesa_amt),
							   @uniqueCustomers = count(distinct mpesa_msisdn),@uniqueMerchants = 
							   count(distinct business_number) from LipaNaMpesaIPN where tstamp>=@tempStartDate and
							   tstamp<=@tempEndDate'			
			IF(@businessNos!='ALL')
			SET @SQLString = @SQLString + N' and business_number IN (@businessNos)'
			
			/* Specify the parameter format one time. */
			SET @ParmDefinition = N'@totalTransactions numeric(35,0) OUTPUT,@uniqueMerchants numeric(35,0) OUTPUT,
									@uniqueCustomers numeric(35,0) OUTPUT,
									@tempSum numeric(35,0) OUTPUT,@businessNos varChar(500),@tempStartDate varchar(50)								     ,@tempEndDate varchar(50)';
					
			EXECUTE sp_executesql @SQLString, @ParmDefinition,
						  @businessNos = @businessNos, @tempStartDate = @tempStartDate, 
						  @tempEndDate = @tempEndDate,@tempSum=@tempSum OUTPUT,
						  @uniqueCustomers = @uniqueCustomers OUTPUT,
						  @uniqueMerchants = @uniqueMerchants OUTPUT,
						  @totalTransactions = @totalTransactions OUTPUT;
							  
			--print @SQLString
			--print 'Total Sum::'+ CAST (@tempSum AS NVARCHAR)
			--print 'Total Trxs::'+ CAST (@totalTransactions AS NVARCHAR)						
			--print 'Unique Customers'+ CAST (@uniqueCustomers as varchar)
			--print 'Unique Merchants'+ CAST(@uniqueMerchants as varchar)
			
			SELECT @merchantAverage = @tempSum / @uniqueMerchants
			SELECT @customerAverage = @tempSum / @uniqueCustomers
			
			--print 'Merchant Average:'+ CAST(@merchantAverage as varchar)
			--print 'Customer Average:'+ CAST(@customerAverage as varchar)		
			--print CAST(@counter as Varchar) +'>>>'+CAST(@tempStartDate as varchar)+'>>'+CAST (@tempSum as varchar)
			
			INSERT INTO TrendView
				select @counter,@totalTransactions,@tempSum,@uniqueMerchants,@uniqueCustomers,
				@merchantAverage,@customerAverage,@tempStartDate,@tempEndDate,@userId 
				
			SET @startDate = DATEADD(MM, +1,@startDate)
			select @counter=@counter+1
		END
	RETURN
END
