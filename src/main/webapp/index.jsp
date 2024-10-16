<%--
  Created by IntelliJ IDEA.
  User: ra
  Date: 15.10.2024
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.common.models.RequestEntryModel" %>
<%@ page import="java.util.List" %>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пример страницы</title>
    <link rel="stylesheet" href="style/style.css">
</head>
<body>
<div class="header" style="font-family: fantasy">
    <h1>Захарченко Роман Владимирович</h1>
    <h2>Группа Р3131 / Вариант 660</h2>
</div>

<div class="form-container">
    <form class="myform" id="myform">
        <input type="hidden" name = "action" value="areaCheck">
        <!-- Поля формы (оставляем как есть) -->
        <div class="input-group">
            <label for="x-coordinate">Изменение Y:</label>
            <input type="text" id="x-coordinate" name="x" placeholder="-4 ... 4"/>
        </div>


        <div class="input-group">
            <label for="y-coordinate">Изменение Y:</label>
            <input type="text" id="y-coordinate" name="y" placeholder="-3 ... 3"/>
        </div>

        <div class="input-group">
            <label>Изменение R:</label>
            <div class="checkbox-group">
                <label><input type="checkbox" name="r" value="1" class = "checkR"></label>
                <label><input type="checkbox" name="r" value="2" class = "checkR"></label>
                <label><input type="checkbox" name="r" value="3" class = "checkR"></label>
                <label><input type="checkbox" name="r" value="4" class = "checkR"></label>
            </div>
            <label id = "resultCheckBox">0</label>
        </div>

        <button type="submit">Отправить</button>
        <div id="out_put_text" class="out_put_text" ></div>
    </form>

    <!-- Canvas для отрисовки фигуры -->
    <div class = "graph-canvas">
        <canvas id="graphCanvas" width="300" height="300"></canvas>
    </div>
</div>

<div class="large-rectangle">
    <table>
        <thead>
        <tr>
            <th>X, Y, R</th>
            <th>Результат</th>
            <th>Время реальное</th>
            <th>Время работы скрипта (мс)</th>
        </tr>
        </thead>
        <tbody id = "myTBody">

        </tbody>
    </table>
</div>
<script src = "js/script.js"></script>
<script src = "js/graph.js"></script>
<script src = "js/actions.js"></script>
</body>
</html>
