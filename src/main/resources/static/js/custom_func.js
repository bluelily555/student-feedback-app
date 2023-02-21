
function show(id){
    if(document.getElementById(id) != null) {
        document.getElementById(id).removeAttribute("hidden")
    }
}
function hide(id) {
    if(document.getElementById(id) != null) {
        document.getElementById(id).setAttribute("hidden", "true")
    }
}
// true 면 활성화
function check_activate(bool, id){
    if(!bool){
        document.getElementById(id).setAttribute("disabled", "true")
    }else{
        document.getElementById(id).removeAttribute("disabled")
    }
}