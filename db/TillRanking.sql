	/*Function GetTillRank

		Author: Tom Kimani
		
		Date: April 23

		Purpose: Returns grade of a merchant given a business Number

		Arguments: 
			1. @businessNumber Varchar for Merchant
			2. @startDate VarChar in the format '2014-10-01'
			3. @endDate VarChar in the format '2014-10-01'
			
		Return type: Numeric(12,2)
	*/
	ALTER Function [dbo].[fn_GetTillRank]
		(@businessNo Varchar(10), @startDate varchar(50),@endDate varchar(50))
		Returns varchar(10)
	As
	Begin
		DECLARE @totalAmount numeric(35,0)
		DECLARE @min_value numeric(35,0)
		DECLARE @max_value numeric(35,0)
		DECLARE @grade varchar(10)
		DECLARE @timePeriod numeric(2,0)
		DECLARE @averageAmt numeric(11,0)
		DECLARE @tblCount int
		DECLARE @counter int
		DECLARE @startpoint int
		DECLARE @description varchar(50)
		DECLARE @maximum_amt numeric(11,0)
		DECLARE @returnVal varchar(10)
		

		select @averageAmt = dbo.fn_GetTillAverage(@businessNo,@startDate,@endDate)
		
		--print 'Average Amount:'+ cast(@averageAmt as varchar)
		--print 'Time Period:'+ cast(@timePeriod as varchar)

		SET @counter = 1

		SELECT TOP 1 @startpoint=id from TillRanges order by id ASC
		SELECT @maximum_amt = min_value from TillRanges where id=1

		select @tblCount=COUNT(*) from TillRanges

		while(@counter <= @tblCount)
			BEGIN
				select @min_value = min_value,@max_value=max_value,@grade=grade,@description=description from TillRanges
				where id=@startpoint
				
				--print 'Max Values>>'+CAST(@startpoint as varchar)+'::'+cast(@max_value as varchar)
				
				if @averageAmt >= @maximum_amt
					BEGIN
						--print 'Grade::' + @grade + 'Description:'+ @description
						select @returnVal=@grade
						break
					END	
				ELSE IF @averageAmt >= @min_value AND @averageAmt <= @max_value
					  BEGIN
						  --print 'id:'+CAST(@startpoint as varchar)+'>>'+CAST(@min_value AS varchar) +'-'
						  --+ cast(@max_value as varchar)
						  --print 'Grade::' + @grade + '::'+ @description
						  select @returnVal=@grade
						 break
					 END
				ELSE
					select @returnVal=@grade
				
				/****************************/
				SELECT @counter=@counter+1
				SELECT @startpoint=@startpoint+1
			END
		return @returnVal
	END	



