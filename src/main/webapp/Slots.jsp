<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <style>
        <%@include file="/Styles/SlotsStyle.css"%>
        <%@include file="/Styles/UpperBar.css"%>
      </style>

      <head>
        <title> Slots </title>
        <script src = "Scripts/SlotsScript.js"></script>
      </head>

      <body style = "background-color: #0e0d0d">

        <div id="header_box">
            <header id="upper-bar">
                <div id="left_corner">
                    <a href="/homepage"> <img src="Images/Logo.png" id="logo"> </a>
                </div>
                <div id = title-format> NARGIZBET SLOTS </div>
            </header>
        </div>

        <div id = dist> </div>

        <div class = container>
            <div class = slot id = slot0>
                <img src = "Images/DefaultSlotImage.png" alt="" width="180" height="300"
                     id="slot0-img"/>
            </div>

            <div class = slot id = slot1>
                <img src = "Images/DefaultSlotImage.png" alt="" width="180" height="300"
                     id="slot1-img"/>
            </div>

            <div class = slot id = slot2>
                <img src = "Images/DefaultSlotImage.png" alt="" width="180" height="300"
                     id="slot2-img"/>
            </div>

        </div>

        <br>

        <div class = center-spin-button>
            <button id = spin-button onclick = "spin()"> SPIN! </button>
        </div>


      </body>

</html>