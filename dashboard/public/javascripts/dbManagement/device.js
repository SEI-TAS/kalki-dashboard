jQuery(document).ready(($) => {
    let deviceTable = $('#deviceTable').DataTable(
        {
            order: [[1, 'asc']],
            columnDefs: [
                {"orderable": false, "targets": 0}
            ]
        }
    );


    async function getDevices() {
        //must wait on these functions to complete otherwise the mappings might not be set in time
        deviceTable.clear();

        $.get("/devices", (devices) => {
            $.each(JSON.parse(devices), (index, device) => {
                let deviceGroupName = device.group ? device.group.name : "N/A";

                let newRow = "<tr id='tableRow" + device.id + "'>\n" +
                    "    <td class='fit'>" +
                    "        <div class='editDeleteContainer' >" +
                    "           <button type='button' class='btn btn-primary btn-sm' id='editButton" + device.id + "'>Edit</button>" +
                    "           <button type='button' class='btn btn-secondary btn-sm' id='deleteButton" + device.id + "'>Delete</button>" +
                    "        </div>" +
                    "    </td>\n" +
                    "    <td class='fit' id='deviceID" + device.id + "'>" + device.id + "</td>\n" +
                    "    <td id='name" + device.id + "'>" + device.name + "</td>\n" +
                    "    <td id='description" + device.id + "'>" + device.description + "</td>\n" +
                    "    <td id='deviceType" + device.id + "'>" + device.type.name + "</td>\n" +
                    "    <td id='group" + device.id + "'>" + deviceGroupName + "</td>\n" +
                    "    <td id='ipAddress" + device.id + "'>" + device.ip + "</td>\n" +
                    "    <td id='statusHistorySize" + device.id + "'>" + device.statusHistorySize + "</td>\n" +
                    "    <td id='samplingRate" + device.id + "'>" + device.samplingRate + "</td>\n" +
                    "    <td id='tags" + device.id + "'>" + tagIdsToNames(device.tagIds) + "</td>\n" +
                    "</tr>"

                deviceTable.row.add($(newRow)).draw();

                //add tagIds for this device to the map
                // deviceIdToTagIdsMap[device.id] = device.tagIds;

                //Add options in copy from existing device modal
                // $("#deviceContent .form-control#deviceSelect").append("<option id='deviceSelectOption" + device.id + "' value='" + device.id + "'>\n" +
                //     device.name + "</option>");



            });
        });
    }



    //populate device form with information from the desired device
    $("#copyFromDeviceModalForm").submit(function (e) {
        e.preventDefault();
        let deviceId = document.getElementById("deviceSelect").value;
        $.get("/device", {id: deviceId}, function (device) {
            populateForm(JSON.parse(device))
        });
        $("#copyFromDeviceModal").modal("hide");
    });

    //only load data when tab is active
    $('a[href="#DeviceContent"]').on('shown.bs.tab', function (e) {
        getDevices();
    });
});
