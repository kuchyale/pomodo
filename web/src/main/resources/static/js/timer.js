const timer = new easytimer.Timer();
let nextRoundTab;
let time;

$("#timerStopBtn").hide();

$('#nav-work-time-tab').click(changeTimerTab(this, workPeriodTime));

$('#nav-sbrake-tab').click(changeTimerTab(this, shortBrakeTime));

$('#nav-lbrake-tab').click(changeTimerTab(this, longBrakeTime));

function changeTimerTab(tab, tabTime) {
    return function() {
        if (!$(tab).hasClass("active")) {
            nextRoundTab = $(tab);
            time = tabTime;
            if (timer.isRunning()) {
                $("#timerTabModal").modal();
            } else {
                $('#timer .values').html((time < 10 ? '0' : '') + time + ":00");
                nextRoundTab.tab('show');
            }
        }
    }
}

$('#endRoundBtn').click(function() {
    $("#timerTabModal").modal("hide");
    stopTimer(time);
    nextRoundTab.tab('show');
});

$('#timerStartBtn').click(function() {
    let time;
    if ($("#nav-work-time-tab").hasClass("active")) {
        time = workPeriodTime;
    } else if ($("#nav-sbrake-tab").hasClass("active")) {
        time = shortBrakeTime;
    } else if ($("#nav-lbrake-tab").hasClass("active")) {
        time = longBrakeTime;
    }
    timer.start({countdown: true, startValues: {minutes: time}});
    $("#timerStartBtn").hide();
    $("#timerStopBtn").show();
});

$('#timerStopBtn').click(function() {
    stopTimer();
});

timer.addEventListener('secondsUpdated', function() {
    setTimerHtmlValues();
});

timer.addEventListener('reset', function() {
    setTimerHtmlValues();
});

timer.addEventListener('targetAchieved', function (e) {
    timer.reset();
    timer.stop();
    playTimerSound();
});

function setTimerHtmlValues() {
    $('#timer .values').html(timer.getTimeValues().toString(['minutes', 'seconds']));
}

function stopTimer(time) {
    timer.reset();
    timer.stop();
    if (time) {
        $('#timer .values').html((time < 10 ? '0' : '') + time + ":00");
    }
    $("#timerStopBtn").hide();
    $("#timerStartBtn").show();
}

function playTimerSound() {
    const audio = new Audio('../../audio/bell_sound.mp3');
    audio.play();
}