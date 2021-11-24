var modal;
var formModal;
var formModalEl;
window.onload = function(){
	prepareForms();
	var responser = document.getElementById('responser');
	if (document.getElementById("info"))
		tinymce.init({
			selector: '#info',
			language: 'pt_BR',
		});
	if (document.getElementById('login'))
		document.getElementById('login').focus();

	if (document.getElementById('form-modal')){
		formModalEl = document.getElementById('form-modal');
		formModal = new bootstrap.Modal(formModalEl);
		formModalEl.addEventListener('show.bs.modal', function (event) {
			t = event.relatedTarget;
			let serial = t.getAttribute('data-bs-serial');
			let url = formModalEl.querySelector('form.json').getAttribute('action');
			console.log(serial);
			console.log(url);
			url = url.replace('serial',serial);
			formModalEl.querySelector('form.json').setAttribute('action',url);
		});
	}
}

function sendForm( event ) {
	let formData = new FormData(event.target);
	let url = event.target.action + '?';
	url += '_csrf='+ formData.get('_csrf');
	formData.delete('_csrf');
	let fields = {};
	formData.forEach((value, key) => fields[key] = value)
	
	if(event.target.classList.contains('reload'))
		postJson(url, JSON.stringify(fields) ).then( response => {
			responseDelegator(response, true);
		})
	else
		postJson(url, JSON.stringify(fields) ).then( response => {
			responseDelegator(response,false);
		});

	event.preventDefault();
};

async function postJson (url, req) {
	let response = await fetch(url,{
		method: 'post',
		headers: {
			'Content-Type': 'application/json',
			'Accept': 'application/json'
		},
		body: req
	});
	let json = await response.json();
	return json;
}

function responseDelegator(response, reload){

	if (modal === undefined)
		modal =	new bootstrap.Modal(responser);
	//Se existir e tiver algo, faça
	if(response.title && response.title !== undefined)
		responser.getElementsByClassName('modal-title')[0].innerHTML = response.title;
	if(response.message && response.message !== undefined)
		responser.querySelector('.modal-body p.comment').innerHTML = response.message;
	if(response.action && response.action !== undefined)
		responser.querySelector('.modal-body p.comment').innerHTML = response.action;
	if (reload)
		responser.addEventListener( 'hidden.bs.modal', function(){location.reload()} );
	modal.show();
}

function prepareForms(){
	document.querySelectorAll('form.json').forEach(
		(e)=> e.addEventListener( 'submit', sendForm )
	)
}