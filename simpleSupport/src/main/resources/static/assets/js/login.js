function modalCadastro() {
    $('#modalCadastro').modal('show');
}

function cadastrar() {
    var nomeCadastro = document.getElementById('nomeCadastro').value;
    var sobrenomeCadastro = document.getElementById('sobrenomeCadastro').value;
    var emailCadastro = document.getElementById('emailCadastro').value;
    var senhaCadastro = document.getElementById('senhaCadastro').checked;

    $.ajax({
        url: '/cadastrar',
        type: 'POST',
        data: {
            nomeCadastro: nomeCadastro,
            sobrenomeCadastro: sobrenomeCadastro,
            emailCadastro: emailCadastro,
            senhaCadastro: senhaCadastro
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
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = "/login";
                        }
                    });
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