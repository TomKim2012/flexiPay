	
		/*Function GetTillAverage

		Author: Tom Kimani
		
		Date: April 23

		Purpose: Returns grade of a merchant given a business Number

		Arguments: 
			1. @businessNumber Varchar for Merchant
			2. @startDate VarChar in the format '2014-10-01'
			3. @endDate VarChar in the format '2014-10-01'
			
		Return type: Numeric(12,2)
	*/
	ALTER Function [dbo].[fn_GetTillAverage](
		@businessNo varchar(10),
		@startDate varchar(50),
		@endDate varchar(50)
	 )
	returns numeric(11,0) AS
	
	BEGIN
			DECLARE @timePeriod numeric(2,0)
			DECLARE @totalAmount numeric(35,0)
			DECLARE @averageAmt numeric(11,0)

			--/*Get totalAmount startDate and endDate and businessNo*/
			set @timePeriod= dbo.FullMonthsSeparation(@startDate,@endDate)

			select  @totalAmount = SUM(mpesa_amt) from LipaNaMpesaIPN where 
			tstamp>=@startDate and tstamp<=@endDate
			and business_number=@businessNo
					
			/*Time Period*/
			if @timePeriod<1
				set @timePeriod = 1

			set @averageAmt = @totalAmount / @timePeriod
			if @averageAmt IS NULL
				set @averageAmt=0	
		return @averageAmt
	END