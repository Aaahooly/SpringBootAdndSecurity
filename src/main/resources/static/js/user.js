addRowUserInfoTable()

async function addRowUserInfoTable() {
    let promise = await fetch('http://localhost:8080/people/authentication/');
    let json = await promise.json();
    let headerUserAndRolePageUser = document.getElementById("headerUsernameAndRolePageUser");
    let resStringRole;
    let tableBody = document.getElementById("tbodyUserInfo");
    let newCellArr = [document.createElement("td"),
        document.createElement("td"),
        document.createElement("td"),
        document.createElement("td"),];
    newCellArr[0].innerHTML = json.id;
    newCellArr[1].innerHTML = json.username;
    newCellArr[2].innerHTML = json.age;
    if (json['roles'].length == 2) {
        newCellArr[3].innerHTML = resStringRole = "ADMIN, USER";
    } else if (json['roles'][0]['id'] == 1) {
        newCellArr[3].innerHTML = resStringRole = "ADMIN";
    } else {
        newCellArr[3].innerHTML = resStringRole = "USER";
    }
    headerUserAndRolePageUser.innerText = "Login: " + json.username + " with roles: " + resStringRole;
    let row = document.createElement('tr');
    for (let i = 0; i < newCellArr.length; i++) {
        row.appendChild(newCellArr[i]);
    }
    tableBody.appendChild(row);
}