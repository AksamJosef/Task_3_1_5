const LOCALHOST_8080 = "http://localhost:8080/api/";

/** RENDER PAGE VIA JS */
const tableBody = document.getElementById("tableBody");
const tableHead = document.getElementById("tableHead");
const navRoles = document.getElementById("nav_roles");
const currentUserName = document.getElementById("currentUserName");
const newUserRoles = document.getElementById("newUserRoles");

tableBody.addEventListener("click", handleModalWindow);
fetchAllUsers()
    .then(users => {
    fetchCurrentUser().then(currentUser => fillNavbar(currentUser));
    fillTable(tableHead, tableBody, users);
})
    .then(() => fillNewUserRoles());
/** END TABLE */


/**
 * FETCH BLOCK */

/** GET */

async function fetchCurrentUser() {
    const response = await fetch(LOCALHOST_8080 + "user");
    return response.json();
}

async function fetchUserById(id) {
    const response = await fetch(LOCALHOST_8080 + "admin/users/" + id);
    return response.json();
}

async function fetchAllUsers() {
    const response = await fetch(LOCALHOST_8080 + "admin/users");
    return response.json();
}

async function fetchAllRoles() {
    const response = await fetch(LOCALHOST_8080 + "roles");
    return response.json();
}

/** POST */
async function saveUser(user) {
    const response = await fetch(LOCALHOST_8080 + "admin/users", {
        method: "POST", headers: {
            "Content-Type": "application/json"
        }, body: JSON.stringify(user)
    });
    return response.json();
}

/**  PUT */
async function updateUser(user) {
    const response = await fetch(LOCALHOST_8080 + "admin/users", {
        method: "PUT", headers: {
            "Content-Type": "application/json"
        }, body: JSON.stringify(user)
    });
    return response.json();
}

/**  DELETE */
async function deleteUser(id) {
    const response = await fetch(LOCALHOST_8080 + "admin/users/" + id, {
        method: "DELETE"
    });
    return response.json();
}


/**
 * END: FETCH BLOCK */


/**  METHODS TO FILL TABLE HEAD AND CONTENT */

function fillTable(_tableHead, _tableBody, usersData) {
    fillHead(_tableHead);
    fillRows(_tableBody, usersData);
}

function fillHead(parentElement) {
    parentElement.innerHTML = "";
    parentElement.append(AdminHeadRow())
}

function fillRows(parentElement, usersData) {

    parentElement.innerHTML = "";
    if (Array.isArray(usersData)) {
        usersData.forEach(user => parentElement.append(AdminUserRow(user)));
    } else {
        parentElement.append(AdminUserRow(usersData));
    }
}

function fillNavbar({username, roleNames}) {
    currentUserName.innerText = username;
    roleNames.forEach(role => navRoles.innerText += role + " ")
}

/**  END: METHODS TO FILL TABLE HEAD AND CONTENT */

/**  STATIC DATA OF TABLE HEAD */
function AdminHeadRow() {
    const headRow = createElement("tr");

    const id = createElement("th", "align-middle", headRow);
    id.innerText = "ID";

    const firstName = createElement("th", "align-middle", headRow);
    firstName.innerText = "First Name";

    const lastName = createElement("th", "align-middle", headRow);
    lastName.innerText = "Last Name";

    const age = createElement("th", "align-middle", headRow);
    age.innerText = "Age";

    const email = createElement("th", "align-middle", headRow);
    email.innerText = "Email";


    const roles = createElement("th", "align-middle", headRow);
    roles.innerText = "Role";

    const _edit = createElement("th", "align-middle", headRow);
    _edit.innerText = "Edit";

    const _delete = createElement("th", "align-middle", headRow);
    _delete.innerText = "Delete";

    return headRow;
}

/**  END: STATIC DATA OF TABLE HEAD */


