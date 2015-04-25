/*Get startDate and endDate and businessNo*/
DECLARE @totalAmount numeric(35,0)
DECLARE @startDate varchar(50)
DECLARE @endDate varchar(50)

set @startDate = '2015-01-01'
set @endDate = '2015-01-31'

select  @totalAmount = SUM(mpesa_amt) from LipaNaMpesaIPN where 
tstamp>=@startDate and tstamp<=@endDate
and business_number='893817'

DECLARE @min_value numeric(35,0)
DECLARE @max_value numeric(35,0)
DECLARE @grade varchar(50)
DECLARE @timePeriod numeric(2,0)

select @timePeriod= dbo.FullMonthsSeparation(@startDate,@endDate)
DECLARE @averageAmt numeric(11,0)


if @timePeriod<1
select @timePeriod = 1

select @averageAmt = @totalAmount / @timePeriod
print 'Average Amount:'+ cast(@averageAmt as varchar)
print 'Time Period:'+ cast(@timePeriod as varchar)

DECLARE @tblCount int
DECLARE @counter int
DECLARE @startpoint int
DECLARE @description varchar(50)
SET @counter = 1

SELECT TOP 1 @startpoint=id from TillRanges order by id ASC

select @tblCount=COUNT(*) from TillRanges

	while(@counter <= @tblCount)
	BEGIN
		select @min_value = min_value,@max_value=max_value,@grade=grade,@description=description from TillRanges
		where id=@startpoint
		
		print '>Below '+CAST(@min_value AS varchar)
		
		if @max_value IS NOT NULL
		BEGIN
			if @averageAmt < @min_value
			  BEGIN
				print '>Below '+CAST(@min_value AS varchar)
				print 'Grade::' + @grade + 'Description:'+ @description
			  break
			  END
		
			else if @averageAmt >= @min_value AND @averageAmt <= @max_value
			  BEGIN
			  print 'id'+CAST(@startpoint as varchar)+' '+CAST(@min_value AS varchar) +' '+ cast(@max_value as varchar)
			  print 'Grade::' + @grade + 'Description:'+ @description
			  break
			  END
		END
		ELSE
		BEGIN
			if @averageAmt >= @min_value
				print CAST(@startpoint as varchar)+'above '+CAST(@min_value AS varchar)
				print 'Grade::' + @grade + 'Description:'+ @description
		END	
	SELECT @counter=@counter+1
	SELECT @startpoint=@startpoint+1
	END
		
	





