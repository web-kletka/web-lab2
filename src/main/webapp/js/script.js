class Interaction {
    constructor(form) {
        console.log("Request init")
        this.form = form;
    }
    send = async (ev=null) => {

        if (ev) {
            ev.preventDefault();
            console.log("Остановлена отправка формы");
        }

        const formData = new FormData(this.form);
        const params = new URLSearchParams(formData);

        console.log(params.get("r"))
        params.set("r", document.getElementById("resultCheckBox").textContent);
        console.log(params.get("r"))
        console.log(params.toString())
        if (!this.validate(params)) return
        console.log("params ok")
        const url = "/web-lab2-1/controller?" + params.toString();
        try {
            fetch(url, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                }
            })
                .then(response => {
                    if (response.ok) {
                        return response.json(); // Если статус ответа 200, парсим JSON
                    } else {
                        return response.json().then(result => {
                            throw new Error(result.error.toString()); // Обработка ошибок с сервера
                        });
                    }
                })
                .then(result => {
                    console.log(result.got)

                    if (result.got) drawPoint(params.get("x"), params.get("y"), "green")
                    else drawPoint(params.get("x"), params.get("y"), "red")
                    document.getElementById("out_put_text").innerHTML = result.resultJSP;

                    var tbody = document.querySelector("#myTBody")
                    tbody.insertAdjacentHTML("afterbegin", result.trJSP);

                })
                .catch(error => {
                    console.error('Ошибка:', error);
                    outputText.textContent = error.message;
                });

        } catch (error) {
            console.error('Ошибка', error)
        }
    }

    validate = (params) => {
        let x = params.get("x");
        let y = params.get("y");
        let r = params.get("r");
        let result = "";
        console.log(x, y, r);

        if (x == null){
            result = "ERROR: x = null";
            console.log("ERROR: x = null");
        }
        if (y === ''){
            result = "ERROR: y = ''";
            console.log("ERROR: y = ''");
        }
        console.log(y, typeof y, Number.isNaN(y))
        if (Number.isNaN(Number(y))){
            result = "ERROR: y not num";
            console.log("ERROR: y not num");
        }
        if (r == null){
            result = "ERROR: r = null";
        }
        if (result === "")
            return true;
        else {
            outputText.hidden = false
            outputText.textContent = result;
            return false;
        }
    }
}


// вызов функций при обновлении страницы
window.onbeforeunload = saveTable;
window.onload = loadTable;

const outputText = document.getElementById("out_put_text")
const tBody = document.getElementById("myTBody")

let num = parseInt(document.querySelector("#myTBody tr:last-child td:first-child")?.textContent) || 0;
console.log("NUM:" + num);

function saveTable() {
    let tableData = [];
    let rows = document.querySelectorAll('#myTBody tr');
    rows.forEach(function(row) {
        let rowData = [];
        let cells = row.querySelectorAll('td');
        cells.forEach(function(cell) {
            rowData.push(cell.textContent);
        });
        tableData.push(rowData);
    });
    localStorage.setItem('tableData', JSON.stringify(tableData));
}


function loadTable() {
    let tableData = localStorage.getItem('tableData');
    tableData = JSON.parse(tableData);
    if (tableData) {
        // заполняем таблицу данными из localStorage
        let table = tBody;
        table.innerHTML = '';
        tableData.forEach(function(rowData) {
            let row = document.createElement('tr');
            rowData.forEach(function(cellData) {
                let cell = document.createElement('td');
                cell.textContent = cellData;
                row.appendChild(cell);
            });
            table.appendChild(row);
        });

        num = parseInt(document.querySelector("#myTBody tr:last-child td:first-child")?.textContent) || 0;
    } else {
        num = 0;
    }
}



