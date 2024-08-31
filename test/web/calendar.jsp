<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calendar</title>

    <link rel="stylesheet" type="text/css" href="/test/assets/css/calendar.toolkit.css" />

    <script src="/test/assets/js/calendar.min.js"></script>
    <script src="/test/assets/js/calendar.toolkit.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <style>
        body {
            display: grid;
            place-items: center;

            margin-top: 10rem;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif
        }

        calendar {
            width: 60%;
        }
    </style>
</head>
<body>
    <h1> Test 1 </h1>
    <p> Mix <input type="date" target="myCalendar" /> <input type="datetime-local" target="myCalendar" /> </p>
    <p> Date <input type="date" target="myCalendar.date" /> <input type="datetime-local" target="myCalendar.date" /> </p>
    <p> Début <input type="date" target="myCalendar.start" /> <input type="datetime-local" target="myCalendar.start" /> </p>
    <p> Fin <input type="date" target="myCalendar.end" /> <input type="datetime-local" target="myCalendar.end" /> </p>
    <p> Resource <input type="text" target="myCalendar.resource" /> <input type="datetime-local" target="myCalendar.resource" /> </p>

    <calendar type="timeline" id="myCalendar" indicated selectable>
        <resources>
            <resource> Resource A </resource>
            <resource> Resource B </resource>
            <resource> Resource C </resource>
        </resources>
        <events>
            <event start="2024-08-20" all-day> Event 0 </event>
            <event color="white" border="red" background="red" start="2024-08-29T10:00:00"> Anniversaire de quelqu'un peut-être ? </event>
            <event color="orange" border="orange" background="white" start="2024-08-30" end="2024-08-31"> Manao auto-école ndray </event>
            <event href="https://youtube.com" start="2024-08-30"> YOUTUBE!!! </event>
        </events>
    </calendar>
    
    <h1> Test 2 </h1>
    <p> Mix <input type="date" target="myCalendar2" /> <input type="datetime-local" target="myCalendar2" /> </p>
    <p> Date <input type="date" target="myCalendar2.date" /> <input type="datetime-local" target="myCalendar2.date" /> </p>
    <p> Début <input type="date" target="myCalendar2.start" /> <input type="datetime-local" target="myCalendar2.start" /> </p>
    <p> Fin <input type="date" target="myCalendar2.end" /> <input type="datetime-local" target="myCalendar2.end" /> </p>
    <p> Event <input type="text" target="myCalendar2.event" /> <input type="datetime-local" target="myCalendar2.event" /> </p>
    <p> Debut Event <input type="date" target="myCalendar2.event.start" /> <input type="datetime-local" target="myCalendar2.event.start" /> </p>
    <p> Fin Event <input type="date" target="myCalendar2.event.end" /> <input type="datetime-local" target="myCalendar2.event.end" /> </p>
    <p> Groupe Event <input type="text" target="myCalendar2.event.group" /> <input type="datetime-local" target="myCalendar2.event.group" /> </p>

    <calendar type="grid" id="myCalendar2" clickable editable>
        <events>
            <event start="2024-08-20" end="2024-08-25" display="background" background="red" overlap="false"> Red Zone </event>
            <event start="2024-08-25" end="2024-08-30" display="background" background="green" group="green-zone"> Green Zone </event>
            <event clickable start="2024-08-15"> Red Event </event>
            <event start="2024-08-10" color="darkgreen" constraint="green-zone"> Green Event </event>
        </events>
    </calendar>

    <h1> Test 3 </h1>
    <p> Mix <input type="date" target="myCalendar3" /> <input type="datetime-local" target="myCalendar3" /> </p>
    <p> Date <input type="date" target="myCalendar3.date" /> <input type="datetime-local" target="myCalendar3.date" /> </p>
    <p> Début <input type="date" target="myCalendar3.start" /> <input type="datetime-local" target="myCalendar3.start" /> </p>
    <p> Fin <input type="date" target="myCalendar3.end" /> <input type="datetime-local" target="myCalendar3.end" /> </p>
    <p> Event <input type="text" target="myCalendar3.event" /> <input type="datetime-local" target="myCalendar3.event" /> </p>
    <p> Debut Event <input type="date" target="myCalendar3.event.start" /> <input type="datetime-local" target="myCalendar3.event.start" /> </p>
    <p> Fin Event <input type="date" target="myCalendar3.event.end" /> <input type="datetime-local" target="myCalendar3.event.end" /> </p>
    <p> Groupe Event <input type="text" target="myCalendar3.event.group" /> <input type="datetime-local" target="myCalendar3.event.group" /> </p>

    <calendar id="myCalendar3" type="grid" track-by="time" track-over="week" event-clickable>
        <events>
            <event start="2024-08-28" all-day> Event 0 </event>
            <event group="red-group" color="white" border="red" background="red" start="2024-08-29" end="2024-08-30"> Anniversaire de quelqu'un peut-être ? </event>
            <event color="red" border="red" background="white" start="2024-08-30" end="2024-08-31"> Manao auto-école ndray </event>
        </events>
    </calendar>

    <calendar type="list">
        <events>
            <event start="2024-08-20" all-day> Event 0 </event>
            <event color="white" border="red" background="red" start="2024-08-29" end="2024-08-30"> Anniversaire de quelqu'un peut-être ? </event>
            <event color="orange" border="orange" background="white" start="2024-08-30" end="2024-08-31"> Manao auto-école ndray </event>
        </events>
    </calendar>

    <calendar type="multiple" track-by="day" track-over="year">
        <events>
            <event start="2024-08-20" all-day> Event 0 </event>
            <event color="white" border="red" background="red" start="2024-08-29" end="2024-08-30"> Anniversaire de quelqu'un peut-être ? </event>
            <event color="orange" border="orange" background="white" start="2024-08-30" end="2024-08-31"> Manao auto-école ndray </event>
        </events>
    </calendar>
</body>
</html>