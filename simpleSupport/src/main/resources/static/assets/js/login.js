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

function validarSenha(senha) {
    var regexSenha = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return regexSenha.test(senha);
}

function cadastrar() {
    var nomeCadastro = document.getElementById('nomeCadastro').value;
    var sobrenomeCadastro = document.getElementById('sobrenomeCadastro').value;
    var emailCadastro = document.getElementById('emailCadastro').value;
    var telefoneCadastro = document.getElementById('telefoneCadastro').value;
    var senhaCadastro = document.getElementById('senhaCadastro').value;


    if (!nomeCadastro || !sobrenomeCadastro || !emailCadastro || !telefoneCadastro || !senhaCadastro) {
        Swal.fire({
            title: "Ops!",
            text: "Preencha todos os campos!",
            icon: "warning",
            confirmButtonText: 'OK'
        })
        return;
    }

    if(!validarSenha(senhaCadastro)) {
        exibirMensagemErro(mensagemErro, 'A senha deve ter no mínimo 8 caracteres, incluindo maiúsculas, minúsculas, números e caracteres especiais.');
        return;
    }

    $.ajax({
        url: '/cadastrar',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            nome: nomeCadastro,
            sobrenome: sobrenomeCadastro,
            email: emailCadastro,
            telefone: telefoneCadastro,
            senha: senhaCadastro,
            role: "USER"
        }),
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
                case 409:
                    Swal.fire({
                        title: "Ops!",
                        text: "Telefone já cadastrado.",
                        icon: "warning",
                        confirmButtonText: "OK"
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

function modalLogin() {
    $('#modalLogin').modal('show');
}

function logar() {
    var emailLogin = document.getElementById('emailLogin').value;
    var senhaLogin = document.getElementById('senhaLogin').value;

    $.ajax({
        url: '/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            email: emailLogin,
            password: senhaLogin,
        }),
        success: function(response) {
            window.location.href = "/dashboard/home";
        },
        error: function(xhr, status, error) {
            Swal.fire({
                title: "Ops!",
                text: "Email ou senha inválidos.",
                icon: "error",
                confirmButtonText: "OK"
            });
        }
    });
}
