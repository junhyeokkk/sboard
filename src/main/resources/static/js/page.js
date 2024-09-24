window.onload = function (){

    const numBtn= document.getElementsByClassName("num");

    Array.from(numBtn).forEach(function (numBtn){
    addEventListener('click', function (e) {

            console.log("dddd")
            e.target.classList.add("current");
        });
    });

};