const tableBody = document.getElementById("tableBody");
const tableHead = document.getElementById("tableHead");

const navRoles = document.getElementById("nav_roles");
const currentUserName = document.getElementById("currentUserName");



// ----- FETCH BLOCK
fetch("http://localhost:8080/api/user")
    .then(response => {
        return response.json();
    })
    .then(currentUser => {
        fillNavbar(currentUser)
        fillHead(tableHead);
        fillRow(tableBody, currentUser);
    });



// METHODS TO FILL HEAD AND CONTENT
function fillHead(parentElement) {
    parentElement.innerHTML = "";
    parentElement.append(HeadRow())
}

function fillRow(parentElement, usersData) {
    parentElement.innerHTML = "";
    parentElement.append(UserRow(usersData));
}

function fillNavbar({username, roleNames}) {
    currentUserName.innerText = username;
    roleNames.forEach(role => navRoles.innerText += role + " ")
}

// STATIC DATA OF TABLE HEAD
function HeadRow() {
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


    return headRow;
}

// DATA BOUT CURRENT USER
function Navbar({username, roleNames}) {

}

function UserRow({id, username, name, lastName, age, roleNames}) {
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


    return userRow;
}


// ------- CREATE ELEMENT WITH STYLES CONNECTED TO PARENT
function createElement(tagName, bootstrapClass, parent) {
    const element = document.createElement(tagName);
    if(parent) parent.append(element);
    element.className = bootstrapClass;

    return element;
}
