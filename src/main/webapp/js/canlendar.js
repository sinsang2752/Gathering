

function getDayMeet(year, month, day) {
	
	var meetDay = year + "/" + month + "/" + day;
	
	$.ajax({
		url: "dayMeet.do",
		type: "GET",
		data: {"meetDay" : meetDay},
		success: function(result) {
			$("#toDoMeetArea").html(result);
		}, error: function(e) {
			console.log(e);
		}
	});
}

function calendarInit() {

    var date = new Date();
    var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // uct 표준시 도출
    var kstGap = 9 * 60 * 60 * 1000;
    var today = new Date(utc + kstGap);
  
    var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());

    var currentYear = thisMonth.getFullYear();
    var currentMonth = thisMonth.getMonth();
    var currentDate = thisMonth.getDate();

    renderCalender(thisMonth);
    
    function renderCalender(thisMonth) {

        currentYear = thisMonth.getFullYear();
        currentMonth = thisMonth.getMonth();
        currentDate = thisMonth.getDate();
        
        var dayArr = [];
        
        var curMon = currentMonth + 1;
        
        var month = currentYear + "/" + curMon;
        
        $.ajax({
			url: "meetDays.do",
			type: "GET",
			async: false,
			data: {"month" : month},
			success: function(data) {
				dayArr = data.days;
			}, error: function(e) {
				console.log(e);
			}
		});
		
		getDayMeet(currentYear, (currentMonth + 1), today.getDate());

        var startDay = new Date(currentYear, currentMonth, 0);
        var prevDate = startDay.getDate();
        var prevDay = startDay.getDay();

        var endDay = new Date(currentYear, currentMonth + 1, 0);
        var nextDate = endDay.getDate();
        var nextDay = endDay.getDay();

        // console.log(prevDate, prevDay, nextDate, nextDay);

        $('.year-month').text(currentYear + '.' + (currentMonth + 1));

        calendar = document.querySelector('.dates')
        calendar.innerHTML = '';
        
        for (var i = prevDate - prevDay + 1; i <= prevDate; i++) {
            calendar.innerHTML = calendar.innerHTML + '<div class="day prev disable">' + i + '</div>';
        }
        
        for (var i = 1; i <= nextDate; i++) {
			
			if(dayArr.includes(i)) {
            	calendar.innerHTML = calendar.innerHTML 
            	+ '<div class="day current meetDay" onclick="getDayMeet('+ currentYear + ',' + (currentMonth + 1) + ',' + i+')">' + i + '</div>';
            } else {
				calendar.innerHTML = calendar.innerHTML + '<div class="day current">' + i + '</div>';
			}
        }

        for (var i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
            calendar.innerHTML = calendar.innerHTML + '<div class="day next disable">' + i + '</div>';
        }

        if (today.getMonth() == currentMonth) {
            todayDate = today.getDate();
            var currentMonthDate = document.querySelectorAll('.dates .current');
            currentMonthDate[todayDate -1].classList.add('today');
        }
    }

    $('.go-prev').on('click', function() {
        thisMonth = new Date(currentYear, currentMonth - 1, 1);
        renderCalender(thisMonth);
    });

    $('.go-next').on('click', function() {
        thisMonth = new Date(currentYear, currentMonth + 1, 1);
        renderCalender(thisMonth); 
    });
}