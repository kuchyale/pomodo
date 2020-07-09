let timer = new easytimer.Timer();
$("#timerStopBtn").hide();

$('#timerStartBtn').click(function() {
    timer.start({countdown: true, startValues: {minutes: 25}});
    $("#timerStartBtn").hide();
    $("#timerStopBtn").show();
});

$('#timerStopBtn').click(function() {
    timer.reset();
    timer.stop();
    $("#timerStopBtn").hide();
    $("#timerStartBtn").show();
});

timer.addEventListener('secondsUpdated', function() {
    $('#timer .values').html(timer.getTimeValues().toString(['minutes', 'seconds']));
});

timer.addEventListener('started', function() {
    $('#timer .values').html(timer.getTimeValues().toString(['minutes', 'seconds']));
});

timer.addEventListener('reset', function() {
    $('#timer .values').html(timer.getTimeValues().toString(['minutes', 'seconds']));
});

timer.addEventListener('targetAchieved', function (e) {
    timer.reset();
    timer.stop();
    playTimerSound();
});

function playTimerSound() {
    const audio = new Audio('../../audio/bell_sound.mp3');
    audio.play();
}