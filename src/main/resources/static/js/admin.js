const urlUsers = 'http://localhost:8080/people/';
const urlUser = 'http://localhost:8080/people/authentication/';
let usId;

let modal = document.getElementById('myModal');
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};

addUsersTable();
createForm();

function clearTable() {
    let table = document.getElementById("tableId");
    table.innerHTML = "";
}

async function addUsersTable() {
    clearTable();
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
            let jsonIdUser = {id: usId};
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
            updateUser(usId);
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

//USer table
(async () => {
    let promise = await fetch(urlUser);
    let json = await promise.json();
    let headerUserAndRole = document.getElementById("headerUsernameAndRole");
    let resStringRoles;
    let tableBody = document.getElementById("tbodyUser");
    let newCellArr = [document.createElement("td"),
        document.createElement("td"),
        document.createElement("td"),
        document.createElement("td"),];
    newCellArr[0].innerHTML = json.id;
    newCellArr[1].innerHTML = json.username;
    newCellArr[2].innerHTML = json.age;
    if (json['roles'].length == 2) {
        newCellArr[3].innerHTML = resStringRoles = "ADMIN, USER";
    } else if (json['roles'][0]['id'] == 1) {
        newCellArr[3].innerHTML = resStringRoles = "ADMIN";
    } else {
        newCellArr[3].innerHTML = resStringRoles = "USER";
    }

    headerUserAndRole.innerText = "Login: " + json.username + " with roles: " + resStringRoles;

    let row = document.createElement('tr');
    for (let i = 0; i < newCellArr.length; i++) {
        row.appendChild(newCellArr[i]);
    }
    tableBody.appendChild(row);

})();

//Create method
function createForm() {
    let user;
    let formCreate = document.getElementById("formCreate");
    let btnCreate = document.createElement("button");
    btnCreate.setAttribute("class", "w-25 btn btn-success");
    btnCreate.innerText = "create";
    btnCreate.onclick = function (ev) {
        ev.preventDefault();
        let login = document.getElementById('inputLogin').value;
        let password = document.getElementById('inputPassword').value;
        let age = document.getElementById('inputAge').value;
        let roles = $("#selectRole").val();

        let resRoles;
        if (roles.length == 0) {
            resRoles = [{id: 2, name: roles[0]}]
        } else if (roles.length == 2) {
            resRoles = [{id: 1, name: roles[0]}, {id: 2, name: roles[1]}]
        } else if (roles[0] == "ROLE_ADMIN") {
            resRoles = [{id: 1, name: roles[0]}]
        } else {
            resRoles = [{id: 2, name: roles[0]}]
        }
        user = {password: password, username: login, age: Number(age), roles: resRoles};
        createUser(user);
    }
    formCreate.appendChild(btnCreate);

}

function updateUser(id) {
    let tempId = id
    let userUpdate;
    //Создание кнопок закрыть, создать
    let btnEdit = document.createElement("button");
    btnEdit.setAttribute("class", "btn btn-primary btn-xl");
    btnEdit.innerText = "Edit";
    let buttonClose = document.createElement("button");
    buttonClose.setAttribute("class", "btn btn-secondary");
    buttonClose.setAttribute("id", "btnClose")
    buttonClose.innerText = "Close";
    buttonClose.onclick = function () {
        modal.style.display = "none";
    }
    let modalFooter = document.getElementById("modal-footer");
    btnEdit.onclick = function (ev) {
        let id = tempId;
        let login = document.getElementById('formControlInputLogin2').value;
        let password = document.getElementById('formControlInputPassword2').value;
        let age = document.getElementById('formControlInputAge2').value;
        let roles = $("#selectRoleModal").val();
        let resRoles;
        if (roles.length == 2) {
            resRoles = [{id: 1, name: roles[0]}, {id: 2, name: roles[1]}]
        } else if (roles[0] == "ROLE_ADMIN") {
            resRoles = [{id: 1, name: roles[0]}]
        } else {
            resRoles = [{id: 2, name: roles[0]}]
        }
        userUpdate = {
            id: id,
            password: password,
            username: login,
            age: Number(age),
            roles: resRoles
        };
        ev.preventDefault();
        fetch("http://localhost:8080/people/", {
            method: "PATCH",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(userUpdate)
        }).then(() => {
            $('#btnClose').click();
            addUsersTable();
            btnEdit.remove();
        });
    }
    modalFooter.appendChild(buttonClose);
    modalFooter.appendChild(btnEdit);
}

async function deleteUser(id) {
    await fetch("http://localhost:8080/people/", {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(id)
    });
    await addUsersTable();
}

async function createUser(user) {
    await fetch("http://localhost:8080/people/", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    }).then(() => {
        document.forms['formCreate'].reset();
        addUsersTable();
        $('#users-table').click();
    });
}