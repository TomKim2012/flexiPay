	DECLARE @businessNos varchar(500)
	DECLARE @SQLString NVARCHAR(500);
	DECLARE @SQLString2 NVARCHAR(500);
	DECLARE @ParmDefinition NVARCHAR(500);
	
	
	SET @SQLString =
		 'SELECT * FROM LipaNaMpesaIPN';
     
	IF(@businessNos!='ALL')
	 SET @SQLString = @SQLString + ' where business_number IN (@businessNos)'
	 
	 
	print @SQLString
 
	/* Specify the parameter format one time. */
	SET @ParmDefinition = N'@businessNos varChar(500)';
	
	SET @businessNos = '893512'
	
	EXECUTE sp_executesql @SQLString, @ParmDefinition,
                      @businessNos = @businessNos;