/**  DATA FROM USER LIST */
function AdminUserRow({id, username, name, lastName, age, roleNames}) {
    const userRow = createElement("tr");

    const userId = createElement("th", "align-middle", userRow);
    userId.innerText = id;

    const userFirstName = createElement("td", "align-middle", userRow);
    userFirstName.innerText = name;

    const userLastName = createElement("td", "align-middle", userRow);
    userLastName.innerText = lastName;

    const userAge = createElement("td", "align-middle", userRow);
    userAge.innerText = age;

    const userEmail = createElement("td", "align-middle", userRow);
    userEmail.innerText = username;


    const userRoles = createElement("td", "align-middle", userRow);
    roleNames.forEach(role => userRoles.innerText += role + " ");

    const _edit = createElement("td", "align-middle", userRow);
    const buttonEdit = createElement("button", "btn btn-info", _edit);
    buttonEdit.innerText = "Edit";
    buttonEdit.dataset.bsToggle = "modal";
    buttonEdit.dataset.bsTarget = "#modalWindow";
    buttonEdit.dataset.userId = id;
    buttonEdit.dataset.action = "edit";

    const _delete = createElement("td", "align-middle", userRow);
    const buttonDelete = createElement("button", "btn btn-danger", _delete);
    buttonDelete.innerText = "Delete";
    buttonDelete.dataset.bsToggle = "modal";
    buttonDelete.dataset.bsTarget = "#modalWindow";
    buttonDelete.dataset.userId = id;
    buttonDelete.dataset.action = "delete";

    return userRow;
}

/** END: DATA FROM USER LIST */


/**  CREATE ELEMENT WITH STYLES CONNECTED TO PARENT */
function createElement(tagName, bootstrapClass, parent) {
    const element = document.createElement(tagName);
    if (parent) parent.append(element);
    element.className = bootstrapClass;

    return element;
}

/**  END: CREATE ELEMENT WITH STYLES CONNECTED TO PARENT */

/**  TRANSFER FORM TO OBJECT USER */

function mapFormToUser(form) {
    const formData = new FormData(form);
    const user = {};

    function Role(id) {
        this.id = id;
    }

    for (const [key, value] of formData) {
        // if key is "roles" -- create a [] array, then push each role via
        // new Role(id) --> in JSON we have structure: [{"id": 1}, {"id":2}] for roles Admin+User
        if (key === "roles") {
            user[key] ||= [];
            user[key].push(new Role(value));
        } else {
            user[key] = value;
        }
    }
    return user;
}

/**  END: TRANSFER FORM TO OBJECT USER */

/**  ADD NEW USER TAB */

const newUserForm = document.getElementById("new-tab");

newUserForm.addEventListener("submit", handleNewUser);
const homeTab = document.getElementById("home-tab");
const profileTab = document.getElementById("profile-tab");
const homePane = document.getElementById("home-tab-pane");
const profilePane = document.getElementById("profile-tab-pane");

async function fillNewUserRoles() {
    const roles = await fetchAllRoles();
    const label = createElement("label", "form-label fw-bold", newUserRoles);
    label.innerText = "Role";
    label.setAttribute("for", "roles");
    const select = createElement("select", "form-select w-100", newUserRoles);
    select.id = "roles";
    select.name = "roles";
    select.multiple = true;
    select.required = true;

    roles.forEach(role => {
        const option = createElement("option", "", select);
        option.value = role.id || role;
        option.innerText = role.role || role;

    });

}


function handleNewUser(event) {
    event.preventDefault();
    const user = mapFormToUser(newUserForm);

    saveUser(user).then(() => {
        newUserForm.reset();
        homeTab.classList.add("active");
        profileTab.classList.remove("active");
        homePane.classList.add("active", "show");
        profilePane.classList.remove("active", "show");
        fetchAllUsers().then(users => {
            fillTable(tableHead, tableBody, users);
        });
    });
}

/**  END: ADD NEW USER TAB */


/**  MODALS!!! */

const modalActionBtn = document.querySelector(".modal-footer > [data-action]");
modalActionBtn.addEventListener("click", handleModalAction);

