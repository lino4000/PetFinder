var modal;
window.onload = function(){
	document.getElementById('send').addEventListener( 'submit', sendForm );
	var responser = document.getElementById('responser');
}

function sendForm( event ) {
	console.log('entrou SendForm');
	let formData = new FormData(event.target);
	let url = event.target.action + '?';
	url += '_csrf='+ formData.get('_csrf');
	formData.delete('_csrf');
	let fields = {};
	formData.forEach((value, key) => fields[key] = value)

	postJson(url, JSON.stringify(fields) ).then( response => {
		responseDelegator(response);
		
	})
	event.preventDefault();
};

async function postJson (url, req) {
	let response = await fetch(url,{
		method: 'post',
//		credentials: 'include',
		headers: {
			'Content-Type': 'application/json',
			'Accept': 'application/json'
//			'Content-Length': req.length.toString(),
//			'Accept-Encoding': 'gzip, deflate, br',
//			'Connection': 'keep-alive'
		},
		body: req
	});
	let json = await response.json();
	return json;
}

function responseDelegator(response){

	if (modal === undefined)
		modal =	new bootstrap.Modal(responser);
	//Se existir e tiver algo, faça
	if(response.title && response.title !== undefined)
		responser.getElementsByClassName('modal-title')[0].innerHTML = response.title;
	if(response.message && response.message !== undefined)
		responser.querySelector('.modal-body p').innerHTML = response.message;
	if(response.action && response.action !== undefined)
		responser.querySelector('.modal-body p').innerHTML = response.action;
	modal.show();
}


/*



const userAction = async () => {
  const response = await fetch('http://example.com/movies.json', {
    method: 'POST',
    body: myBody, // string or object
    headers: {
      'Content-Type': 'application/json'
    }
  });
  const myJson = await response.json(); //extract JSON from the http response
  // do something with myJson
}

*/