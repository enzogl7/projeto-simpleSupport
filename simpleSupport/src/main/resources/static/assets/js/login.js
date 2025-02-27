document.getElementById('telefoneCadastro').addEventListener('input', function (e) {
    let telefone = e.target.value.replace(/\D/g, '');

    if (telefone.length > 10) {
        telefone = telefone.replace(/^(\d{2})(\d{5})(\d{4}).*/, '($1) $2-$3');
    } else {
        telefone = telefone.replace(/^(\d{2})(\d{4})(\d{0,4}).*/, '($1) $2-$3');
    }

    e.target.value = telefone;
});

function modalCadastro() {
    $('#modalCadastro').modal('show');
}

function cadastrar() {
    var nomeCadastro = document.getElementById('nomeCadastro').value;
    var sobrenomeCadastro = document.getElementById('sobrenomeCadastro').value;
    var emailCadastro = document.getElementById('emailCadastro').value;
    var telefoneCadastro = document.getElementById('telefoneCadastro').value;
    var senhaCadastro = document.getElementById('senhaCadastro').value;

    $.ajax({
        url: '/cadastrar',
        type: 'POST',
        data: {
            nome: nomeCadastro,
            sobrenome: sobrenomeCadastro,
            email: emailCadastro,
            telefone: telefoneCadastro,
            senha: senhaCadastro
        },
        complete: function(xhr, status) {
            switch (xhr.status) {
                case 200:
                    $('#modalCadastro').modal('hide')
                    Swal.fire({
                        title: "Pronto!",
                        text: "Cadastrado com sucesso!",
                        icon: "success",
                        confirmButtonText: 'OK'
                    })
                    break;
                case 406:
                    Swal.fire({
                        title: "Ops!",
                        text: "Email já cadastrado.",
                        icon: "warning",
                        confirmButtonText: 'OK'
                    })
                    break;
                case 500:
                    $('#modalCadastro').modal('hide')
                    Swal.fire({
                        title: "Erro!",
                        text: "Ocorreu um erro ao cadastrar-se.",
                        icon: "error"
                    });
                    break;
                default:
                    alert("Erro desconhecido: " + status);
            }
        }
    });
}