const modalTitle = document.querySelector(".modal-title");
const modalBody = document.querySelector("#modalBody");

async function handleModalWindow(event) {
    const userId = event.target.dataset.userId;
    if (!userId) return;

    modalBody.innerHTML = "";
    const processedUser = await fetchUserById(userId);
    const roles = await fetchAllRoles();


    let form;
    if (event.target.dataset.action === "edit") {
        modalTitle.innerText = "Edit user";
        modalActionBtn.innerText = "Edit";
        modalActionBtn.className = "btn btn-primary";
        modalActionBtn.dataset.action = "edit";

        form = createUserForm("edit", processedUser, roles);

    } else if (event.target.dataset.action === "delete") {
        modalTitle.innerText = "Delete user";
        modalActionBtn.innerText = "Delete";
        modalActionBtn.className = "btn btn-danger";
        modalActionBtn.dataset.action = "delete";

        form = createUserForm("delete", processedUser, roles);
    }

    modalBody.append(form);
}

async function handleModalAction(event) {
    event.preventDefault();
    const form = document.querySelector("#modalForm");
    const userId = form.querySelector("[data-user-id]");

    if (modalActionBtn.dataset.action === "edit") {
        userId.disabled = false;
        const user = mapFormToUser(form);
        userId.disabled = true;

        if (user.password === "") {
            user.password = modalCurrentUserPass;
        }

        updateUser(user)
            .then(() => {
                fetchAllUsers()
                    .then(users => {
                        fillTable(tableHead, tableBody, users);
                    });
            });
    } else if (modalActionBtn.dataset.action === "delete") {
        deleteUser(userId.dataset.userId)
            .then(() => {
                fetchAllUsers()
                    .then(users => {
                        fillTable(tableHead, tableBody, users);
                    });
            });
    }
}

let modalCurrentUserPass;

function createUserForm(action, user, allRoles) {
    const {id, name, lastName, age, username, password, roles} = user;
    const formData = [
        {name: "id", label: "ID", value: id},
        {name: "name", label: "First name", value: name},
        {name: "lastName", label: "Last name", value: lastName},
        {name: "age", label: "Age", value: age},
        {name: "username", label: "Email", value: username},
        {name: "password", label: "Password", value: password},
        {name: "roles", label: "Role", value: roles}
    ];

    const form = createElement("form", "d-flex flex-column align-items-center");
    form.id = "modalForm";

    formData.forEach(data => {
        // Skip password field if we render delete form
        if (action === "delete" && data.name === "password") return;


        const container = createElement("div", "col-10 mx-auto mb-3 d-flex flex-column align-items-center w-100", form);
        const label = createElement("label", "form-label fw-bold", container);
        label.innerText = data.label;
        label.setAttribute("for", data.name);


        // If Role -> create select with options -> skip to the next input
        if (data.name === "roles") {
            const select = createElement("select", "form-control", container);
            select.name = data.name;
            select.multiple = true;
            select.required = true;
            if (action === "delete") {
                select.disabled = true;
            }
            // If delete size == user's roles
            select.size = action === "delete" ? data.value.length : allRoles.length;

            allRoles.forEach(role => {
                const option = createElement("option", "", select);
                option.value = role.id || role;
                option.innerText = role.role || role;

                // Select user's roles when editing and keep other available roles deselected
                if (action === "edit") {
                    option.selected = data.value.find(userRole => userRole.role === role.role);
                }
            });
            return;
        }

        // For basic inputs
        const input = createElement("input", "form-control", container);
        input.required = true;
        input.value = data.value;
        input.name = data.name;

        if (data.name === "password") {
            input.type = "password";
            input.value = "";
            modalCurrentUserPass = data.value;
        } else {
            input.type = "text";
        }

        // In delete form all fields are disabled, otherwise just ID
        if (action === "delete") {
            input.disabled = true;
        } else if (data.name === "id") {
            input.disabled = true;
        }
        input.dataset.userId = id;
    });
    
    return form;
}

/**  END MODALS */
