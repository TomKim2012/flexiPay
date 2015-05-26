	DECLARE @businessNos varchar(500)
	DECLARE @SQLString NVARCHAR(500);
	DECLARE @SQLString2 NVARCHAR(500);
	DECLARE @ParmDefinition NVARCHAR(500);
	DECLARE @totalTransactions numeric(35,0)
	DECLARE @tempSum numeric(35,0)
	SET @businessNos='ALL'
	
	SET @SQLString =
		 'SELECT * from LipaNaMpesaIPN 
			where tstamp>=@tempStartDate and tstamp<=@tempEndDate'
			
     
	IF(@businessNos!='ALL')
	 SET @SQLString = @SQLString + ' where business_number IN (@businessNos)'
	 
	 
	print @SQLString
 
	/* Specify the parameter format one time. */
	SET @ParmDefinition = N'@totalTransactions numeric(35,0),
							@tempSum numeric(35,0),
							@businessNos varChar(500),
							@tempStartDate varchar(50),
							@tempEndDate varchar(50)';
	
	EXECUTE sp_executesql @SQLString, @ParmDefinition,
                      @businessNos = @businessNos, @totalTransactions = @totalTransactions, @tempSum=@tempSum;
    
    --print 'tempSum:'+CAST(@tempSum as Varchar);