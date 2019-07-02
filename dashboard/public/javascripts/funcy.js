jQuery(document).ready(($) => {
    console.log("document ready, funcy view");
    var socket;

    $("#connectBtn").on("click", function() {
        if(!("WebSocket" in window)){
            alert('<p>You need a browser that supports WebSockets. How about <a href="http://www.google.com/chrome">Google Chrome</a>?</p>');
        }else {

            try {
                var host = "ws://localhost:9000/funcy-output";
                socket = new WebSocket(host);

                console.log('Socket Status: ' + socket.readyState + ' (not connected)');

                socket.onopen = function () {
                    console.log('Socket Status: ' + socket.readyState + ' (open)');
                }

                socket.onmessage = function (msg) {

                    var reader = new FileReader();
                    reader.onload = function() {
                        var output = reader.result.split('\n');
                        output.forEach(function(str){
                            if(str !== ""){
                                $("#outputContainer").append("<li class='col-12 outputItem'>"+str+"</li>");
                            }
                        });

                    }
                    reader.readAsText(msg.data);
                }

                socket.onclose = function () {
                    console.log('Socket Status: ' + socket.readyState + ' (closed)');
                }

            } catch (exception) {
                console.log('Error: ' + exception);
            }
        }
        $("#connectBtn").attr("hidden", true);
        $("#disconnectBtn").attr("hidden", false);
    });

    $("#disconnectBtn").on("click", function(){
       socket.close();
       $.post("/funcy-close-stream", function(data){ location.reload(); });
    });

    $("#clearButton").on("click", function() {
        $(".outputItem").remove();
    });

    $.get("/funcy-serial-ports", (serialPorts) => {
        var ports = JSON.parse(serialPorts);
        if(ports.length == 0){
            $("#selectValueBtn").attr("hidden", true);
            $("#noValuesLabel").attr("hidden", false);
        } else {
            $("#selectValueBtn").attr("hidden", false);
            $("#noValuesLabel").attr("hidden", true);
            $.each(ports, function(ind, val){
                $("#selectPort").append("<option label='" + val + "' value="+val+"></option>");
            });
        }
    });

    $.get("/funcy-monitoring-values", (values) => {
        var vals = JSON.parse(values);
        if (vals.baud != 0 && vals.serial != ""){
            $("#baud").text(vals.baud);
            $("#port").text(vals.port);
            $("#funcyOutput").attr("hidden", false);

            //show connect button
            $("#selectValueBtn").attr("hidden", true);
            $("#connectBtn").attr("hidden", false);
        } else {
            $("#funcyOutput").attr("hidden", true);
        }
    });

    $.get("/funcy-baud-rates", (baudRates) => {
        var rates = JSON.parse(baudRates);

        $.each(rates, function(ind, val){
            $("#selectBaud").append("<option label='" + val + "'value="+val+"></option>");
        });
    });


});