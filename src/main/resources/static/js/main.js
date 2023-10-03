marcarMenuActivo();

function marcarMenuActivo() {
    let menu = document.getElementById("menu");
    let u = document.createElement("u");

    let url = window.location.pathname;
    
    for(let i = 0; i < menu.children.length; i++) {
        let a = menu.children[i].children[0];
        let hrefAtribute = a.getAttribute("href");
        if (hrefAtribute.localeCompare(url) == 0) {
            u.innerHTML = a.innerHTML;
            a.innerHTML = "";
            a.append(u);
        }
    }
}