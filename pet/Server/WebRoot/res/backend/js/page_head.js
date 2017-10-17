/**
 * Created by liijdong on 2016/11/30.
 */

window.onload = function(){
    $(".application_2").hide();
    $(".application_3").hide();
    $(".application_7").hide();

}


$(function(){
    $(".application_1").hover(function(){
        $(".application_2").show();
        $(".application_3").show();
    },function(){
        $(".application_2").hide();
        $(".application_3").hide();
    });
})

$(function(){
    $(".application_5").hover(function(){
        $(".application_7").show();
    },function(){
        $(".application_7").hide();
    });
})