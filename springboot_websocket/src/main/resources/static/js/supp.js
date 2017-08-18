var getRandomColor = function(){
    return (function(m,s,c){
        return (c ? arguments.callee(m,s,c-1) : '#') +
            s[m.floor(m.random() * 16)]
    })(Math,'0123456789abcdef',5)
}


function getRandomColorCustomer(){
    var colors  = [
        '#EEB4B4',
        '#E6E6FA',
        '#C0FF3E',
        '#98FB98',
        '#CD6600'
    ];
    return colors[GetRandomNum(1,5)];
}
function GetRandomNum(Min,Max)
{
    var Range = Max - Min;
    var Rand = Math.random();
    return(Min + Math.round(Rand * Range));
}