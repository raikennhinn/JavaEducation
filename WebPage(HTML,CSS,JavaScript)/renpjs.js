//JavaScriptの文法各種と、DOMについて

//document.write("Hello world");

/*let i = 1;
console.log(i);
i = i + 1;
console.log(i);
i += 1;
console.log(i);
i++;
console.log(i);
*/
/*
let x = "JavaScript";

let v = "pen";
*/

//console.log("This is a "+ v +".");
//console.log(`This is a ${v}. Hello ${x} ${a + b}`);

/*


let a = 0;
let b = 10;
let c = 1;

if(a === b){
	console.log("aとbは同じ");
}else if(a === c){
	console.log("aとcは同じ、bは異なる");
}else{
	console.log("aはb、cどちらともちがう");
}

let suzi = ['5', '1', '9', '4'];
	console.log(suzi.sort());

let kudamono = {apple:1,pain:2,peach:3,};
	console.log(kudamono.apple);
	console.log(kudamono['pain']);
	kudamono.peach = 30;
	console.log(kudamono.peach);
	



let w = 0;
while(w < 5){
	console.log(w);
	w = w + 1;
}

let suzi = ['5', '1', '9', '4'];

for(let i =0;i<4;i++){
	suzi.sort();
	console.log(suzi[i]);
}


let mozi = 'Pen Pineapple Apple Pen';
console.log(mozi.split(' '));
console.log(mozi.substr(4,9));
console.log(mozi.length);

function ppap(){
  console.log('pen');
  console.log('pineapple');
  console.log('apple');
  console.log('pen');
}
//作成上と実行下
ppap();

let goukei =function sum(n){
	let total = 0;
	for(let i=0;i<10;i++){
		total += i;
	}
	return total;
}

console.log(goukei(10));
*/

/*
let sum = function(n){
  let total = 0;
  for(let i=0; i<10; i++){
    total = total + n * i;
  }
  return total;
}

let sumone = function(n) {
  let total = 0;
  for(let i=0; i<10; i++){
    total = total + n;
  }
  return total;
}

function enzan(list, en){
    let newList = [];
    for(let i = 0; i < list.length; i++ ) {
        let n = list[i];
        newList.push(en(n));
    }
  return newList;
}

let list = [10, 20];
console.log(enzan(list, sumone));

var answer = confirm("are you sure?");
console.log(answer);

var name = prompt("お名前は？","名無しさん");
console.log(name);
*/
/*

var user = {
	email:"taguchi@gmail.com",
	score:80
	};
	
console.log(user["email"]);
console.log(user.email);

user.score = 100;
console.log(user);

*/

/*
var user = {
	email:"yamada@gmail.com",
	score:80,
	greet:function(name){
		console.log("hello, " + name);
	}
};

console.log(user.email);
user.greet("Tom");

*/
/*
var user = {
	email: "taguchi@gmail.com", 
	score:80,
	name:"hirata",
	
	greet:function(name){
		console.log("hello, " + name + " from " + this.email);	//this = user
	}
};

user.email = ""
user.greet("Tom");

*/
/*
//console.dir(window);
console.log(window.outerHeight);
//ワンクッション置かずに変わる
//window.location.href = 'https://www.google.co.jp/';
*/

document.getElementById('add').addEventListener('click',function(){
var greet = document.createElement('p'), 
	text = document.createTextNode('hello world');
	document.body.appendChild(greet).appendChild(text);
	});


