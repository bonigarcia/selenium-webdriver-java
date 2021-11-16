var keys = document.querySelectorAll('#calculator span');
var operators = ['+', '-', 'x', '÷'];
var decimalAdded = false;

function setCookie(cname, cvalue, exminutes) {
    const d = new Date();
    d.setTime(d.getTime() + (exminutes * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    console.log("Cookies", decodedCookie);
    let ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

for (var i = 0; i < keys.length; i++) {
    keys[i].onclick = function (e) {
        var input = document.querySelector('.screen');
        var inputVal = input.innerHTML;
        var btnVal = this.innerHTML;

        if (btnVal == 'C') {
            input.innerHTML = '';
            decimalAdded = false;
        } else if (btnVal == '=') {
            var equation = inputVal;
            var lastChar = equation[equation.length - 1];

            equation = equation.replace(/x/g, '*').replace(/÷/g, '/');

            if (operators.indexOf(lastChar) > -1 || lastChar == '.')
                equation = equation.replace(/.$/, '');

            if (equation) {
                var countCookie = getCookie("count");
                var count = countCookie ? Number(countCookie) : 1;
                console.log("count", count);

                var correct = document.getElementById("correct").value;
                var random = Math.floor(Math.random() * 100) + 1;
                var correctResult = count >= correct;

                if (!correctResult) {
                    var percent = document.getElementById("percent").value;
                    correctResult = random > percent;
                }
                if (correctResult) {
                    console.log("Getting correct result");
                    input.innerHTML = eval(equation);
                }
                else {
                    console.log("Getting random result");
                    input.innerHTML = random;
                }
                count++;
                setCookie("count", count, 1);
            }

            decimalAdded = false;
        } else if (operators.indexOf(btnVal) > -1) {
            var lastChar = inputVal[inputVal.length - 1];
            if (inputVal != '' && operators.indexOf(lastChar) == -1)
                input.innerHTML += btnVal;

            else if (inputVal == '' && btnVal == '-')
                input.innerHTML += btnVal;

            if (operators.indexOf(lastChar) > -1 && inputVal.length > 1) {
                input.innerHTML = inputVal.replace(/.$/, btnVal);
            }

            decimalAdded = false;
        } else if (btnVal == '.') {
            if (!decimalAdded) {
                input.innerHTML += btnVal;
                decimalAdded = true;
            }
        } else {
            input.innerHTML += btnVal;
        }

        e.preventDefault();
    }
}
