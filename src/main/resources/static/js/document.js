const urlUsers = 'http://localhost:8080/people/';
const urlUser = 'http://localhost:8080/people/authentication/';
let usId;
let modal = document.getElementById('myModal');
let btnClose = document.getElementById("btnClose");
btnClose.onclick = function () {
    modal.style.display= "none"
};
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};

addRowUsers();
addRowUserInfoTable();
createUser();
updateUser();



async function addRowUsers() {
    let promise = await fetch(urlUsers);
    let json = await promise.json();
    let tableBody = document.getElementById("tableId");
    let inputId = document.getElementById("formControlInputId");
    let inputLogin = document.getElementById("formControlInputLogin2");
    let inputAge = document.getElementById("formControlInputAge2");

    for (let user of json) {

        // delete
        let btnDelete = document.createElement("button");
        btnDelete.setAttribute("class", "btn btn-danger btn-xl");
        btnDelete.setAttribute("value", user.id);
        btnDelete.innerText = "delete";
        btnDelete.onclick = function () {
            usId = btnDelete.value;
            let jsonIdUser = {id : usId};
            deleteUser(jsonIdUser);
        }

        // update
        let btnNode = document.createElement("button");
        btnNode.setAttribute("class", "btn btn-primary btn-xl");
        btnNode.setAttribute("value", user.id);
        btnNode.innerText = "edit";
        btnNode.onclick = function () {
            usId = btnNode.value;
            inputId.placeholder = usId;
            inputAge.placeholder = user.age;
            inputLogin.placeholder = user.username;
            modal.style.display = "block";
        }

        let newCellArr = [document.createElement("td"),
            document.createElement("td"),
            document.createElement("td"),
            document.createElement("td"),
            document.createElement("td"),
            document.createElement("td")];

        let newRow = document.createElement("tr");

        newCellArr[0].innerHTML = user.id;
        newCellArr[1].innerHTML = user.username;
        newCellArr[2].innerHTML = user.age;
        if (user['roles'].length == 2) {
            newCellArr[3].innerHTML = "ADMIN, USER";
        } else if (user['roles'][0]['id'] == 1) {
            newCellArr[3].innerHTML = "ADMIN";
        } else {
            newCellArr[3].innerHTML = "USER";
        }
        newCellArr[4].appendChild(btnNode);
        newCellArr[5].appendChild(btnDelete);


        for (let i = 0; i < newCellArr.length; i++) {
        newRow.appendChild(newCellArr[i]);
    }
   tableBody.appendChild(newRow);
  }


}

async function addRowUserInfoTable() {
    let promise = await fetch(urlUser);
    let json = await promise.json();

    let tableBody = document.getElementById("tbodyUser");
    let newCellArr = [document.createElement("td"),
        document.createElement("td"),
        document.createElement("td"),
        document.createElement("td"),];
    newCellArr[0].innerHTML = json.id;
    newCellArr[1].innerHTML = json.username;
    newCellArr[2].innerHTML = json.age;
    if (json['roles'].length == 2) {
        newCellArr[3].innerHTML = "ADMIN, USER";
    } else if (json['roles'][0]['id'] == 1) {
        newCellArr[3].innerHTML = "ADMIN";
    } else {
        newCellArr[3].innerHTML = "USER";
    }

    let row = document.createElement('tr');
    for (let i = 0; i < newCellArr.length; i++) {
        row.appendChild(newCellArr[i]);
    }
    tableBody.appendChild(row);

}

async function createUser() {
    let user;
    document.getElementById('btn').addEventListener('click', () => {
        let login = document.getElementById('inputLogin').value;
        let password = document.getElementById('inputPassword').value;
        let age = document.getElementById('inputAge').value;
        let roles = $("#selectRole").val();
        console.log(roles);
        let resRoles;
        if (roles.length == 2) {
            resRoles = [{id: 1, name: roles[0]},{id: 2, name: roles[1]}]
        } else if (roles[0] == "ROLE_ADMIN") {
            resRoles = [{id: 1, name: roles[0]}]
        } else {
            resRoles = [{id: 2, name: roles[0]}]
        }
        user = {password: password, username: login, age: Number(age), roles: resRoles };
        console.log(user);
        fetch("http://localhost:8080/people", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        });
    });
}

async function updateUser(){
    let userUpdate;
    document.getElementById('btnEditId').addEventListener('click', () => {
        let id = usId;
        let login = document.getElementById('formControlInputLogin2').value;
        let password = document.getElementById('formControlInputPassword2').value;
        let age = document.getElementById('formControlInputAge2').value;
        let roles = $("#selectRoleModal").val();
        let resRoles;
        if (roles.length == 2) {
            resRoles = [{id: 1, name: roles[0]},{id: 2, name: roles[1]}]
        } else if (roles[0] == "ROLE_ADMIN") {
            resRoles = [{id: 1, name: roles[0]}]
        } else {
            resRoles = [{id: 2, name: roles[0]}]
        }
        userUpdate = {id: id, password: password, username: login, age: Number(age), roles: resRoles };
        fetch("http://localhost:8080/people/", {
            method: "PATCH",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(userUpdate)
        });
    });
    console.log(userUpdate);
}

async function deleteUser(id) {
     await fetch("http://localhost:8080/people/", {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(id)
    });
     console.log(id);
}