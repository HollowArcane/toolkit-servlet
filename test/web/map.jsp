<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calendar</title>

    <link rel="stylesheet" type="text/css" href="/test/assets/css/map.min.css" />
    <link rel="stylesheet" type="text/css" href="/test/assets/css/map.toolkit.css" />

    <script src="/test/assets/js/map.min.js"></script>
    <script src="/test/assets/js/map.toolkit.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <style>
    </style>
</head>
<body>
    <h1> Test 1 </h1>
    <input id="myInput" oninput="console.log(myInput.value)" type="text" target="myMap.pin.lat" />
    <input type="number" target="myMap.pin.latitude" />
    <input type="text" target="myMap.pin.lng" />
    <input type="number" target="myMap.pin.longitude" />
    <input type="text" target="myMap.pin.name" />
    <br><br>
    <input type="text" target="myMap.marker.lat" />
    <input type="number" target="myMap.marker.latitude" />
    <input type="text" target="myMap.marker.lng" />
    <input type="number" target="myMap.marker.longitude" />
    <input type="text" target="myMap.marker.name" />

    <map id="myMap" lat="51.5" lng="-0.09">
        <icon popup-anchor-y="-50" popup-anchor-x="0" anchor-x="25" anchor-y="70" shadow-anchor-x="10" shadow-anchor-y="65" width="50" height="70" shadow-width="50" shadow-height="70" src="assets/img/pin.png" shadow-src="assets/img/shadow.png" name="myIcon"></icon>
        
        <pin icon="myIcon" lat="0" lng="0" follow-click>
            <overlay type="popup">
                <form>
                    <p> Username <input type="text" name="username" /> </p>
                    <p> Password <input type="password" name="password" /> </p>
                    <p> Latitude <input type="number" name="latitude"7 target="myMap.latitude" /> </p>
                    <p> Longitude <input type="number" name="longitude" target="myMap.longitude" /> </p>
                    <button> Submit </button>
                </form>
            </overlay>
        </pin>

        <pin name="myPin" opacity="0.3" lat="51.5" lng="-0.09" draggable>
            <overlay type="popup">
                <b>Hello world!</b><br>I am a popup.
                <img width="100" src="assets/img/img-1.jpg" />
            </overlay>
        </pin>
        <path border="red" border-dash="0 4 0" border-width="1" background="rgba(255, 0, 0, .1)" radius="500" lat="51.5" lng="-0.09">
            <overlay type="tooltip" open> I am a circle and a polygon! </overlay>
        </path>
        <path type="rectangle"  opacity="0.5" border="darkgreen" background="rgba(0, 255, 150, .1)">
            <point lat="51.509" lng="-0.08"></point>
            <point lat="51.51" lng="-0.047"></point>
            <point lat="51.503" lng="-0.06"></point>
        </path>
        <!-- <path type="image" src="assets/img/img-1.jpg" >
            <point lat="51.509" lng="-0.08"></point>
            <point lat="51.51" lng="-0.047"></point>
            <point lat="51.503" lng="-0.06"></point>
        </path> -->
    </map>
</body>
</html>